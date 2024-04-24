package clientRMI;

public interface ClientInterface {
    boolean creerCompte(String pseudo, String mdp);
    boolean supprimerCompte(String pseudo, String mdp);
    boolean connexion(String pseudo, String mdp);
}
