package com.codingblocks.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class TaskAdapter(var mArrayList: ArrayList<TaskModel>) : RecyclerView.Adapter<TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.row_task, parent, false)
        return TaskViewHolder(mView)    }

    override fun getItemCount(): Int = mArrayList.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

    }
}

class TaskViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView)  {

}

