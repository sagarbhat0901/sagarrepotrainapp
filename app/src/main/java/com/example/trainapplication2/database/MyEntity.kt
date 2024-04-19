package com.example.trainapplication2.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity class representing a table in the database.
 */
@Entity
class MyEntity {

    // Primary key for the entity, auto-generated
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_column")
    var myId: Int = 0

    // Username column
    @ColumnInfo(name = "name_column")
    var myUsername: String = ""

    // Password column
    @ColumnInfo(name = "pass_column")
    var myPassword: String = ""

    // Age column
    @ColumnInfo(name = "age_column")
    var myAge: String = ""

    // Full name column
    @ColumnInfo(name = "fullname_column")
    var myFullName: String = ""
}