/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.shape.*;
import opisiame.database.Connection_db;

/**
 * FXML Controller class
 *
 * @author clement
 */
public class Interface_authentificationController implements Initializable {

    @FXML
    private AnchorPane content;
    @FXML
    private TextField Login_field;
    @FXML
    private PasswordField Passwd_field;
    @FXML
    private Label Message_field;
    @FXML
    private Polygon Submit_field;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private boolean lecture_admin() throws SQLException {
        final String log = Login_field.getText();
        final String pass = Passwd_field.getText();
        int count = 0;
        boolean ok = false;

        //vérification du login
        //connection avec la base de donnée
        Connection database = Connection_db.getDatabase();
        PreparedStatement pslog = database.prepareStatement("SELECT COUNT(*) AS total FROM administrateur WHERE Admin_login = ? and Admin_mdp = ?");
        pslog.setString(1, log);
        pslog.setString(2, pass);
        ResultSet logres = pslog.executeQuery();
        while (logres.next()){
            count = logres.getInt("total");
        }
        if (count == 1){ok = true;}
        
       return ok;
    }

    //lecture de la base administrateur
    //private 
    @FXML
    public void Submit_passwd() throws IOException, SQLException {

        if (lecture_admin() == true) {

            Stage stage = (Stage) content.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/menu_admin.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else Message_field.setText("erreur d'authentification");
    }
}

//coucou
