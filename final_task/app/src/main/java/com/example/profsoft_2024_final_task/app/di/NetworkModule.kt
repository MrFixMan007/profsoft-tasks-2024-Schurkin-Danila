package com.example.profsoft_2024_final_task.app.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.common.data.AuthenticatedApiRepository
import com.example.common.data.UnauthenticatedApiRepository
import com.example.network.AuthInterceptor
import com.example.network.TokenProvider
import com.example.network.api.AuthenticatedApiRepositoryImpl
import com.example.network.api.CourseApiService
import com.example.network.api.UnauthenticatedApiRepositoryImpl
import com.example.network.api.UserApiService
import com.example.utils.shared_prefs.TOKEN_NAME
import com.example.utils.shared_prefs.TOKEN_SHARED_PREFS
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object {
        private const val READ_TIMEOUT_IN_SECONDS = 5L
        private const val CONNECTION_TIMEOUT_IN_SECONDS = 5L
        private const val DEV_BASE_URL = "http://profsoft.ddns.net:8080"
    }

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @UnauthenticatedRetrofitClient
    @Provides
    @Singleton
    fun provideUnauthenticatedOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(CONNECTION_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(ChuckerInterceptor(context = context))
            .build()
    }

    @UnauthenticatedRetrofitClient
    @Provides
    @Singleton
    fun provideUnauthenticatedRetrofit(@UnauthenticatedRetrofitClient okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(DEV_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideUserService(@UnauthenticatedRetrofitClient retrofit: Retrofit): UserApiService {
        return retrofit.create(UserApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideUnauthenticatedApiRepository(@UnauthenticatedRetrofitClient retrofit: Retrofit): UnauthenticatedApiRepository {
        return UnauthenticatedApiRepositoryImpl(retrofit = retrofit)
    }

    @Provides
    @Singleton
    fun provideTokenProvider(@ApplicationContext context: Context): TokenProvider{
        return object : TokenProvider{
            val sharedPreferences = context.getSharedPreferences(TOKEN_SHARED_PREFS, MODE_PRIVATE)
            val myToken = sharedPreferences.getString(TOKEN_NAME, "").toString()

            override fun getToken(): String {
                return myToken
            }
        }
    }

    @AuthenticatedRetrofitClient
    @Provides
    @Singleton
    fun provideAuthenticatedOkHttpClient(@ApplicationContext context: Context, tokenProvider: TokenProvider): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(CONNECTION_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(ChuckerInterceptor(context = context))
            .addInterceptor(AuthInterceptor(tokenProvider = tokenProvider))
            .build()
    }

    @AuthenticatedRetrofitClient
    @Provides
    @Singleton
    fun provideAuthenticatedRetrofit(@AuthenticatedRetrofitClient okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(DEV_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideCourseApiService(@AuthenticatedRetrofitClient retrofit: Retrofit): CourseApiService {
        return retrofit.create(CourseApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthenticatedApiRepository(@AuthenticatedRetrofitClient retrofit: Retrofit): AuthenticatedApiRepository {
        return AuthenticatedApiRepositoryImpl(retrofit = retrofit)
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthenticatedRetrofitClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UnauthenticatedRetrofitClient