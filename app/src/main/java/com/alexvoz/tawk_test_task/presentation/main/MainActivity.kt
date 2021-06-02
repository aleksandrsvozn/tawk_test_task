package com.alexvoz.tawk_test_task.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexvoz.tawk_test_task.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi

/**
 *  Main Activity
 *
 *  Contains:
 *  1) UserListFragment
 *  2) ProfileFragment
 */
@AndroidEntryPoint
@InternalCoroutinesApi
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}