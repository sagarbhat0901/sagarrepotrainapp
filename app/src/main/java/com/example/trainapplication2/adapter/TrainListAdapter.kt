package com.example.trainapplication2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trainapplication2.R
import com.example.trainapplication2.Train

class TrainListAdapter(private val trainList: ArrayList<Train>) : RecyclerView.Adapter<TrainListAdapter.TrainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_train, parent, false)
        return TrainViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrainViewHolder, position: Int) {
        val train = trainList[position]
        holder.bind(train)
    }

    override fun getItemCount(): Int {
        return trainList.size
    }

    fun updateData(trainList: List<Train>) {
        this.trainList.clear()
        this.trainList.addAll(trainList)
        notifyDataSetChanged()
    }

    inner class TrainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val trainNumberTextView: TextView = itemView.findViewById(R.id.trainNumberTextView)
        private val trainNameTextView: TextView = itemView.findViewById(R.id.trainNameTextView)
        private val stationFromTextView: TextView = itemView.findViewById(R.id.stationFromTextView)
        private val stationToTextView: TextView = itemView.findViewById(R.id.stationToTextView)

        fun bind(train: Train) {
            trainNumberTextView.text = "${train.trainNumber}"
            trainNameTextView.text = "${train.name}"
            stationFromTextView.text = "${train.startingStation}"
            stationToTextView.text = "${train.destination}"
        }
    }

}
