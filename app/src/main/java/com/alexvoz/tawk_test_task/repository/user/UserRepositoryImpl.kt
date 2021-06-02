package com.alexvoz.tawk_test_task.repository.user

import com.alexvoz.tawk_test_task.data.profile.asPortfolio
import com.alexvoz.tawk_test_task.data.user.FIRST_USER_ID
import com.alexvoz.tawk_test_task.data.user.User
import com.alexvoz.tawk_test_task.data.user.asUser
import com.alexvoz.tawk_test_task.data.user.asUserCached
import com.alexvoz.tawk_test_task.data.wrappers.Resource
import com.alexvoz.tawk_test_task.repository.network.networkBoundResource
import com.alexvoz.tawk_test_task.repository.notes.cache.NotesDao
import com.alexvoz.tawk_test_task.repository.profile.cache.ProfileDao
import com.alexvoz.tawk_test_task.repository.user.cache.UserDao
import com.alexvoz.tawk_test_task.repository.user.network.UserRetrofit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

const val DOWNLOAD_USERS_PER_REQUEST = 30

/**
 *  User repository implementation
 *
 *  @property userDao an instance of [UserDao]
 *  @property profileDao an instance of [ProfileDao]
 *  @property notesDao an instance of [NotesDao]
 *  @property userRetrofit an instance of [UserRetrofit]
 */
class UserRepositoryImpl(
    private val userDao: UserDao,
    private val profileDao: ProfileDao,
    private val notesDao: NotesDao,
    private val userRetrofit: UserRetrofit
) : UserRepository {

    /**
     *  @property users List of [User]
     *  @return Flow<Resource<List<User>>>
     */
    override suspend fun getUsers(users: List<User>): Flow<Resource<List<User>>> =
        networkBoundResource(
            fromCache = {
                userDao.queryAllUntil(users.size + DOWNLOAD_USERS_PER_REQUEST).map {
                    it.map { user ->
                        user.asUser().apply {
                            this.profile = profileDao.getProfileById(user.id)?.asPortfolio()
                            this.notes = notesDao.getNotesByUserId(user.id)?.value ?: ""
                        }
                    }
                }
            },
            requestCall = { userRetrofit.getUsersList(users.lastOrNull()?.id ?: FIRST_USER_ID) },
            saveInCache = { usersResponse -> userDao.insertAll(usersResponse.map { it.asUserCached() }) }
        )

    /**
     *  @property input String
     *  @return Flow<Resource<List<User>>>
     */
    override suspend fun searchUsers(input: String): Flow<Resource<List<User>>> = flow {
        emit(
            Resource.Success(
                //By login
                userDao.searchByLogin(input).map { user ->
                    user.asUser().apply {
                        this.profile = profileDao.getProfileById(user.id)?.asPortfolio()
                        this.notes = notesDao.getNotesByUserId(user.id)?.value ?: ""
                    }
                }.toList().plus(
                    //By notes
                    notesDao.searchByInput(input).map { notes ->
                        userDao.getUserById(notes.id).first().asUser().apply {
                            this.profile = profileDao.getProfileById(this.id)?.asPortfolio()
                            this.notes = notes.value
                        }
                    })
            )
        )
    }
}
