package com.alexvoz.tawk_test_task.di

import android.content.Context
import com.alexvoz.tawk_test_task.repository.network.interceptor.NetworkConnectionInterceptor
import com.alexvoz.tawk_test_task.repository.profile.network.ProfileDataRetrofit
import com.alexvoz.tawk_test_task.repository.user.network.UserRetrofit
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val MAX_REQUEST_AT_A_TIME = 1

@InstallIn(SingletonComponent::class)
@Module
object RetrofitModule {
    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, @ApplicationContext appContext: Context): Retrofit.Builder {
        val dispatcher = Dispatcher()
        dispatcher.maxRequests = MAX_REQUEST_AT_A_TIME

        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(5, TimeUnit.MINUTES)
            .connectTimeout(90, TimeUnit.SECONDS)
            .addInterceptor(NetworkConnectionInterceptor(appContext))
            .dispatcher(dispatcher)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideUserService(retrofit: Retrofit.Builder): UserRetrofit {
        return retrofit.build().create(UserRetrofit::class.java)
    }

    @Singleton
    @Provides
    fun providePortfolioDataService(retrofit: Retrofit.Builder): ProfileDataRetrofit {
        return retrofit.build().create(ProfileDataRetrofit::class.java)
    }
}