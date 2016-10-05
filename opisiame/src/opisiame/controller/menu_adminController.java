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
import javafx.stage.*;

/**
 *
 * @author Audrey
 */
public class menu_adminController implements Initializable {

    @FXML
    public void ClicImageOnOff(ActionEvent event) {
        // Button was clicked, do something...
        System.out.println("clic image on_off");;
    }

    @FXML
    public void ClicBoutonEleves(ActionEvent event) {
        // Button was clicked, do something...
        System.out.println("clic bouton eleves");;
    }

    @FXML
    public void ClicBoutonProfs(ActionEvent event) {
        // Button was clicked, do something...
        System.out.println("clic bouton profs");;
    }

    @FXML
    public void ClicBoutonQuiz(ActionEvent event) throws Exception {
        // Button was clicked, do something...
        System.out.println("clic bouton quiz");;
        //ouvre la fenêtre liste_quize (admin)
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/liste_quize.fxml"));
            stage.setTitle("OPI'SIAME");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
