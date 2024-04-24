package gestionCompte;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.io.*;

public class CompteManager extends UnicastRemoteObject implements ICompte {
    private static final long serialVersionUID = 1L;
    private Map<String, Compte> comptes = new HashMap<>();
    private static final String FILE_PATH = "resources/comptes.txt"; // Chemin mis à jour

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

    // Méthode pour sauvegarder les comptes dans un fichier de texte
    private void saveComptes() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Map.Entry<String, Compte> entry : comptes.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue().getMdp());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour charger les comptes à partir d'un fichier de texte
    private void loadComptes() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 2) {
                        comptes.put(parts[0], new Compte(parts[0], parts[1]));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}