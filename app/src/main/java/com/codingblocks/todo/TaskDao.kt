package com.codingblocks.todo

import androidx.room.*

@Dao
interface TaskDao {
    @Insert()
    fun insertTask(task:TaskModel) : Long

    @Query("UPDATE Task SET finish = 0 WHERE id = :tid")
    fun finishTask(tid:Long)

    @Query("DELETE FROM Task WHERE id = :tid")
    fun deleleTask(tid: Long)

}