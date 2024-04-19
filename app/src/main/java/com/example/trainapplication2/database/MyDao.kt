package com.example.trainapplication2.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Data Access Object (DAO) interface for interacting with the database.
 */
@Dao
interface MyDao {

    /**
     * Inserts data into the database.
     * If there is a conflict, it ignores the new data.
     *
     * @param myEntity The entity to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveData(myEntity: MyEntity)

    /**
     * Retrieves all data from the database.
     *
     * @return A list of [MyEntity] objects representing the data in the database.
     */
    @Query("SELECT * FROM MyEntity")
    fun readData(): List<MyEntity>

    /**
     * Checks if a user exists in the database based on the provided username and password.
     *
     * @param username The username of the user to check.
     * @param password The password of the user to check.
     * @return A list of [MyEntity] objects matching the provided username and password.
     */
    @Query("SELECT * FROM MyEntity WHERE name_column LIKE :username AND pass_column LIKE :password")
    fun checkUser(username: String, password: String): List<MyEntity>
}