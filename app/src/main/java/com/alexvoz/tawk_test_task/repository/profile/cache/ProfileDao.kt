package com.alexvoz.tawk_test_task.repository.profile.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alexvoz.tawk_test_task.data.profile.local_database.ProfileCached

/**
 *  Profile local database functions
 */
@Dao
interface ProfileDao {

    /***
     *  @param profileCached [ProfileCached]
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profileCached: ProfileCached)

    /**
     *  @param userId Long
     *  @return [ProfileCached]?
     */
    @Query("SELECT * FROM profile WHERE id = :userId")
    suspend fun getProfileById(userId: Long): ProfileCached?
}