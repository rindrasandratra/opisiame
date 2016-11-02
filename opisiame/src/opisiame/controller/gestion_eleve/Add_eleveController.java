/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller.gestion_eleve;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.*;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import opisiame.database.*;

/**
 * FXML Controller class
 *
 * @author clement
 */
public class Add_eleveController implements Initializable {

    @FXML
    private AnchorPane content;
    @FXML
    private TextField Edit_Nom;
    @FXML
    private TextField Edit_Prenom;
    @FXML
    private ComboBox Choix_Flilere;
    @FXML
    private ComboBox Choix_annee;
    @FXML
    private Label PasOkNom;
    @FXML
    private Label PasOkFiliere;
    @FXML
    private Label PasOkAnnee;
    @FXML
    private Label PasOkPrenom;

    private List<String> liste_Filiere = new ArrayList<>();//contient les champs filiere pour les combobox
    private List<Integer> liste_Annee = new ArrayList<>(); //contient les champs Année pour les combobox

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        remplissage_filiere();
        remplissage_annee();

    }

    //remplissage combobox filiere
    void remplissage_filiere() {
        Connection database = Connection_db.getDatabase();
        PreparedStatement req;
        try {
            req = database.prepareStatement("SELECT Filiere From filiere");
            ResultSet res = req.executeQuery();
            while (res.next()) {
                String fil = res.getString(1);
                liste_Filiere.add(fil);
                for (int i = 0; i < liste_Filiere.size(); ++i) {
                    //remplissage du combobox
                    if (!Choix_Flilere.getItems().contains(liste_Filiere.get(i))) {
                        Choix_Flilere.getItems().add(liste_Filiere.get(i));
                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Add_eleveController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //remplissage combobox annee
    void remplissage_annee() {
        Connection database = Connection_db.getDatabase();
        PreparedStatement req;
        try {
            req = database.prepareStatement("SELECT Annee From filiere");
            ResultSet res = req.executeQuery();
            while (res.next()) {
                Integer ann = res.getInt(1);
                liste_Annee.add(ann);
                for (int i = 0; i < liste_Annee.size(); ++i) {
                    //remplissage du combobox
                    if (!Choix_annee.getItems().contains(liste_Annee.get(i))) {
                        System.out.println(liste_Annee.get(i));
                        Choix_annee.getItems().add(liste_Annee.get(i));
                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Add_eleveController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void Clic_Valider() throws IOException {
        int ok = 1;
        if (Edit_Nom.getText().equals("")) {
            PasOkNom.setText("*");
            ok = 0;
        }
        
        if (Choix_Flilere.getSelectionModel().isEmpty()) {
            PasOkFiliere.setText("*");
            ok = 0;
        }
        if (Choix_annee.getSelectionModel().isEmpty()) {
            PasOkAnnee.setText("*");
            ok = 0;
        }
        
        if (Edit_Prenom.getText().equals("")){
            PasOkPrenom.setText("*");
            ok = 0;
        }
        //System.out.println(Choix_Flilere.getValue().toString());
    }
}
