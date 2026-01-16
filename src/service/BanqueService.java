package service;

import model.Compte;
import exception.MontantInvalideException;
import exception.SoldeInsuffisantException;
import exception.LimiteDepasseeException;
/**
 * Service contenant les opérations bancaires générales.
 */
public class BanqueService {

    /**
     * Effectue un virement entre deux comptes.
     *
     * @param source compte source
     * @param destination compte destinataire
     * @param montant montant à virer
     * @throws SoldeInsuffisantException si le solde du compte source est insuffisant
     * @throws MontantInvalideException si le montant est invalide
     * @throws LimiteDepasseeException si une limite est dépassée
     */
    public static void virement(Compte source, Compte destination, double montant)
            throws SoldeInsuffisantException, MontantInvalideException, LimiteDepasseeException {
        source.retirer(montant);
        destination.deposer(montant);
        System.out.println("Virement effectué de " + source.getNumero() + " vers " + destination.getNumero());
    }
}