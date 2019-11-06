package com.codingblocks.todo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Task")
class TaskModel(
    var title: String,
    var task: String,
    var category: String,
    var date: String? = null,
    var time: String? = null,
    var finish: Int = TASK_IS_NOT_FINISH,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

)