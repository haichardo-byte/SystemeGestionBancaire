import config.Configuration;
import model.*;
import factory.CompteFactory;
import service.BanqueService;
import exception.*;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        double taux = Configuration.getDouble("tauxInteretEpargne");
        System.out.println(taux);

        try {
            System.out.println("===== SYSTEME DE GESTION BANCAIRE =====");

            System.out.println("--- Création du client ---");
            System.out.print("Nom : ");
            String nom = sc.nextLine();

            System.out.print("Prénom : ");
            String prenom = sc.nextLine();

            System.out.print("CIN : ");
            String cin = sc.nextLine();

            System.out.print("Adresse : ");
            String adresse = sc.nextLine();

            Client client = new Client(nom, prenom, cin, adresse);


            boolean ajouterCompte = true;
            while (ajouterCompte) {

                System.out.println("\n--- Création d'un compte ---");
                System.out.println("Type de compte (COURANT / EPARGNE / ENTREPRISE) : ");
                String type = sc.nextLine().toUpperCase();

                System.out.print("Numéro du compte : ");
                String numero = sc.nextLine();

                System.out.print("Solde initial : ");
                double solde = sc.nextDouble();

                double parametre = 0;

                if (type.equals("COURANT")) {
                    System.out.print("Découvert autorisé : ");
                    parametre = sc.nextDouble();
                } else if (type.equals("EPARGNE")) {
                    System.out.print("Taux d'intérêt (ex: 0.03) : ");
                    parametre = sc.nextDouble();
                } else if (type.equals("ENTREPRISE")) {
                    System.out.print("Plafond de transaction : ");
                    parametre = sc.nextDouble();
                }

                Compte compte = CompteFactory.creerCompte(type, numero, solde, parametre);
                client.ajouterCompte(compte);

                sc.nextLine();

                System.out.print("\nVoulez-vous ajouter un autre compte ? (oui/non) : ");
                String rep = sc.nextLine();
                if (!rep.equalsIgnoreCase("oui")) {
                    ajouterCompte = false;
                }
            }


            boolean quitterApplication = false;

            while (!quitterApplication) {

                System.out.println("\n--- Comptes disponibles ---");
                afficherComptes(client);
                System.out.print("Choisir le compte actif pour les opérations : ");
                int choixCompte = sc.nextInt() - 1;
                Compte compteActif = client.getComptes().get(choixCompte);

                boolean quitterCompte = false;

                while (!quitterCompte) {
                    System.out.println("\n===== MENU COMPTE : " + compteActif.getNumero() + " =====");
                    System.out.println("1. Dépôt");
                    System.out.println("2. Retrait");
                    System.out.println("3. Virement");
                    System.out.println("4. Afficher solde");
                    System.out.println("5. Historique");
                    System.out.println("6. Appliquer intérêts (épargne)");
                    System.out.println("0. Retour à la sélection du compte");

                    System.out.print("Choix : ");
                    int choix = sc.nextInt();

                    switch (choix) {

                        case 1:
                            System.out.print("Montant à déposer : ");
                            compteActif.deposer(sc.nextDouble());
                            System.out.println(" Dépôt effectué");
                            break;

                        case 2:
                            System.out.print("Montant à retirer : ");
                            compteActif.retirer(sc.nextDouble());
                            System.out.println(" Retrait effectué");
                            break;

                        case 3:
                            if (client.getComptes().size() < 2) {
                                System.out.println(" Au moins deux comptes sont nécessaires");
                                break;
                            }

                            System.out.println("\n--- Choisir le compte destinataire ---");
                            afficherComptes(client);
                            int dest = sc.nextInt() - 1;

                            if (dest == choixCompte) {
                                System.out.println(" Comptes identiques");
                                break;
                            }

                            System.out.print("Montant à virer : ");
                            double montantV = sc.nextDouble();

                            BanqueService.virement(
                                    compteActif,
                                    client.getComptes().get(dest),
                                    montantV
                            );

                            System.out.println(" Virement effectué");
                            break;

                        case 4:
                            System.out.println(" Solde actuel : " + compteActif.getSolde());
                            break;

                        case 5:
                            System.out.println(" Historique des transactions :");
                            for (Transaction t : compteActif.getTransactions()) {
                                System.out.println(
                                        t.getDate() + " | " + t.getType() + " | " + t.getMontant()
                                );
                            }
                            break;

                        case 6:
                            if (compteActif instanceof CompteEpargne) {
                                double soldeAvant = compteActif.getSolde();
                                ((CompteEpargne) compteActif).appliquerInteret();
                                double interet = compteActif.getSolde() - soldeAvant;
                                System.out.println(" Intérêts appliqués : " + interet);
                                System.out.println(" Nouveau solde : " + compteActif.getSolde());
                            } else {
                                System.out.println(" Ce compte n’a pas d’intérêts");
                            }
                            break;

                        case 0:
                            quitterCompte = true;
                            break;

                        default:
                            System.out.println(" Choix invalide");
                    }
                }

                System.out.print("\nVoulez-vous sélectionner un autre compte ? (oui/non) : ");
                sc.nextLine(); // nettoyage buffer
                String rep = sc.nextLine();
                if (!rep.equalsIgnoreCase("oui")) {
                    quitterApplication = true;
                }
            }

        } catch (MontantInvalideException |
                 SoldeInsuffisantException |
                 LimiteDepasseeException e) {

            System.out.println(" ERREUR : " + e.getMessage());
        }

        sc.close();
    }

    private static void afficherComptes(Client client) {
        int i = 1;
        for (Compte c : client.getComptes()) {
            System.out.println(i + ". " + c.getNumero() +
                    " | " + c.getClass().getSimpleName() +
                    " | Solde : " + c.getSolde());
            i++;
        }
    }
}