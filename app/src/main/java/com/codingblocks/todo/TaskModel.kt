package com.codingblocks.todo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class TaskModel(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var title: String,
    var task: String,
    var category: String,
    var date: String?,
    var time: String?,
    var finish: String?

)