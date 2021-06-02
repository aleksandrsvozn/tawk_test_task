package com.alexvoz.tawk_test_task.data.user.network


import com.google.gson.annotations.SerializedName


/**
 * Response User data class from https://api.github.com/users?since=0 request
 *
 * @property id Long
 * @property login String
 * @property type String
 * @property url String
 * @property avatarUrl String
 * @property eventsUrl String
 * @property followersUrl String
 * @property gistsUrl String
 * @property gravatarId String
 * @property htmlUrl String
 * @property nodeId String
 * @property organizationsUrl String
 * @property receivedEventsUrl String
 * @property reposUrl String
 * @property siteAdmin Boolean
 * @property starredUrl String
 * @property subscriptionsUrl String
 */
data class UserResponse(
    val id: Long,
    val login: String = "",
    val type: String = "",
    val url: String = "",

    @SerializedName("avatar_url")
    val avatarUrl: String = "",

    @SerializedName("events_url")
    val eventsUrl: String = "",

    @SerializedName("followers_url")
    val followersUrl: String = "",

    @SerializedName("following_url")
    val followingUrl: String = "",

    @SerializedName("gists_url")
    val gistsUrl: String = "",

    @SerializedName("gravatar_id")
    val gravatarId: String = "",

    @SerializedName("html_url")
    val htmlUrl: String = "",

    @SerializedName("node_id")
    val nodeId: String = "",

    @SerializedName("organizations_url")
    val organizationsUrl: String = "",

    @SerializedName("received_events_url")
    val receivedEventsUrl: String = "",

    @SerializedName("repos_url")
    val reposUrl: String = "",

    @SerializedName("site_admin")
    val siteAdmin: Boolean = false,

    @SerializedName("starred_url")
    val starredUrl: String = "",

    @SerializedName("subscriptions_url")
    val subscriptionsUrl: String = ""
)