package com.example.todolistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todolistapp.data.TodoDatabase
import com.example.todolistapp.network.RetrofitInstance
import com.example.todolistapp.repository.TodoRepository
import com.example.todolistapp.ui.AppNavigation
import com.example.todolistapp.ui.theme.TodoListAppTheme
import com.example.todolistapp.viewmodel.TodoViewModel
import com.example.todolistapp.viewmodel.TodoViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = TodoDatabase.getDatabase(applicationContext).todoDao()
        val repository = TodoRepository(RetrofitInstance.api, dao)
        val viewModelFactory = TodoViewModelFactory(repository)

        setContent {
            val viewModel: TodoViewModel = viewModel(factory = viewModelFactory)

            TodoListAppTheme {
                AppNavigation(viewModel)
            }
        }
    }
}
