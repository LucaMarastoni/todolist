package com.example.todolist.todo; // Package repository.

import org.springframework.data.jpa.repository.JpaRepository; // Interfaccia base Spring Data.

/**
 * Accesso al database: con una sola riga ereditiamo tutte le CRUD essential.
 */
public interface TodoRepository extends JpaRepository<Todo, Long> {

    long countByCompleted(boolean completed); // Conta quanti record sono completati o attivi.

    void deleteByCompletedTrue(); // Cancella in massa tutte le attività completate.
}
