package com.poc.cursortodo.domain.usecase

import com.poc.cursortodo.domain.model.Todo
import com.poc.cursortodo.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTodosUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    operator fun invoke(): Flow<List<Todo>> = repository.getAllTodos()
}

