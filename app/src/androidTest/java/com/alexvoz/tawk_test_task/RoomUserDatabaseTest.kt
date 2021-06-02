package com.alexvoz.tawk_test_task

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.alexvoz.tawk_test_task.data.user.local_database.UserCached
import com.alexvoz.tawk_test_task.repository.cache.AppDatabase
import com.alexvoz.tawk_test_task.repository.user.cache.UserDao
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private const val TEST_USER_ID = 1L
private const val TEST_USER_LOGIN = "user_test_login"
private const val TEST_USER_LOGIN_UPDATED = "user_test_login_updated"

private val TEST_USER = UserCached(TEST_USER_ID, TEST_USER_LOGIN, "test_url", "test_url")

@ExperimentalCoroutinesApi
@SmallTest
class RoomUserDatabaseTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var userDao: UserDao
    private lateinit var appDatabase: AppDatabase

    @Before
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries().build()

        userDao = appDatabase.userDao()
    }

    @After
    fun closeDatabase() {
        appDatabase.close()
    }

    @Test
    fun insertionTest() = runBlockingTest {

        userDao.insert(TEST_USER)

        val userFromLocalDatabase = userDao.getUserById(TEST_USER_ID).first()

        assertThat(userFromLocalDatabase).isEqualTo(TEST_USER)
    }

    @Test
    fun editionTest() = runBlocking {
        val updatedUser = TEST_USER.apply { this.login = TEST_USER_LOGIN_UPDATED }

        userDao.insert(updatedUser)

        val userFromLocalDatabase = userDao.getUserById(TEST_USER_ID).first()

        assertThat(userFromLocalDatabase.login).isEqualTo(TEST_USER_LOGIN_UPDATED)
    }

    @Test
    fun searchByLoginTest() = runBlocking {
        userDao.insert(TEST_USER)

        val userFromLocalDatabase = userDao.searchByLogin(TEST_USER_LOGIN).first()

        assertThat(userFromLocalDatabase).isEqualTo(TEST_USER)
    }
}