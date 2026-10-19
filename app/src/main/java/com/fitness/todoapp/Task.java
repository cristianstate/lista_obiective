package com.fitness.todoapp;

import java.io.Serializable;

/**
 * Model de date care reprezintă un singur obiectiv / sarcină din aplicație.
 * Implementează Serializable pentru a permite serializarea sau transmiterea obiectului între componente.
 */
public class Task implements Serializable {
    // Titlul sau descrierea obiectivului
    private String title;
    
    // Starea obiectivului: true dacă este finalizat, false dacă este în desfășurare
    private boolean completed;

    /**
     * Constructor pentru crearea unui nou obiectiv.
     * @param title Titlul obiectivului introdus de utilizator.
     */
    public Task(String title) {
        this.title = title;
        this.completed = false; // Implicit, un obiectiv nou nu este finalizat
    }

    // --- Metode Getter și Setter ---

    /**
     * Returnează titlul obiectivului.
     */
    public String getTitle() { 
        return title; 
    }

    /**
     * Setează un nou titlu pentru obiectiv.
     */
    public void setTitle(String title) { 
        this.title = title; 
    }

    /**
     * Verifică dacă obiectivul este marcat ca fiind completat.
     * @return true dacă este finalizat, false în caz contrar.
     */
    public boolean isCompleted() { 
        return completed; 
    }

    /**
     * Setează starea de finalizare a obiectivului.
     * @param completed noua stare (true/false).
     */
    public void setCompleted(boolean completed) { 
        this.completed = completed; 
    }
}
