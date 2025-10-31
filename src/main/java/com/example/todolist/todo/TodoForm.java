package com.example.todolist.todo; // Package per il form.

import jakarta.validation.constraints.NotBlank; // Validazione lato server.

/**
 * DTO minimale per gestire il binding del form HTML con validazione.
 */
public class TodoForm {

    @NotBlank(message = "Inserisci una descrizione prima di salvare.") // Validazione con messaggio amichevole.
    private String title; // Campo che arriva dalla richiesta POST.

    public String getTitle() {
        // Getter usato da Thymeleaf per leggere il valore nel form.
        return title;
    }

    public void setTitle(String title) {
        // Setter usato da Spring per popolare il campo dal form.
        this.title = title;
    }
}
