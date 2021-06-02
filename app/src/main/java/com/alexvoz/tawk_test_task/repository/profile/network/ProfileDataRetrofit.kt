package com.alexvoz.tawk_test_task.repository.profile.network

import com.alexvoz.tawk_test_task.data.profile.network.ProfileResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *  Profile network calls functions
 */
interface ProfileDataRetrofit {
    /**
     *  Get [ProfileResponse],
     *  Example: https://api.github.com/users/tawk
     *
     *  @param user_login String
     *  @return [ProfileResponse]
     */
    @GET("users/{user_login}")
    suspend fun getPortfolioData(@Path("user_login") user_login: String): ProfileResponse
}