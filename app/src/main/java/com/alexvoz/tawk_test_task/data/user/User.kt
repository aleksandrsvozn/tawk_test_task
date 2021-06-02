package com.alexvoz.tawk_test_task.data.user

import android.os.Parcelable
import com.alexvoz.tawk_test_task.data.profile.Profile
import kotlinx.android.parcel.Parcelize

const val FIRST_USER_ID = 0L

/**
 * User tawk_test_task app data class
 *
 * @property id Long
 * @property login String
 * @property url String
 * @property avatarUrl String
 * @property profile an instance of [Profile] (optional)
 * @property notes String (optional)
 */
@Parcelize
data class User(
    var id: Long,
    var login: String,
    var url: String,
    var avatarUrl: String,
    var profile: Profile? = null,
    var notes: String = ""
) : Parcelable