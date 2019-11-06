package com.codingblocks.todo

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TaskDao {
    @Insert()
    fun insertTask(task: TaskModel): Long

    @Query("UPDATE Task SET finish = 1 WHERE id = :tid")
    fun finishTask(tid: Long)

    @Query("DELETE FROM Task WHERE id = :tid")
    fun deleleTask(tid: Long)

    @Query("Select * FROM TASK WHERE finish = :finish")
    fun getTask(finish: Int): LiveData<List<TaskModel>>


}