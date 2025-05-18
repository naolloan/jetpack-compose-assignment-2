package com.example.todolistapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.todolistapp.viewmodel.TodoViewModel

@Composable
fun AppNavigation(viewModel: TodoViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            TodoListScreen(viewModel, navController)
        }
        composable("detail/{todoId}") { backStackEntry ->
            val todoId = backStackEntry.arguments?.getString("todoId")?.toIntOrNull()
            val todo = viewModel.todos.collectAsState().value.find { it.id == todoId }
            todo?.let {
                TodoDetailScreen(it, navController)
            }
        }
    }
}
