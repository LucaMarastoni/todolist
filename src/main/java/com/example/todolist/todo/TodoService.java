package com.example.todolist.todo;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TodoService {

    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public List<Todo> findAll() {
        return repository.findAll();
    }

    public void add(String title) {
        if (title == null || title.trim().isEmpty()) {
            return;
        }
        repository.save(new Todo(title.trim()));
    }

    public void toggle(Long id) {
        repository.findById(id).ifPresent(todo -> {
            todo.setCompleted(!todo.isCompleted());
            repository.save(todo);
        });
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
