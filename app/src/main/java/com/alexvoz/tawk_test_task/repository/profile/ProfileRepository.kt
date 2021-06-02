package com.alexvoz.tawk_test_task.repository.profile

import com.alexvoz.tawk_test_task.data.notes.local_database.NotesCached
import com.alexvoz.tawk_test_task.data.user.User
import com.alexvoz.tawk_test_task.data.wrappers.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Profile repository functions
 */
interface ProfileRepository {

    /**
     *  Actions:
     *  1) Return -> Resource.Loading with local data
     *  2) Then downloaded data will be saved in local database
     *  3) Return -> Resource.Success with Downloaded data from database
     *
     *  @param userId Long
     *  @param userLogin String
     *  @return Flow<Resource<User>>
     */
    suspend fun getUserPortfolioData(userId: Long, userLogin: String): Flow<Resource<User>>

    /**
     *  Updating the user notes (only in local database)
     *
     *  @param notes an instance of [NotesCached]
     */
    suspend fun updateUserNotes(notes: NotesCached)
}