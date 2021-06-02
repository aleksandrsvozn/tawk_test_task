package com.alexvoz.tawk_test_task.data.profile.local_database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Profile local data base class
 *
 * @property id Long
 * @property followers Int
 * @property name String
 * @property company String
 * @property blog String
 */
@Entity(tableName = "profile")
data class ProfileCached(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Long,

    @ColumnInfo(name = "followers")
    var followers: Int,

    @ColumnInfo(name = "following")
    var following: Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "company")
    var company: String,

    @ColumnInfo(name = "blog")
    var blog: String
)
