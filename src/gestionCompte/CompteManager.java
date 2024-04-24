package gestionCompte;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.io.*;

public class CompteManager extends UnicastRemoteObject implements ICompte {
    private Map<String, Compte> comptes = new HashMap<>();
    private static final String FILE_PATH = "resources/comptes.ser"; // Chemin mis à jour

    public CompteManager() throws RemoteException {
        super();
        loadComptes(); // Charge les comptes à la création de l'instance
    }

    @Override
    public boolean creerCompte(String pseudo, String mdp) throws RemoteException {
        if (comptes.containsKey(pseudo)) {
            return false;
        }
        comptes.put(pseudo, new Compte(pseudo, mdp));
        saveComptes(); // Sauvegarde après chaque création
        return true;
    }

    @Override
    public boolean supprimerCompte(String pseudo, String mdp) throws RemoteException {
        Compte compte = comptes.get(pseudo);
        if (compte != null && compte.getMdp().equals(mdp)) {
            comptes.remove(pseudo);
            saveComptes(); // Sauvegarde après chaque suppression
            return true;
        }
        return false;
    }

    @Override
    public boolean connexion(String pseudo, String mdp) throws RemoteException {
        Compte compte = comptes.get(pseudo);
        return compte != null && compte.getMdp().equals(mdp);
    }

    // Méthode pour sauvegarder les comptes dans un fichier
    private void saveComptes() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            out.writeObject(comptes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour charger les comptes à partir d'un fichier
    private void loadComptes() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                comptes = (Map<String, Compte>) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
