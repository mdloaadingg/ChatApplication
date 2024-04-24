package Gestioncompte;

import java.io.Serializable;

public class Compte implements Serializable {
    private String pseudo;
    private String mdp; // mot de passe

    public Compte(String pseudo, String mdp) {
        this.pseudo = pseudo;
        this.mdp = mdp;
    }

    // Getters et setters
    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
}
