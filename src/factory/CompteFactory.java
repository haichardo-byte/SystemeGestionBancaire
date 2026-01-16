package factory;

import model.Compte;
import model.CompteCourant;
import model.CompteEpargne;
import model.CompteEntreprise;
import exception.MontantInvalideException;
/**
 * Factory pour créer les différents types de comptes.
 */
public class CompteFactory {
    /**
     * Crée un compte selon le type demandé.
     *
     * @param type type de compte (COURANT, EPARGNE, ENTREPRISE)
     * @param numero numéro du compte
     * @param solde solde initial
     * @param param paramètre spécifique (découvert, taux, plafond)
     * @return instance du compte créé
     * @throws MontantInvalideException si le solde initial ou le param est invalide
     */
    public static Compte creerCompte(String type, String numero, double solde, double param) throws MontantInvalideException {
        switch(type.toUpperCase()) {
            case "COURANT": return new CompteCourant(numero, solde, param);
            case "EPARGNE": return new CompteEpargne(numero, solde, param);
            case "ENTREPRISE": return new CompteEntreprise(numero, solde, param);
            default: throw new IllegalArgumentException("Type de compte inconnu");
        }
    }
}