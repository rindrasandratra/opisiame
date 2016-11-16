/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller.prof;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import opisiame.database.Connection_db;

/**
 * FXML Controller class
 *
 * @author Audrey
 */
public class Ajout_profController implements Initializable {

    //injection des elements graphiques
    @FXML
    private AnchorPane content;
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField lg;
    @FXML
    private TextField mdp;
    @FXML
    private Label label_nom;
    @FXML
    private Label label_lg;
    @FXML
    private Label label_mdp;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void btn_valider() {

        label_nom.setText("");
        label_lg.setText("");
        label_mdp.setText("");

        String nom = this.nom.getText();
        String prenom = this.prenom.getText();
        String lg = this.lg.getText();
        String mdp = this.mdp.getText();
        Boolean champ_ok = true;
        int c1 = 0;
        int c2 = 0;

        //verif que le nom est bien rempli
        if (nom.equals("")) {
            label_nom.setText("champ requis");
            champ_ok = false;
        }

        //verif que le login est bien rempli
        if (lg.equals("")) {
            label_lg.setText("champ requis");
            champ_ok = false;
        }

        //verif que le login n'est pas deja utilise
        try {
            Connection connection = Connection_db.getDatabase();

            PreparedStatement ps1 = connection.prepareStatement("SELECT COUNT(*) AS total FROM animateur WHERE Anim_login = ?");
            ps1.setString(1, lg);
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                c1 = rs1.getInt("total");
            }

            PreparedStatement ps2 = connection.prepareStatement("SELECT COUNT(*) AS total FROM administrateur WHERE Admin_login = ?");
            ps2.setString(1, lg);
            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                c2 = rs2.getInt("total");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (c1 != 0 || c2 != 0) {
            label_lg.setText("login non disponible");
            champ_ok = false;
        }

        //verif que le mdp est bien rempli
        if (mdp.equals("")) {
            label_mdp.setText("champ requis");
            champ_ok = false;
        }

        //si tous les champs sont ok, ajout de l'animateur dans la bdd
        if (champ_ok == true) {
            label_nom.setText("");
            label_lg.setText("");
            label_mdp.setText("");
            insert_new_anim(nom, prenom, lg, mdp);

            //ou ouvre la fenêtre liste_profs_admin
            //ouverture fenêtre menu_anim            
            Stage stage = (Stage) content.getScene().getWindow();
            stage.close();

        }

    }

    public void insert_new_anim(String nom, String prenom, String lg, String mdp) {
        try {
            Connection connection = Connection_db.getDatabase();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO animateur (Anim_nom,Anim_prenom, Anim_login, Anim_mdp) VALUES (?,?,?,?)");
            ps.setString(1, nom);
            ps.setString(2, prenom);
            ps.setString(3, lg);
            ps.setString(4, mdp);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}