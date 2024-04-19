package com.example.trainapplication2.database

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Room Database class for managing database operations.
 * @property entities The array of entity classes that are included in the database.
 * @property version The version number of the database schema.
 */
@Database(entities = [MyEntity::class], version = 1)
abstract class MyDB : RoomDatabase() {

    /**
     * Abstract method to retrieve the Data Access Object (DAO) interface.
     * @return The DAO interface for database operations.
     */
    abstract fun myDao(): MyDao
}