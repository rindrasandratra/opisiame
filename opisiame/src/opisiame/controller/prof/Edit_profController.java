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
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import opisiame.database.Connection_db;
import opisiame.model.Prof;

/**
 * FXML Controller class
 *
 * @author Audrey
 */
public class Edit_profController implements Initializable {

    @FXML
    private AnchorPane content;
    @FXML
    private TextField id;
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField lg;

    //vérifier que le login n'existe pas deja dans la table 
    /**
     * Initializes the controller class.
     */
    private int anim_id;

    public void setAnim_id(int animID) {
        this.anim_id = animID;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            Connection connection = Connection_db.getDatabase();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM animateur WHERE Anim_id = ?");
            ps.setInt(1, anim_id);
            ResultSet rs = ps.executeQuery();

            //affichage des parametres de l'anim dans les textfield
            while (rs.next()) {
                id.setText(String.valueOf(rs.getInt(1)));
                System.out.println(id);
                nom.setText(rs.getString(2));
                prenom.setText(rs.getString(3));
                lg.setText(rs.getString(4));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void btn_valider() throws IOException {

        try {
            //met à jour la base de données
            Connection connection = Connection_db.getDatabase();
            PreparedStatement ps = connection.prepareStatement("UPDATE animateur SET Anim_nom = ?, Anim_prenom = ?, Anim_login = ? WHERE Anim_id = ?");
            ps.setString(1, nom.getText());
            ps.setString(2, prenom.getText());
            ps.setString(3, lg.getText());
            ps.setInt(4, anim_id);
            ps.executeUpdate();
            
            //ferme la fenêtre
            Stage stage = (Stage) content.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/prof/edit_prof.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}
