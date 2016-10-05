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
import javafx.stage.*;

/**
 *
 * @author Audrey
 */
public class menu_adminController implements Initializable {

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

}
