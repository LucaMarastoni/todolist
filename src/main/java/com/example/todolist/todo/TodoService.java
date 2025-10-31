package com.example.todolist.todo;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;

import java.util.List;

@Service
@Transactional
public class TodoService {

    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public List<Todo> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "completed")
            .and(Sort.by(Sort.Direction.DESC, "id")));
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

    public long countCompleted() {
        return repository.countByCompleted(true);
    }

    public long countActive() {
        return repository.countByCompleted(false);
    }

    public void clearCompleted() {
        repository.deleteByCompletedTrue();
    }
}
