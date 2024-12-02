package com.example.demo1;

public class Conge {
    private String numero;  // changed from Numero to numero
    private String nom;
    private String prenom;
    private int nbj;  // changed from NBJ to nbj
    private String service;  // changed from Service to service

    // Constructor
    public Conge(String numero, String nom, String prenom, int nbj, String service) {
        this.numero = numero;
        this.nom = nom;
        this.prenom = prenom;
        this.nbj = nbj;
        this.service = service;
    }

    // Default constructor
    public Conge() {}

    // Getters and setters
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getNbj() {
        return nbj;
    }

    public void setNbj(int nbj) {
        this.nbj = nbj;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
