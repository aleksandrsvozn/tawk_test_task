package com.alexvoz.tawk_test_task.data.profile

import com.alexvoz.tawk_test_task.data.profile.local_database.ProfileCached
import com.alexvoz.tawk_test_task.data.profile.network.ProfileResponse

/**
 * [ProfileResponse] conversion to [ProfileCached]
 */
fun ProfileResponse.asCached() = ProfileCached(
    id = id,
    followers = followers,
    following = following,
    name = name ?: "",
    company = company ?: "",
    blog = blog
)

/**
 * [ProfileCached] conversion to [Profile]
 */
fun ProfileCached.asPortfolio() = Profile(
    id = id,
    followers = followers,
    following = following,
    name = name,
    company = company,
    blog = blog
)

