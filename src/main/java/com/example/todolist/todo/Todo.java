package com.example.todolist.todo; // Dichiarazione del package per organizzare il codice.

import jakarta.persistence.Entity; // Import dell'annotazione che indica una tabella JPA.
import jakarta.persistence.GeneratedValue; // Import per generare automaticamente l'ID.
import jakarta.persistence.GenerationType; // Import per scegliere la strategia di generazione ID.
import jakarta.persistence.Id; // Import che identifica il campo chiave primaria.
import jakarta.persistence.Table; // Import per nominare la tabella nel database.
import jakarta.validation.constraints.NotBlank; // Import per validare il campo titolo.

/**
 * Rappresenta una singola attività nel database.
 * Ogni variabile è strettamente essenziale e super commentata.
 */
@Entity // Indica a JPA che questa classe va trasformata in tabella.
@Table(name = "todos") // Nome esplicito della tabella: facile da riconoscere.
public class Todo {

    @Id // Definisce la colonna come chiave primaria.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Lasciamo che il DB generi ID incrementali.
    private Long id; // Identificativo univoco dell'attività.

    @NotBlank(message = "Il titolo non può essere vuoto") // Validazione: obblighiamo l'utente a compilare.
    private String title; // Testo dell'attività mostrato nella UI.

    private boolean completed; // Flag booleano che indica se l'attività è stata completata.

    public Todo() {
        // Costruttore vuoto richiesto da JPA.
    }

    public Todo(String title) {
        // Costruttore rapido per creare l'entità con titolo e completed=false implicito.
        this.title = title;
    }

    public Long getId() {
        // Getter essenziale per leggere l'ID dal database o dalla view.
        return id;
    }

    public String getTitle() {
        // Restituisce il testo dell'attività.
        return title;
    }

    public void setTitle(String title) {
        // Permette di aggiornare il titolo (utile se implementiamo l'edit in futuro).
        this.title = title;
    }

    public boolean isCompleted() {
        // Accessor booleano per leggere lo stato di completamento.
        return completed;
    }

    public void setCompleted(boolean completed) {
        // Setter per aggiornare lo stato completato/non completato.
        this.completed = completed;
    }
}
