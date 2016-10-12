/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller;

import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.*;

/**
 *
 * @author Audrey
 */
public class menu_adminController implements Initializable {

    @FXML 
    private AnchorPane content;
    
    @FXML
    public void ClicImageOnOff() {
        // Button was clicked, do something...
        System.out.println("clic image on_off");;
    }

    @FXML
    public void ClicBoutonEleves() {
        // Button was clicked, do something...
        System.out.println("clic bouton eleves");;
    }

    @FXML
    public void ClicBoutonProfs() {
        // Button was clicked, do something...
        System.out.println("clic bouton profs");;
    }

    @FXML
    public void ClicBoutonQuiz() throws Exception {
        //ouvre la fenêtre liste_quize (admin)
        Stage stage = (Stage) content.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/liste_quiz_anim.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

}
