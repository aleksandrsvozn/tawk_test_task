package com.alexvoz.tawk_test_task.presentation.users_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexvoz.tawk_test_task.data.user.User
import com.alexvoz.tawk_test_task.data.wrappers.Resource
import com.alexvoz.tawk_test_task.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserListViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    private var userLoadingJob: Job? = null

    var isUsersLoading = false

    private val _users = MutableLiveData<Resource<List<User>>>()
    val users: LiveData<Resource<List<User>>>
        get() = _users

    /**
     *  Download users list, use existingUsers to download 30 more
     *  By default download first 30 users
     *
     *  @param existingUsers List of [User] (optional)
     */
    fun getUsers(existingUsers: List<User> = listOf()) {
        cancelAnyUserLoadingJobJob()

        isUsersLoading = true

        userLoadingJob = viewModelScope.launch {

            userRepository.getUsers(existingUsers).onEach { resultUsers ->
                _users.value = resultUsers
            }.launchIn(viewModelScope)
        }
    }

    /**
     *  Search users by login and notes in local database
     */
    fun searchUsers(input: String) {
        cancelAnyUserLoadingJobJob()
        userLoadingJob = viewModelScope.launch {
            userRepository.searchUsers(input).cancellable().collect { user ->
                _users.value = user
            }
        }
    }

    /**
     *  Cancel of getUsers() or searchUsers() job
     *  @see getUsers()
     *  @see searchUsers()
     */
    fun cancelAnyUserLoadingJobJob() {
        userLoadingJob?.cancel()
    }
}