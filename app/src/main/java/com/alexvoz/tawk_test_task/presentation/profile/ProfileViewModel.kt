package com.alexvoz.tawk_test_task.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexvoz.tawk_test_task.data.notes.local_database.NotesCached
import com.alexvoz.tawk_test_task.data.user.User
import com.alexvoz.tawk_test_task.data.wrappers.Resource
import com.alexvoz.tawk_test_task.repository.profile.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(private val profileRepository: ProfileRepository) :
    ViewModel() {

    private var job: Job? = null

    private val _user = MutableLiveData<Resource<User>?>()
    val user: LiveData<Resource<User>?>
        get() = _user

    fun initialization(userId: Long?, userLogin: String?) {
        if (userId != null && userLogin != null) {

            _user.value = null
            cancelAnyWorkingJob()

            getUserPortfolio(userId, userLogin)
        }
    }

    /**
     *  Download user portfolio and add it to local database user
     */
    private fun getUserPortfolio(userId: Long, userLogin: String) {
        cancelAnyWorkingJob()

        job = viewModelScope.launch {
            profileRepository.getUserPortfolioData(userId, userLogin).onEach { user ->
                _user.value = user
            }.launchIn(viewModelScope)
        }
    }

    fun saveNote(notes: String) {
        _user.value?.getData?.notes = notes

        viewModelScope.launch {
            user.value?.getData?.id?.let { userId ->
                profileRepository.updateUserNotes(NotesCached(userId, notes))
            }
        }
    }

    private fun cancelAnyWorkingJob() {
        job?.cancel()
    }
}