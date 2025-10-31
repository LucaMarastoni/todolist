package com.example.todolist.todo; // Package service.

import java.util.List; // Import per liste.

import org.springframework.data.domain.Sort; // Import per ordinare risultati dal DB.
import org.springframework.stereotype.Service; // Annotazione che marca la classe come servizio.
import org.springframework.transaction.annotation.Transactional; // Gestione transazioni automatica.

/**
 * Racchiude tutta la logica applicativa legata alle ToDo.
 * Ogni metodo è piccolo, leggibile e documentato.
 */
@Service // Permette l'iniezione automatica e il riconoscimento da Spring.
@Transactional // Mantiene consistenza del database per ogni operazione.
public class TodoService {

    private final TodoRepository repository; // Repository usato per parlare con il DB.

    public TodoService(TodoRepository repository) {
        // Costruttore con injection: Spring passa automaticamente l'istanza.
        this.repository = repository;
    }

    public List<Todo> findAll() {
        // Recuperiamo tutte le attività ordinate prima le attive poi le completate.
        return repository.findAll(orderTodos());
    }

    public void add(String title) {
        // Difendiamo il database da stringhe nulle o vuote.
        if (title == null) {
            return; // Interrompiamo subito se arriva null.
        }
        String trimmed = title.trim(); // Rimuoviamo spazi iniziali/finali.
        if (trimmed.isEmpty()) {
            return; // Non salviamo titoli vuoti.
        }
        repository.save(new Todo(trimmed)); // Creiamo la nuova entità e la persistiamo.
    }

    public void toggle(Long id) {
        // Recuperiamo l'attività, invertiamo il flag completato e salviamo.
        repository.findById(id).ifPresent(todo -> {
            todo.setCompleted(!todo.isCompleted()); // Switch semplice.
            repository.save(todo); // Persistiamo la modifica.
        });
    }

    public void delete(Long id) {
        // Eliminazione diretta tramite ID.
        repository.deleteById(id);
    }

    public long countCompleted() {
        // Numero di attività già concluse.
        return repository.countByCompleted(true);
    }

    public long countActive() {
        // Numero di attività ancora da fare.
        return repository.countByCompleted(false);
    }

    public void clearCompleted() {
        // Cancelliamo tutte le righe con completed=true.
        repository.deleteByCompletedTrue();
    }

    private Sort orderTodos() {
        // Strategia d'ordinamento condivisa: attive in cima, completate in fondo, più recenti prima.
        return Sort.by(Sort.Direction.ASC, "completed")
            .and(Sort.by(Sort.Direction.DESC, "id"));
    }
}
