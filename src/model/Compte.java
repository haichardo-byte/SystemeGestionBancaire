package model;

import exception.MontantInvalideException;
import exception.SoldeInsuffisantException;
import exception.LimiteDepasseeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite représentant un compte bancaire.
 * Elle définit les attributs communs et les opérations de base pour tous les types de comptes.
 */
public abstract class Compte {
    protected String numero;
    protected double solde;
    protected List<Transaction> transactions = new ArrayList<>();

    public Compte(String numero, double soldeInitial) throws MontantInvalideException {
        if(soldeInitial < 0) throw new MontantInvalideException("Solde initial doit être positif");
        this.numero = numero;
        this.solde = soldeInitial;
    }
    /**
     * Retire un montant du compte selon les règles métier.
     *
     * @param montant montant à retirer
     * @throws SoldeInsuffisantException si le solde est insuffisant
     * @throws LimiteDepasseeException si une limite est dépassée (ex: CompteEntreprise)
     */
    public abstract void retirer(double montant)throws SoldeInsuffisantException, LimiteDepasseeException;
    /**
     * Dépose un montant sur le compte.
     *
     * @param montant montant à déposer
     * @throws MontantInvalideException si le montant est invalide
     */
    public abstract void deposer(double montant) throws MontantInvalideException;
    /**
     * Ajoute une transaction à l'historique du compte.
     *
     * @param type type d'opération (DEPOT, RETRAIT, INTERET...)
     * @param montant montant de la transaction
     */
    public void ajouterTransaction(String type, double montant) {
        transactions.add(new Transaction(type, montant, LocalDateTime.now()));
    }
    /**
     * Retourne la liste des transactions du compte.
     *
     * @return liste des transactions
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }
    /**
     * Retourne le solde actuel du compte.
     *
     * @return solde du compte
     */
    public double getSolde() {
        return solde;
    }
    /**
     * Retourne le numéro du compte.
     *
     * @return numéro du compte
     */
    public String getNumero() {
        return numero;
    }
}