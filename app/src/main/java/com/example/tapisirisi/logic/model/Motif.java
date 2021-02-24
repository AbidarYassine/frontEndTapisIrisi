package com.example.tapisirisi.logic.model;

import java.io.Serializable;

public class Motif implements Serializable {
    private long id;
    private String libelle;
    private String description;

    public Motif() {
    }

    public Motif(String libelle, String description) {
        this.libelle = libelle;
        this.description = description;
    }

    public Motif(String libelle) {
        this.libelle = libelle;
    }

    public Motif(long id, String libelle, String description) {
        this.id = id;
        this.libelle = libelle;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
