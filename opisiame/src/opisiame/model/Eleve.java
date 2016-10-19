/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.model;

import javafx.beans.property.*;

/**
 *
 * @author clement
 */
public class Eleve {

    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty prenom = new SimpleStringProperty();
    private StringProperty nom = new SimpleStringProperty();
    private StringProperty filiere = new SimpleStringProperty();
    private IntegerProperty annee = new SimpleIntegerProperty();

    //Constructeur
    public Eleve(IntegerProperty ID, StringProperty PRENOM, StringProperty NOM, StringProperty FILIERE, IntegerProperty ANNEE) {
        id = ID;
        prenom = PRENOM;
        nom = NOM;
        filiere = FILIERE;
        annee = ANNEE;
    }

    public Eleve() {
    }

    // Pour retourner les attributs
    public Integer getId() {
        return id.get();
    }

    public String getPrenom() {
        return prenom.get();
    }

    public String getNom() {
        return nom.get();
    }

    public String getFiliere() {
        return filiere.get();
    }

    public Integer getAnnee() {
        return annee.get();
    }

    //Pour écrire dans les attributs
    public void setId(Integer ID) {
        this.id.set(ID);
    }

    public void setPrenom(String PRENOM) {
        this.prenom.set(PRENOM);
    }

    public void setNom(String NOM) {
        this.nom.set(NOM);
    }
    
    public void setFiliere(String FILIERE){
        this.filiere.set(FILIERE);
    }
    
    public void setAnnee (Integer ANNEE){
        this.annee.set(ANNEE);
    }
}
