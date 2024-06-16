package Entities;

public class Client {
    private int id;
    private String nom;
    private String prenom;
    private double solde;
    private int nbPizza;

    public Client(){};

    public Client(int idClient, String nom, String prenom){}

    public Client(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public int getNbPizza() {
        return nbPizza;
    }

    public void setNbPizza(int nbPizza) {
        this.nbPizza = nbPizza;
    }
}
