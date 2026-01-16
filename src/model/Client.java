package model;

import java.util.ArrayList;
import java.util.List;
/**
 * Classe représentant un client bancaire.
 * Un client peut posséder plusieurs comptes.
 */
public class Client {
    private String nom;
    private String prenom;
    private String CIN;
    private String adresse;
    private List<Compte> comptes = new ArrayList<>();
    /**
     * Crée un client avec ses informations personnelles.
     *
     * @param nom nom du client
     * @param prenom prénom du client
     * @param CIN numéro de CIN
     * @param adresse adresse du client
     */
    public Client(String nom, String prenom, String CIN, String adresse) {
        this.nom = nom;
        this.prenom = prenom;
        this.CIN = CIN;
        this.adresse = adresse;
    }
    /**
     * Ajoute un compte au client.
     *
     * @param c compte à ajouter
     */
    public void ajouterCompte(Compte c) {
        comptes.add(c);
    }
    /**
     * Retourne la liste des comptes du client.
     *
     * @return liste des comptes
     */
    public List<Compte> getComptes() {
        return comptes;
    }
}