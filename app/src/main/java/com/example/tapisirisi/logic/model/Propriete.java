package com.example.tapisirisi.logic.model;

public class Propriete {
    private long id;
    private String libelle;
    private String description;
    public Propriete() {
    }
    public Propriete(String description, String libelle) {
        this.description = description;
        this.libelle = libelle;
    }

    public Propriete(long id, String description, String libelle) {
        this.id = id;
        this.description = description;
        this.libelle = libelle;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}

