package app.jwoo.myapplication.data

import androidx.lifecycle.LiveData
import androidx.room.*
import app.jwoo.myapplication.data.models.ToDoData

@Dao
interface ToDoDao {
    @Query("SELECT * FROM toda_table ORDER BY id ASC")
    fun getAll(): LiveData<List<ToDoData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(toDoData: ToDoData)

    @Update
    suspend fun updateData(toDoData: ToDoData)
}