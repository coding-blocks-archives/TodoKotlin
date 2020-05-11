package com.example.todoapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TodoDao {

    @Insert()
    suspend fun insertTask(todoModel: TodoModel):Long

    @Query("Select * from TodoModel where isFinished == 0")
    fun getTask():LiveData<List<TodoModel>>

    @Query("Update TodoModel Set isFinished = 1 where id=:uid")
    fun finishTask(uid:Long)

    @Query("Delete from TodoModel where id=:uid")
    fun deleteTask(uid:Long)

    @Query("Select * from TodoModel where isFinished == 1")
    fun getFinishedTask():LiveData<List<TodoModel>>

    @Query("Delete from TodoModel where isFinished == 1")
    fun delete_finished_task()

}