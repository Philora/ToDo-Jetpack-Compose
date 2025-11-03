package com.poc.cursortodo.domain.usecase

import com.poc.cursortodo.domain.model.Todo
import com.poc.cursortodo.domain.repository.TodoRepository
import javax.inject.Inject

class UpdateTodoUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(todo: Todo) {
        repository.updateTodo(todo)
    }
}

