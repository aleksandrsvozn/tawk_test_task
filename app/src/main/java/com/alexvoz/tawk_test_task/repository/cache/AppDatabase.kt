package com.alexvoz.tawk_test_task.repository.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alexvoz.tawk_test_task.data.notes.local_database.NotesCached
import com.alexvoz.tawk_test_task.data.profile.local_database.ProfileCached
import com.alexvoz.tawk_test_task.data.user.local_database.UserCached
import com.alexvoz.tawk_test_task.repository.notes.cache.NotesDao
import com.alexvoz.tawk_test_task.repository.profile.cache.ProfileDao
import com.alexvoz.tawk_test_task.repository.user.cache.UserDao

@Database(
    entities = [
        UserCached::class,
        ProfileCached::class,
        NotesCached::class
    ], version = 1
)
/**
 *  Tawk_test_task app Room database
 */
abstract class AppDatabase : RoomDatabase() {

    /**
     *  @return Instance of [UserDao]
     */
    abstract fun userDao(): UserDao

    /**
     *  @return Instance of [ProfileDao]
     */
    abstract fun profileDao(): ProfileDao

    /**
     *  @return Instance of [NotesDao]
     */
    abstract fun notesDao(): NotesDao

    companion object {
        const val DATABASE_NAME: String = "app_db"
    }
}