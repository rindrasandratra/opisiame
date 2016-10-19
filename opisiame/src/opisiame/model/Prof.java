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
public class Prof {

    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty nom = new SimpleStringProperty();
    private StringProperty prenom = new SimpleStringProperty();
    private StringProperty login1;
    private StringProperty passwd = new SimpleStringProperty();

    //Constructeurs
    public Prof() {
    }

    public Prof(IntegerProperty ID, StringProperty NOM, StringProperty PRENOM, StringProperty LOGIN, StringProperty PASSWD) {
        this.id = ID;
        this.nom = NOM;
        this.prenom = PRENOM;
        this.login1 = LOGIN;
        this.passwd = PASSWD;
    }

    //accesseurs
    public Integer getId() {
        return id.get();
    }    
    public void setId(Integer id) {
        this.id.set(id);
    }
    
    public String getNom(){
        return nom.get();
    }    
    public void setNom(String nom) {
        this.nom.set(nom);
    }
    
    public String getPrenom(){
        return prenom.get();
    }
    public void setPrenom(String prenom) {
        this.prenom.set(prenom);
    }
    
    public String getlogin(){
        return login1.get();
    }
    public void setLogin(String login) {
        login1 = new SimpleStringProperty();
        this.login1.set(login);
    }
    
    public String getPasswd(){
        return passwd.get();
    }
    public void setPasswd(String Passwd) {
        this.passwd.set(Passwd);
    }

}
