package com.example.todolistapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todolistapp.viewmodel.TodoViewModel

@Composable
fun TodoListScreen(viewModel: TodoViewModel, navController: NavController) {
    val todos = viewModel.todos.collectAsState().value

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(todos) { todo ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable {
                        navController.navigate("detail/${todo.id}")
                    }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = todo.title, style = MaterialTheme.typography.titleMedium)
                    Text(text = if (todo.isCompleted) "Completed" else "Pending")
                }
            }
        }
    }
}
