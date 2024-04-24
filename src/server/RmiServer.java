package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import gestionCompte.CompteManager;

public class RmiServer {
    public static void main(String[] args) {
        try {
            // Crée et exporte une instance de l'objet registre sur le port spécifié.
            Registry registry = LocateRegistry.createRegistry(1099);

            // Crée l'instance de CompteManager qui est l'objet distant.
            CompteManager manager = new CompteManager();

            // Enregistre l'objet distant dans le registre RMI sous le nom "CompteManager".
            registry.rebind("CompteManager", manager);

            System.out.println("Serveur RMI prêt.");
        } catch (RemoteException e) {
            System.err.println("Exception lors du lancement du serveur RMI: " + e);
            e.printStackTrace();
        }
    }
}
