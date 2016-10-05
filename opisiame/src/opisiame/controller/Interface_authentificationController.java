/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller;

import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.shape.*;
import javafx.stage.*;
import opisiame.database.*;
import opisiame.model.*;

/**
 * FXML Controller class
 *
 * @author clement
 */
public class Interface_authentificationController implements Initializable {

    @FXML
    private TextField login_field;
    @FXML
    private PasswordField Passwd_field;
    @FXML
    private Label Message_field;
    @FXML
    private Polygon Submit_field;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void Submit_passwd() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Interface_authentificationController.class.getResource("/opisiame/view/liste_quize.fxml"));
        Parent root;
        root = (Parent) loader.load();
        Stage stage = new Stage();
        stage.setTitle("OPI'SIAME");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/opisiame/image/icone.png")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

//coucou
