package com.example.todolist.todo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    long countByCompleted(boolean completed);

    void deleteByCompletedTrue();
}
