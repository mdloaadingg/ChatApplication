package clientRMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import gestionCompte.ICompte;

public class Client implements ClientInterface {
    private ICompte compteManager;

    public Client(String host, int port) {
        try {
            Registry registry = LocateRegistry.getRegistry(host, port);
            compteManager = (ICompte) registry.lookup("CompteManager");
            System.out.println("Connecté au serveur RMI.");
        } catch (Exception e) {
            System.err.println("Exception client RMI: " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public boolean creerCompte(String pseudo, String mdp) {
        try {
            return compteManager.creerCompte(pseudo, mdp);
        } catch (Exception e) {
            System.err.println("Erreur lors de la création du compte: " + e.toString());
            return false;
        }
    }

    @Override
    public boolean supprimerCompte(String pseudo, String mdp) {
        try {
            return compteManager.supprimerCompte(pseudo, mdp);
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression du compte: " + e.toString());
            return false;
        }
    }

    @Override
    public boolean connexion(String pseudo, String mdp) {
        try {
            return compteManager.connexion(pseudo, mdp);
        } catch (Exception e) {
            System.err.println("Erreur lors de la connexion: " + e.toString());
            return false;
        }
    }
}
