package com.codingblocks.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_task.view.*
import java.util.*

class TaskAdapter(var mArrayList: ArrayList<TaskModel>) : RecyclerView.Adapter<TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.row_task, parent, false)
        return TaskViewHolder(mView)
    }

    override fun getItemCount(): Int = mArrayList.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(mArrayList[position])
    }

    override fun getItemId(position: Int): Long {
        return mArrayList[position].id
    }

    /**
     * Set new data list
     * */
    fun setList(mArrayList: ArrayList<TaskModel>) {
        this.mArrayList.clear()
        this.mArrayList = mArrayList
        notifyDataSetChanged()
    }


}

class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(taskModel: TaskModel) {
        with(itemView) {
            val androidColors = resources.getIntArray(R.array.random_color)
            val randomAndroidColor = androidColors[Random().nextInt(androidColors.size)]
            viewColorTag.setBackgroundColor(randomAndroidColor)
            txtShowTitle.text = taskModel.title
            txtShowTask.text = taskModel.task
            txtShowCategory.text = taskModel.category
        }
    }

}

