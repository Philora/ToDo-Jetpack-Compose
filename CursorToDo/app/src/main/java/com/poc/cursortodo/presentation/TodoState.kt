package com.poc.cursortodo.presentation

import com.poc.cursortodo.domain.model.Todo

data class TodoUiState(
    val todos: List<Todo> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class TodoEvent {
    data class AddTodo(val title: String, val description: String) : TodoEvent()
    data class UpdateTodo(val todo: Todo) : TodoEvent()
    data class DeleteTodo(val id: Long) : TodoEvent()
    data object LoadTodos : TodoEvent()
}

