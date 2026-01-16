package model;

import exception.MontantInvalideException;
import exception.SoldeInsuffisantException;
/**
 * Classe représentant un compte courant.
 * Un découvert autorisé peut être défini.
 */
public class CompteCourant extends Compte {
    private double decouvertAutorise;
    /**
     * Crée un compte courant.
     *
     * @param numero numéro du compte
     * @param soldeInitial solde initial
     * @param decouvertAutorise montant de découvert autorisé
     * @throws MontantInvalideException si le solde initial est négatif
     */
    public CompteCourant(String numero, double soldeInitial, double decouvertAutorise) throws MontantInvalideException {
        super(numero, soldeInitial);
        this.decouvertAutorise = decouvertAutorise;
    }

    @Override
    public void deposer(double montant) throws MontantInvalideException {
        if(montant <= 0) throw new MontantInvalideException("Le dépôt doit être positif");
        solde += montant;
        ajouterTransaction("DEPOT", montant);
    }

    @Override
    public void retirer(double montant) throws SoldeInsuffisantException {
        if(solde - montant < -decouvertAutorise)
            throw new SoldeInsuffisantException("Découvert autorisé dépassé");
        solde -= montant;
        ajouterTransaction("RETRAIT", montant);
    }
}