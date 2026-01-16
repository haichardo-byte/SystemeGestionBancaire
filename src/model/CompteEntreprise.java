package model;

import exception.LimiteDepasseeException;
import exception.MontantInvalideException;
import exception.SoldeInsuffisantException;
/**
 * Classe représentant un compte entreprise.
 * Possède un plafond de transaction élevé.
 */
public class CompteEntreprise extends Compte {
    private double plafondTransaction;
    /**
     * Crée un compte entreprise.
     *
     * @param numero numéro du compte
     * @param soldeInitial solde initial
     * @param plafond plafond de transaction
     * @throws MontantInvalideException si le solde initial est négatif
     */
    public CompteEntreprise(String numero, double soldeInitial, double plafond) throws MontantInvalideException {
        super(numero, soldeInitial);
        this.plafondTransaction = plafond;
    }

    @Override
    public void deposer(double montant) throws MontantInvalideException {
        if(montant <= 0) throw new MontantInvalideException("Le dépôt doit être positif");
        solde += montant;
        ajouterTransaction("DEPOT", montant);
    }

    @Override
    public void retirer(double montant) throws SoldeInsuffisantException, LimiteDepasseeException {
        if(montant > plafondTransaction)
            throw new LimiteDepasseeException("Plafond de transaction dépassé");
        if(montant > solde) throw new SoldeInsuffisantException("Solde insuffisant");
        solde -= montant;
        ajouterTransaction("RETRAIT", montant);
    }
}
