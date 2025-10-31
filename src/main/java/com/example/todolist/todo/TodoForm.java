package com.example.todolist.todo;

import jakarta.validation.constraints.NotBlank;

public class TodoForm {

    @NotBlank(message = "Inserisci un'attività")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
