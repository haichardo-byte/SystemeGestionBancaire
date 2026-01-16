package model;

import java.time.LocalDateTime;
/**
 * Classe représentant une transaction bancaire.
 * Contient le type, le montant et la date.
 */
public class Transaction {
    private String type;
    private double montant;
    private LocalDateTime date;
    /**
     * Crée une transaction.
     *
     * @param type type d'opération (DEPOT, RETRAIT, INTERET...)
     * @param montant montant de la transaction
     * @param date date et heure de la transaction
     */
    public Transaction(String type, double montant, LocalDateTime date) {
        this.type = type;
        this.montant = montant;
        this.date = date;
    }

    public String getType() {
        return type;
    }
    public double getMontant() {
        return montant;
    }
    public LocalDateTime getDate() {
        return date;
    }
}