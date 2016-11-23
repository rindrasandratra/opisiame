/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller.competence;

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
public class Editer_sous_competenceController implements Initializable {
    
    @FXML
    private AnchorPane content;
    @FXML
    private TextField id;
    @FXML
    private TextField nom;
    @FXML
    private Label label_nom;
    
    private int SousComp_id;

    public void setSousComp_id(int souscompID) {
        this.SousComp_id = souscompID;
    }
    
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Connection connection = Connection_db.getDatabase();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM souscompetence WHERE SousComp_id = ?");
            ps.setInt(1, SousComp_id);
            ResultSet rs = ps.executeQuery();

            //affichage des parametres de l'anim dans les textfield
            while (rs.next()) {
                id.setText(String.valueOf(rs.getInt(1)));
                nom.setText(rs.getString(2));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
        
    
}
