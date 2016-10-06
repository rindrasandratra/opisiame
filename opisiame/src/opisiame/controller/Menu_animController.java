/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.*;

/**
 * FXML Controller class
 *
 * @author Audrey
 */
public class Menu_animController implements Initializable {

    @FXML 
    private AnchorPane content;
    
    @FXML
    public void ClicImageOnOff() {
        // Button was clicked, do something...
        System.out.println("clic image on_off");;
    }
    
    @FXML
    public void ClicBoutonParam() {
        // Button was clicked, do something...
        System.out.println("clic image Param");;
    }

    @FXML
    public void ClicBoutonNouveauQuiz() throws IOException{
        //ouvre la fenêtre liste_eleves_admin
        Stage stage = (Stage) content.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/liste_eleves_admin.fxml")); //à modifier
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    @FXML
    public void ClicBoutonRésultatsQuiz() throws IOException{
        //ouvre la fenêtre liste_profs_admin
        Stage stage = (Stage) content.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/liste_profs_admin.fxml")); //à modifier
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    @FXML
    public void ClicBoutonListeQuiz() throws IOException {
        //ouvre la fenêtre liste_quiz (admin)
        Stage stage = (Stage) content.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/liste_quiz.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

}
