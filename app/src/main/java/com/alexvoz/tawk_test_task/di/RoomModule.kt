package com.alexvoz.tawk_test_task.di

import android.content.Context
import androidx.room.Room
import com.alexvoz.tawk_test_task.repository.cache.AppDatabase
import com.alexvoz.tawk_test_task.repository.notes.cache.NotesDao
import com.alexvoz.tawk_test_task.repository.profile.cache.ProfileDao
import com.alexvoz.tawk_test_task.repository.user.cache.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Singleton
    @Provides
    fun providePortfolioDao(appDatabase: AppDatabase): ProfileDao {
        return appDatabase.profileDao()
    }

    @Singleton
    @Provides
    fun provideNotesDao(appDatabase: AppDatabase): NotesDao {
        return appDatabase.notesDao()
    }
}