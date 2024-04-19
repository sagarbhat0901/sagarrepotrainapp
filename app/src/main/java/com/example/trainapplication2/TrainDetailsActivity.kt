package com.example.trainapplication2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trainapplication2.adapter.TrainListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import org.json.JSONArray
import org.json.JSONException


class TrainDetailsActivity : AppCompatActivity() {

    private lateinit var trainNumberEditText: EditText
    private lateinit var trainNameEditText: EditText
    private lateinit var trainListRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var searchButton: Button
    private lateinit var trainDetails: TextView

    private val apiClient = ApiClient.getInstance()
    private val trainListAdapter = TrainListAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_train_details)

        trainNumberEditText = findViewById(R.id.trainNumberEditText)
        trainNameEditText = findViewById(R.id.trainNameEditText)
        trainListRecyclerView = findViewById(R.id.trainListRecyclerView)
        progressBar = findViewById(R.id.progressBar)
        searchButton = findViewById(R.id.button2)
        trainDetails = findViewById(R.id.trainDetailsTextView)
        trainDetails.visibility = View.GONE
        progressBar.visibility = View.GONE

        searchButton.setOnClickListener {
            searchTrain()
            progressBar.visibility = View.VISIBLE
        }

        trainListRecyclerView.layoutManager = LinearLayoutManager(this)
        trainListRecyclerView.adapter = trainListAdapter
    }

    private fun searchTrain() {
        val trainNumber = trainNumberEditText.text.toString()
        val trainName = trainNameEditText.text.toString().trim()


        if(trainNumber.isNullOrEmpty() && trainName.isNotEmpty()){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = apiClient.search(trainName)
                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.GONE
                    trainDetails.visibility = View.VISIBLE
                    Log.d("API_RESPONSE", response)
                    // Process the response here and update the UI
                    updateUI(response)
                }
            } catch (e: IOException) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.GONE
                    // Handle error
                    Log.e("API_ERROR", "Error fetching data: ${e.message}")
                }
            }
        }
        }
        else if(trainNumber.isNotEmpty() && trainName.isNullOrEmpty()){
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val response = apiClient.search(trainNumber)
                    withContext(Dispatchers.Main) {
                        progressBar.visibility = View.GONE
                        trainDetails.visibility = View.VISIBLE
                        Log.d("API_RESPONSE", response)
                        // Process the response here and update the UI
                        updateUI(response)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        progressBar.visibility = View.GONE
                        // Handle error
                        Log.e("API_ERROR", "Error fetching data: ${e.message}")
                    }
                }
            }
        }
        else if(trainNumber.isNotEmpty() && trainName.isNotEmpty()){
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val response = apiClient.search(trainName)
                    withContext(Dispatchers.Main) {
                        progressBar.visibility = View.GONE
                        trainDetails.visibility = View.VISIBLE
                        Log.d("API_RESPONSE", response)
                        // Process the response here and update the UI
                        updateUI(response)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        progressBar.visibility = View.GONE
                        // Handle error
                        Log.e("API_ERROR", "Error fetching data: ${e.message}")
                    }
                }
            }
        }
        else if(trainNumber.isNullOrEmpty()&& trainName.isNullOrEmpty()){
            Toast.makeText(this,"Please enter either Train number or Train Name you want to search", Toast.LENGTH_LONG)
        }
    }

    private fun updateUI(response: String) {
        // Parse the response and update the RecyclerView with train details
        // For simplicity, let's assume the response is a list of trains
        val trains = parseResponse(response)
        trainListAdapter.updateData(trains)
    }

    private fun parseResponse(response: String): List<Train> {
        val trains = mutableListOf<Train>()

        // Here, we'll parse the JSON string and create Train objects
        try {
            val jsonArray = JSONArray(response)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val trainNumber = jsonObject.getInt("train_num")
                val name = jsonObject.getString("name")
                val startingStation = jsonObject.getString("train_from")
                val destination = jsonObject.getString("train_to")
                val train = Train(trainNumber, name, startingStation, destination)
                trains.add(train)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return trains
    }
}
