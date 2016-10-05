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
public class Teacher {

    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty nom = new SimpleStringProperty();
    private StringProperty prenom = new SimpleStringProperty();
    private StringProperty login = new SimpleStringProperty();
    private StringProperty passwd = new SimpleStringProperty();

    //Constructeurs
    public Teacher() {
    }

    public Teacher(IntegerProperty ID, StringProperty NOM, StringProperty PRENOM, StringProperty LOGIN, StringProperty PASSWD) {
        id = ID;
        nom = NOM;
        prenom = PRENOM;
        login = LOGIN;
        passwd = PASSWD;
    }

    //accesseur
    public Integer getId() {
        return id.get();
    }
    public String getNom(){
        return nom.get();
    }
    public String getPrenom(){
        return prenom.get();
    }
    public String getlogin(){
        return login.get();
    }
    public String getPasswd(){
        return passwd.get();
    }

}
