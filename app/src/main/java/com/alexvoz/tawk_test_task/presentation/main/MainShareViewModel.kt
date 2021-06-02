package com.alexvoz.tawk_test_task.presentation.main

import androidx.lifecycle.ViewModel
import com.alexvoz.tawk_test_task.repository.network.InternetConnectionListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 *  Shared ViewModel is used to connect ViewModels
 *
 *  Functionality:
 *  1) internetConnectionListener LiveData;
 */
@HiltViewModel
class MainShareViewModel
@Inject
constructor(val internetConnectionListener: InternetConnectionListener) : ViewModel()
