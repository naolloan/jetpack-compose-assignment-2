package com.example.todolistapp.repository

import com.example.todolistapp.data.TodoDao
import com.example.todolistapp.model.Todo
import com.example.todolistapp.network.TodoApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TodoRepository(
    private val api: TodoApiService,
    private val dao: TodoDao
) {
    // ✅ Called by ViewModel.loadTodos()
    suspend fun getCachedTodos(): List<Todo> {
        return dao.getAllTodos()
    }

    // ✅ Called by ViewModel.loadTodos()
    suspend fun getRemoteTodos(): List<Todo> {
        return api.getTodos()
    }

    // ✅ Called by ViewModel.loadTodos()
    suspend fun saveTodos(todos: List<Todo>) {
        dao.clearAll()
        dao.insertTodos(todos)
    }

    fun getTodos(): Flow<List<Todo>> = flow {
        // 1. Load from local database first
        val localData = dao.getAllTodos()
        emit(localData)

        try {
            // 2. Fetch from network
            val remoteData = api.getTodos()

            // 3. Save to local DB
            dao.insertTodos(remoteData)

            // 4. Emit updated data
            emit(remoteData)
        } catch (e: Exception) {
            // In case of error, just emit local data
            emit(localData)
        }
    }
}
