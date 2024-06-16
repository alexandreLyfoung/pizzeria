package Entities;

import java.util.ArrayList;

public class Pizza {
    private int idPizza;
    private String nom;
    private double prix;
    private ArrayList<Ingredients> listIngredients;

    public Pizza(int idPizza, String nom, double prix) {
        this.idPizza = idPizza;
        this.nom = nom;
        this.prix = prix;
        listIngredients = new ArrayList<>();
    }
    public Pizza()
    {
        listIngredients = new ArrayList<>();
    }

    public int getIdPizza() {
        return idPizza;
    }

    public void setIdPizza(int idPizza) {
        this.idPizza = idPizza;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public ArrayList<Ingredients> getListIngredients() {return listIngredients;}

    public void setListIngredients(ArrayList<Ingredients> listIngredients) {this.listIngredients = listIngredients;

    }

    public void addIngredients(Ingredients ingredients)
    {
        this.listIngredients.add(ingredients);
    }
}
