package com.alexvoz.tawk_test_task.data.notes.local_database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Notes local data base class
 *
 * @property id UserId
 * @property value String
 */
@Entity(tableName = "notes")
data class NotesCached(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Long,

    @ColumnInfo(name = "value")
    var value: String,
)