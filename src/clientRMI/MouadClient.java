package clientRMI;

	import java.rmi.registry.LocateRegistry;
	import java.rmi.registry.Registry;
	import gestionCompte.ICompte;

	public class MouadClient {
	    public static void main(String[] args) {
	        try {
	            // Récupère le registre RMI où le service est enregistré
	            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

	            // Recherche le service CompteManager par son nom
	            ICompte compteManager = (ICompte) registry.lookup("CompteManager");

	            // Test de création de compte
	            boolean isCreated = compteManager.creerCompte("mouad", "password123");
	            System.out.println("Création de compte réussie pour Mouad: " + isCreated);

	            // Test de connexion
	            boolean isConnected = compteManager.connexion("mouad", "password123");
	            System.out.println("Connexion réussie pour Mouad: " + isConnected);

	            // Test de suppression de compte
	            boolean isDeleted = compteManager.supprimerCompte("mouad", "password123");
	            System.out.println("Suppression de compte réussie pour Mouad: " + isDeleted);
	            
	         // Test de recréation de compte
	            boolean isReCreated = compteManager.creerCompte("mouad", "password123");
	            System.out.println("Création de compte réussie pour Mouad: " + isReCreated);
	            
	        } catch (Exception e) {
	            System.err.println("Client exception: " + e.toString());
	            e.printStackTrace();
	        }
	    }
	}
	
	


