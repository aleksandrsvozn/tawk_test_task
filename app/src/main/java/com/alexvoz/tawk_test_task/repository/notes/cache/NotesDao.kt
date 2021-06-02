package com.alexvoz.tawk_test_task.repository.notes.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alexvoz.tawk_test_task.data.notes.local_database.NotesCached

/**
 *  Profile local database functions
 */
@Dao
interface NotesDao {

    /***
     *  @param notesCached [NotesCached]
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notesCached: NotesCached)

    /**
     *  @param userId Long
     *  @return [NotesCached]?
     */
    @Query("SELECT * FROM notes WHERE id = :userId")
    suspend fun getNotesByUserId(userId: Long): NotesCached?

    /***
     *  @param input String
     *  @return List of [NotesCached]
     */
    @Query("SELECT * FROM notes WHERE value LIKE '%' || :input || '%'")
    suspend fun searchByInput(input: String): List<NotesCached>
}