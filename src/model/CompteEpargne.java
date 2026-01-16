package model;

import exception.MontantInvalideException;
import exception.SoldeInsuffisantException;
import strategy.CalculInteret;
/**
 * Classe représentant un compte épargne.
 * Possède un taux d'intérêt et ne permet pas de découvert.
 */
public class CompteEpargne extends Compte implements CalculInteret {
    private double tauxInteret;
    /**
     * Crée un compte épargne.
     *
     * @param numero numéro du compte
     * @param soldeInitial solde initial
     * @param tauxInteret taux d'intérêt (>=0)
     * @throws MontantInvalideException si le taux est négatif
     */
    public CompteEpargne(String numero, double soldeInitial, double tauxInteret) throws MontantInvalideException {
        super(numero, soldeInitial); // on laisse le solde comme avant

        if (tauxInteret < 0) {
            throw new MontantInvalideException("Taux d'intérêt invalide (doit être >= 0)");
        }

        this.tauxInteret = tauxInteret;
    }

    @Override
    public void deposer(double montant) throws MontantInvalideException {
        if (montant <= 0) throw new MontantInvalideException("Le dépôt doit être positif");
        solde += montant;
        ajouterTransaction("DEPOT", montant);
    }

    @Override
    public void retirer(double montant) throws SoldeInsuffisantException {
        if (montant > solde) throw new SoldeInsuffisantException("Solde insuffisant");
        solde -= montant;
        ajouterTransaction("RETRAIT", montant);
    }

    @Override
    public void appliquerInteret() {
        double interet = solde * tauxInteret;
        solde += interet;
        ajouterTransaction("INTERET", interet);
    }

    public double getTauxInteret() {

        return tauxInteret;
    }

    public void setTauxInteret(double tauxInteret) throws MontantInvalideException {
        if (tauxInteret < 0 ) {
            throw new MontantInvalideException("Taux d'intérêt invalide (doit être >= 0)");
        }
        this.tauxInteret = tauxInteret;
    }
}