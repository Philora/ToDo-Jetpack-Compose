package com.poc.cursortodo.data.repository

import com.poc.cursortodo.data.local.TodoDao
import com.poc.cursortodo.data.local.TodoEntity
import com.poc.cursortodo.domain.model.Todo
import com.poc.cursortodo.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todoDao: TodoDao
) : TodoRepository {
    
    override fun getAllTodos(): Flow<List<Todo>> {
        return todoDao.getAllTodos().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }
    
    override suspend fun getTodoById(id: Long): Todo? {
        return todoDao.getTodoById(id)?.toDomainModel()
    }
    
    override suspend fun addTodo(todo: Todo) {
        todoDao.insertTodo(todo.toEntity())
    }
    
    override suspend fun updateTodo(todo: Todo) {
        todoDao.updateTodo(todo.toEntity())
    }
    
    override suspend fun deleteTodo(id: Long) {
        todoDao.deleteTodoById(id)
    }
    
    private fun TodoEntity.toDomainModel(): Todo {
        return Todo(
            id = id,
            title = title,
            description = description,
            isCompleted = isCompleted,
            createdAt = createdAt
        )
    }
    
    private fun Todo.toEntity(): TodoEntity {
        return TodoEntity(
            id = id,
            title = title,
            description = description,
            isCompleted = isCompleted,
            createdAt = createdAt
        )
    }
}

