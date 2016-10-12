/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller.utilisateur;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.*;
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
    public void ClicImageOnOff() throws IOException {
        //remise à zéro des variables d'identification (login + mdp)
        session.Session.Logout();
        //ouvre la fenêtre Interface_authentification
        Stage stage = (Stage) content.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/utilisateur/interface_authentification.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }
    
    @FXML
    public void ClicBoutonParam() throws IOException {
        //ouvre la fenêtre ModifParam
        Stage stage = (Stage) content.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/ModifParam.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    @FXML
    public void ClicBoutonEleves() throws IOException{
        //ouvre la fenêtre liste_eleves_admin
        Stage stage = (Stage) content.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/gestion_eleve/liste_eleves_admin.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    @FXML
    public void ClicBoutonProfs() throws IOException{
        //ouvre la fenêtre liste_profs_admin
        Stage stage = (Stage) content.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/prof/liste_profs_admin.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    @FXML
    public void ClicBoutonQuiz() throws IOException {
        //ouvre la fenêtre liste_quiz (admin)
        Stage stage = (Stage) content.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/gestion_quiz/liste_quiz.fxml")); //à modifier
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

}
