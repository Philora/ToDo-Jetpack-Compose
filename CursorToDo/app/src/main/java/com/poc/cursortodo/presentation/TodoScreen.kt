package com.poc.cursortodo.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.poc.cursortodo.domain.model.Todo
import com.poc.cursortodo.presentation.components.TodoDialog
import com.poc.cursortodo.presentation.components.TodoItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(
    viewModel: TodoViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var editingTodo by remember { mutableStateOf<Todo?>(null) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Todo App") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Todo"
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                
                uiState.todos.isEmpty() -> {
                    EmptyTodosMessage(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        items(
                            items = uiState.todos,
                            key = { it.id }
                        ) { todo ->
                            TodoItem(
                                todo = todo,
                                onCheckedChange = { isChecked ->
                                    viewModel.handleEvent(
                                        TodoEvent.UpdateTodo(
                                            todo.copy(isCompleted = isChecked)
                                        )
                                    )
                                },
                                onEditClick = {
                                    editingTodo = todo
                                },
                                onDeleteClick = {
                                    viewModel.handleEvent(
                                        TodoEvent.DeleteTodo(todo.id)
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
        
        // Add Dialog
        if (showAddDialog) {
            TodoDialog(
                title = "Add New Todo",
                onDismiss = { showAddDialog = false },
                onConfirm = { title, description ->
                    viewModel.handleEvent(
                        TodoEvent.AddTodo(title, description)
                    )
                    showAddDialog = false
                }
            )
        }
        
        // Edit Dialog
        editingTodo?.let { todo ->
            TodoDialog(
                title = "Edit Todo",
                initialTitle = todo.title,
                initialDescription = todo.description,
                onDismiss = { editingTodo = null },
                onConfirm = { title, description ->
                    viewModel.handleEvent(
                        TodoEvent.UpdateTodo(
                            todo.copy(
                                title = title,
                                description = description
                            )
                        )
                    )
                    editingTodo = null
                }
            )
        }
        
        // Error Snackbar
        uiState.error?.let { error ->
            LaunchedEffect(error) {
                // Error handling could be shown via Snackbar if needed
            }
        }
    }
}

@Composable
private fun EmptyTodosMessage(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No todos yet",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Tap the + button to add your first todo",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

