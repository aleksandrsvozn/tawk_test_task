package com.alexvoz.tawk_test_task.data.profile

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Profile tawk_test_task app data class
 *
 * @property id Long
 * @property followers Int
 * @property following Int
 * @property name String
 * @property company String
 * @property blog String
 */
@Parcelize
data class Profile(
    var id: Long,
    var followers: Int,
    var following: Int,
    var name: String,
    var company: String,
    var blog: String
) : Parcelable