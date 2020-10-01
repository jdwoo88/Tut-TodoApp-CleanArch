package app.jwoo.myapplication.data.repository

import androidx.lifecycle.LiveData
import app.jwoo.myapplication.data.ToDoDao
import app.jwoo.myapplication.data.models.ToDoData

class ToDoRepository(private val toDoDao: ToDoDao) {
    val getAllData: LiveData<List<ToDoData>> = toDoDao.getAll()

    suspend fun insertData(toDoData: ToDoData) {
        toDoDao.insert(toDoData)
    }

    suspend fun updateData(toDoData: ToDoData) {
        toDoDao.updateData(toDoData)
    }

    suspend fun deleteData(toDoData: ToDoData) {
        toDoDao.deleteData(toDoData)
    }

    suspend fun deleteAllData() {
        toDoDao.deleteAll()
    }
}