package com.alexvoz.tawk_test_task.repository.user.network

import com.alexvoz.tawk_test_task.data.user.network.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *  User network calls functions
 */
interface UserRetrofit {

    /**
     *  Get List of [UserResponse],
     *  Example: https://api.github.com/users?since=0
     *
     *  @param since Long (optional, by default equals 0, the index from which user start downloading 30 users)
     *  @return List of [UserResponse]
     */
    @GET("users")
    suspend fun getUsersList(@Query("since") since: Long = 0): List<UserResponse>
}