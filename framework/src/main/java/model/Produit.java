package model;

public class Produit {
    private String libelle;
    private Float prix;

    public Produit(String libelle, Float prix){
        this.libelle = libelle;
        this.prix = prix;
    }

    // getters
    public String getLibelle() { return libelle; }
    public Float getPrix() { return prix; }
    // setters 
    public void setLibelle(String libelle) { this.libelle = libelle; }
    public void setPrix(Float prix) { this.prix = prix; }

}
