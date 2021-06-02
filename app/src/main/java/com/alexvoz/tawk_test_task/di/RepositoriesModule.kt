package com.alexvoz.tawk_test_task.di

import com.alexvoz.tawk_test_task.repository.notes.cache.NotesDao
import com.alexvoz.tawk_test_task.repository.profile.ProfileRepository
import com.alexvoz.tawk_test_task.repository.profile.ProfileRepositoryImpl
import com.alexvoz.tawk_test_task.repository.profile.cache.ProfileDao
import com.alexvoz.tawk_test_task.repository.profile.network.ProfileDataRetrofit
import com.alexvoz.tawk_test_task.repository.user.UserRepository
import com.alexvoz.tawk_test_task.repository.user.UserRepositoryImpl
import com.alexvoz.tawk_test_task.repository.user.cache.UserDao
import com.alexvoz.tawk_test_task.repository.user.network.UserRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object PortfolioRepositoryModule {
    @Singleton
    @Provides
    fun provideRepository(
        userDao: UserDao,
        profileDao: ProfileDao,
        notesDao: NotesDao,
        profileDataRetrofit: ProfileDataRetrofit
    ): ProfileRepository {
        return ProfileRepositoryImpl(userDao, profileDao, notesDao, profileDataRetrofit)
    }
}


@InstallIn(SingletonComponent::class)
@Module
object UserRepositoryModule {
    @Singleton
    @Provides
    fun provideRepository(
        userDao: UserDao,
        profileDao: ProfileDao,
        notesDao: NotesDao,
        userRetrofit: UserRetrofit
    ): UserRepository {
        return UserRepositoryImpl(userDao, profileDao, notesDao, userRetrofit)
    }
}