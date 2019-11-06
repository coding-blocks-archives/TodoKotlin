package com.codingblocks.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {
    var mArrayList: ArrayList<TaskModel> = ArrayList()
    lateinit var taskAdapter: TaskAdapter

    val db: AppDatabase by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "todo.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        recyclerViewTask.setHasFixedSize(true)
        recyclerViewTask.layoutManager = LinearLayoutManager(this)
        taskAdapter = TaskAdapter(mArrayList)
        recyclerViewTask.adapter = taskAdapter

        db.taskDao().getTask(TASK_IS_FINISH).observe(this, Observer {
            if (it.isNotEmpty()) {
                mArrayList = it as ArrayList<TaskModel>
                taskAdapter.setList(mArrayList)
            } else {
                mArrayList = it as ArrayList<TaskModel>
                taskAdapter.setList(mArrayList)
            }
            txtNoTask.isVisible = it.isEmpty()
        })
    }
}
