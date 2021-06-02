package com.alexvoz.tawk_test_task.repository.user

import com.alexvoz.tawk_test_task.data.wrappers.Resource
import com.alexvoz.tawk_test_task.data.user.User
import kotlinx.coroutines.flow.Flow

/**
 * User repository functions
 */
interface UserRepository {

    /**
     *  Actions:
     *  1) Return -> Resource.Loading with local data
     *  2) Then downloaded data will be saved in local database
     *  3) Return -> Resource.Success with Downloaded 30 user plus previous users (if exists) from database
     *
     *  @property users List of [User]
     *  @return Flow<Resource<List<User>>>
     */
    suspend fun getUsers(users: List<User>): Flow<Resource<List<User>>>

    /**
     *  Search users by name and notes by input
     *
     *  @property input String
     *  @return Flow<Resource<List<User>>>
     */
    suspend fun searchUsers(input: String): Flow<Resource<List<User>>>
}