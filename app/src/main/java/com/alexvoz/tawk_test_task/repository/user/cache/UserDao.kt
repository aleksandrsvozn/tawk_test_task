package com.alexvoz.tawk_test_task.repository.user.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alexvoz.tawk_test_task.data.user.local_database.UserCached
import kotlinx.coroutines.flow.Flow

/**
 *  User local database functions
 */
@Dao
interface UserDao {

    /**
     *  @param userCached [UserCached]
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userCached: UserCached)

    /***
     *  @param usersCached List of [UserCached]
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(usersCached: List<UserCached>)

    /**
     *  @param userLogin String
     *  @return [UserCached]
     */
    @Query("SELECT * FROM user WHERE login = :userLogin")
    suspend fun getUserByLogin(userLogin: String): UserCached

    /**
     *  @param input String
     *  @return List of [UserCached]
     */
    @Query("SELECT * FROM user WHERE login LIKE '%' || :input || '%'")
    suspend fun searchByLogin(input: String): List<UserCached>

    /**
     *  Using for working with Coroutines
     *
     *  @param limit Int
     *  @return Flow<List<UserCached>>
     */
    @Query("SELECT * FROM user ORDER BY id LIMIT :limit")
    fun queryAllUntil(limit: Int): Flow<List<UserCached>>

    /**
     *  Using for working with Coroutines
     *
     *  @param userId Long
     *  @return Flow<UserCached>
     */
    @Query("SELECT * FROM user WHERE id = :userId")
    fun getUserById(userId: Long): Flow<UserCached>
}