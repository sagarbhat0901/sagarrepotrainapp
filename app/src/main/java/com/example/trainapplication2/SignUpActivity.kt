package com.example.trainapplication2


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.trainapplication2.database.MyDB
import com.example.trainapplication2.database.MyEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * SignUpActivity for user registration.
 */
class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Enable edge-to-edge display for immersive experience
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)

        // Initialize UI elements
        val usernameEditText = findViewById<EditText>(R.id.editTextUsername1)
        val passwordEditText = findViewById<EditText>(R.id.editTextPassword1)
        val ageEditText = findViewById<EditText>(R.id.editTextAge)
        val fullNameEditText = findViewById<EditText>(R.id.editTextFullName)
        val submitButton = findViewById<Button>(R.id.buttonSubmit)
        val db = Room.databaseBuilder(this, MyDB::class.java, "mydatabase")
            .fallbackToDestructiveMigration().build()
        val myIntent2 = Intent(this, MainActivity::class.java)
        val handler = Handler()

        // Set click listener for submit button
        submitButton.setOnClickListener {
            // Retrieve user input data
            val myUsername = usernameEditText.text.toString()
            val myPassword = passwordEditText.text.toString()
            val myAge = ageEditText.text.toString()
            val myFullName = fullNameEditText.text.toString()

            GlobalScope.launch {
                // Check if username already exists in the database
                val data: List<MyEntity>? = db.myDao().readData()
                val users = MyEntity().apply {
                    this.myUsername = myUsername
                    this.myPassword = myPassword
                    this.myAge = myAge
                    this.myFullName = myFullName
                }
                var check = 1
                if (data == null) {
                    handler.post {
                        Toast.makeText(
                            this@SignUpActivity,
                            "Please Enter valid data",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    data.forEach {
                        if (myUsername.equals(it.myUsername)) {
                            check = 0
                        }
                    }
                }
                // Insert user data into the database if username is unique
                if (check == 1) {
                    db.myDao().saveData(users)
                    handler.post {
                        Toast.makeText(
                            this@SignUpActivity,
                            "Details Submitted",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    startActivity(myIntent2)
                } else {
                    handler.post {
                        Toast.makeText(
                            this@SignUpActivity,
                            "Username is already taken",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }

            // Clear input fields after submission
            usernameEditText.setText("")
            passwordEditText.setText("")
            ageEditText.setText("")
            fullNameEditText.setText("")
        }
    }
}

