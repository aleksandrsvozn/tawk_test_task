package com.alexvoz.tawk_test_task.data.user

import com.alexvoz.tawk_test_task.data.user.local_database.UserCached
import com.alexvoz.tawk_test_task.data.user.network.UserResponse

/**
 * [UserResponse] conversion to [UserCached]
 */
fun UserResponse.asUserCached() = UserCached(
    id = id,
    login = login,
    url = url,
    avatarUrl = avatarUrl
)

/**
 * [UserCached] conversion to [User]
 */
fun UserCached.asUser() = User(
    id = id,
    login = login,
    url = url,
    avatarUrl = avatarUrl
)