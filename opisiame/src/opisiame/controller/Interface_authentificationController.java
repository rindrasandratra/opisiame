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
    
    public void Submit_passwd(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("opisiame.view.menu_admin.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("menu");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException e) {

        }
    }
}
//coucou