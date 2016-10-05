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
    private TextField login_field;
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

    private boolean lecture() throws SQLException {
        final String log = login_field.getText();
        final String pass = Passwd_field.getText();
        boolean log_ok = false;
        boolean pass_ok = false;

        //vérification du login
        //connection avec la base de donnée
        Connection database = Connection_db.getDatabase();
        PreparedStatement pslog = database.prepareStatement("SELECT Admin_login FROM administrateur");
        ResultSet info_log = pslog.executeQuery();
        while ((!log.equals(info_log.getString(2))) && info_log.next()) {
            if (!log.equals(info_log.getString(2))) {
                log_ok = true;
            }
        }
        //verification du mot de passe
        PreparedStatement pspass = database.prepareStatement("SELECT Admin_mdp FROM administrateur");
        ResultSet info_pass = pspass.executeQuery();
        while ((!log.equals(info_pass.getString(3))) && info_log.next()) {
            if (!log.equals(info_pass.getString(3))) {
                pass_ok = true;
            }
        }

        return pass_ok;
    }

    //lecture de la base administrateur
    //private 
    @FXML
    public void Submit_passwd() throws IOException {

        Stage stage = (Stage) content.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/menu_admin.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

//coucou
