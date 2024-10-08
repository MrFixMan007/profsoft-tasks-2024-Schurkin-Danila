package com.example.profsoft_2024_final_task.di

import com.example.common.data.AuthenticatedApiRepository
import com.example.common.data.DatabaseRepository
import com.example.common.data.UnauthenticatedApiRepository
import com.example.common.domain.usecase.authenticated.GetAllCoursesUseCase
import com.example.common.domain.usecase.authenticated.GetCommunityNoteByIdUseCase
import com.example.common.domain.usecase.authenticated.GetCourseByIdUseCase
import com.example.common.domain.usecase.authenticated.GetLastCommunityNoteUseCase
import com.example.common.domain.usecase.authenticated.GetLastLocalNoteUseCase
import com.example.common.domain.usecase.unauthenticated.AuthorizeUserUseCase
import com.example.common.domain.usecase.unauthenticated.RegisterUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    @Singleton
    fun provideRegisterUserUseCase(apiRepository: UnauthenticatedApiRepository): RegisterUserUseCase {
        return RegisterUserUseCase(apiRepository = apiRepository)
    }

    @Provides
    @Singleton
    fun provideAuthorizeUserUseCase(apiRepository: UnauthenticatedApiRepository): AuthorizeUserUseCase {
        return AuthorizeUserUseCase(apiRepository = apiRepository)
    }
    
    @Provides
    @Singleton
    fun provideGetCourseByIdUseCase(apiRepository: AuthenticatedApiRepository): GetCourseByIdUseCase{
        return GetCourseByIdUseCase(apiRepository = apiRepository)
    }

    @Provides
    @Singleton
    fun provideGetAllCoursesUseCase(apiRepository: AuthenticatedApiRepository): GetAllCoursesUseCase{
        return GetAllCoursesUseCase(apiRepository = apiRepository)
    }

    @Provides
    @Singleton
    fun provideGetLastCommunityNoteUseCase(apiRepository: AuthenticatedApiRepository): GetLastCommunityNoteUseCase {
        return GetLastCommunityNoteUseCase(apiRepository = apiRepository)
    }

    @Provides
    @Singleton
    fun provideGetLastLocalNoteUseCase(databaseRepository: DatabaseRepository): GetLastLocalNoteUseCase {
        return GetLastLocalNoteUseCase(databaseRepository = databaseRepository)
    }

    @Provides
    @Singleton
    fun provideGetCommunityNoteByIdUseCase(apiRepository: AuthenticatedApiRepository): GetCommunityNoteByIdUseCase {
        return GetCommunityNoteByIdUseCase(apiRepository = apiRepository)
    }

}