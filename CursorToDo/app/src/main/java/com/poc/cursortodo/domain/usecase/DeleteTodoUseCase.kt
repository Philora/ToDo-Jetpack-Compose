package com.poc.cursortodo.domain.usecase

import com.poc.cursortodo.domain.repository.TodoRepository
import javax.inject.Inject

class DeleteTodoUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(id: Long) {
        repository.deleteTodo(id)
    }
}

