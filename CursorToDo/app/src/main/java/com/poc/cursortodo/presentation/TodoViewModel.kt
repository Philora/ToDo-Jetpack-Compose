package com.poc.cursortodo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poc.cursortodo.domain.model.Todo
import com.poc.cursortodo.domain.usecase.AddTodoUseCase
import com.poc.cursortodo.domain.usecase.DeleteTodoUseCase
import com.poc.cursortodo.domain.usecase.GetTodosUseCase
import com.poc.cursortodo.domain.usecase.UpdateTodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val getTodosUseCase: GetTodosUseCase,
    private val addTodoUseCase: AddTodoUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(TodoUiState(isLoading = true))
    val uiState: StateFlow<TodoUiState> = _uiState.asStateFlow()
    
    init {
        loadTodos()
    }
    
    fun handleEvent(event: TodoEvent) {
        when (event) {
            is TodoEvent.AddTodo -> addTodo(event.title, event.description)
            is TodoEvent.UpdateTodo -> updateTodo(event.todo)
            is TodoEvent.DeleteTodo -> deleteTodo(event.id)
            is TodoEvent.LoadTodos -> loadTodos()
        }
    }
    
    private fun loadTodos() {
        viewModelScope.launch {
            try {
                getTodosUseCase().collect { todos ->
                    _uiState.update { it.copy(todos = todos, isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error occurred"
                    ) 
                }
            }
        }
    }
    
    private fun addTodo(title: String, description: String) {
        if (title.isBlank()) return
        
        viewModelScope.launch {
            try {
                addTodoUseCase(
                    Todo(
                        title = title.trim(),
                        description = description.trim()
                    )
                )
                // State will be updated automatically via Flow
                _uiState.update { it.copy(error = null) }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(error = e.message ?: "Failed to add todo")
                }
            }
        }
    }
    
    private fun updateTodo(todo: Todo) {
        viewModelScope.launch {
            try {
                updateTodoUseCase(todo)
                // State will be updated automatically via Flow
                _uiState.update { it.copy(error = null) }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(error = e.message ?: "Failed to update todo")
                }
            }
        }
    }
    
    private fun deleteTodo(id: Long) {
        viewModelScope.launch {
            try {
                deleteTodoUseCase(id)
                // State will be updated automatically via Flow
                _uiState.update { it.copy(error = null) }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(error = e.message ?: "Failed to delete todo")
                }
            }
        }
    }
}

