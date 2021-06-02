package com.alexvoz.tawk_test_task.repository.profile

import com.alexvoz.tawk_test_task.data.notes.local_database.NotesCached
import com.alexvoz.tawk_test_task.data.profile.asCached
import com.alexvoz.tawk_test_task.data.profile.asPortfolio
import com.alexvoz.tawk_test_task.data.user.User
import com.alexvoz.tawk_test_task.data.user.asUser
import com.alexvoz.tawk_test_task.data.wrappers.Resource
import com.alexvoz.tawk_test_task.repository.network.networkBoundResource
import com.alexvoz.tawk_test_task.repository.notes.cache.NotesDao
import com.alexvoz.tawk_test_task.repository.profile.cache.ProfileDao
import com.alexvoz.tawk_test_task.repository.profile.network.ProfileDataRetrofit
import com.alexvoz.tawk_test_task.repository.user.cache.UserDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 *  Profile repository implementation
 *
 *  @property userDao an instance of [UserDao]
 *  @property profileDao an instance of [ProfileDao]
 *  @property notesDao an instance of [NotesDao]
 *  @property profileDataRetrofit an instance of [ProfileDataRetrofit]
 */
class ProfileRepositoryImpl(
    private val userDao: UserDao,
    private val profileDao: ProfileDao,
    private val notesDao: NotesDao,
    private val profileDataRetrofit: ProfileDataRetrofit
) : ProfileRepository {

    /**
     *  @param userId Long
     *  @param userLogin String
     *  @return Flow<Resource<User>> from local database
     */
    override suspend fun getUserPortfolioData(
        userId: Long,
        userLogin: String
    ): Flow<Resource<User>> = networkBoundResource(
        fromCache = {
            userDao.getUserById(userId).map { user ->
                user.asUser().apply {
                    this.profile = profileDao.getProfileById(user.id)?.asPortfolio()
                    this.notes = notesDao.getNotesByUserId(user.id)?.value ?: ""
                }
            }
        },
        requestCall = { profileDataRetrofit.getPortfolioData(userLogin) },
        saveInCache = { item -> profileDao.insert(item.asCached()) }
    )

    /**
     *  @param notes an instance of [NotesCached]
     */
    override suspend fun updateUserNotes(notes: NotesCached) {
        notesDao.insert(notes)
    }
}