package com.alexvoz.tawk_test_task.data.profile.network


import com.google.gson.annotations.SerializedName

/**
 * Response Profile data class from obtained from https://api.github.com/users/username request
 *
 * @property avatarUrl String
 * @property bio Any
 * @property blog String
 * @property company String
 * @property createdAt String
 * @property email Any
 * @property eventsUrl String
 * @property followers Int
 * @property followersUrl String
 * @property following Int
 * @property followingUrl String
 * @property gistsUrl String
 * @property gravatarId String
 * @property hireable Any
 * @property htmlUrl String
 * @property id Long
 * @property location String
 * @property login String
 * @property name String?
 * @property nodeId String
 * @property organizationsUrl String
 * @property publicGists Int
 * @property publicRepos Int
 * @property receivedEventsUrl String
 * @property reposUrl String
 * @property siteAdmin Boolean
 * @property starredUrl String
 * @property subscriptionsUrl String
 * @property twitterUsername Any
 * @property type String
 * @property updatedAt String
 * @property url String
 */
data class ProfileResponse(
    @SerializedName("avatar_url")
    val avatarUrl: String,
    val bio: Any,
    val blog: String,
    val company: String?,
    @SerializedName("created_at")
    val createdAt: String,
    val email: Any,
    @SerializedName("events_url")
    val eventsUrl: String,
    val followers: Int,
    @SerializedName("followers_url")
    val followersUrl: String,
    val following: Int,
    @SerializedName("following_url")
    val followingUrl: String,
    @SerializedName("gists_url")
    val gistsUrl: String,
    @SerializedName("gravatar_id")
    val gravatarId: String,
    val hireable: Any,
    @SerializedName("html_url")
    val htmlUrl: String,
    val id: Long,
    val location: String,
    val login: String,
    val name: String?,
    @SerializedName("node_id")
    val nodeId: String,
    @SerializedName("organizations_url")
    val organizationsUrl: String,
    @SerializedName("public_gists")
    val publicGists: Int,
    @SerializedName("public_repos")
    val publicRepos: Int,
    @SerializedName("received_events_url")
    val receivedEventsUrl: String,
    @SerializedName("repos_url")
    val reposUrl: String,
    @SerializedName("site_admin")
    val siteAdmin: Boolean,
    @SerializedName("starred_url")
    val starredUrl: String,
    @SerializedName("subscriptions_url")
    val subscriptionsUrl: String,
    @SerializedName("twitter_username")
    val twitterUsername: Any,
    val type: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    val url: String
)