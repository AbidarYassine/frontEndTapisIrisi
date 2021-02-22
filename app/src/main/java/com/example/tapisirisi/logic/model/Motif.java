package com.example.tapisirisi.logic.model;

public class Motif {
    private long id;
    private int drawable;
    private String libelle;

    public Motif() {
    }

    public Motif(int drawable, String libelle) {
        this.drawable = drawable;
        this.libelle = libelle;
    }

    public Motif(long id, int drawable, String libelle) {
        this.id = id;
        this.drawable = drawable;
        this.libelle = libelle;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
