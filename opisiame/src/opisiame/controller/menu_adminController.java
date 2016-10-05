/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 *
 * @author Audrey
 */
public class menu_adminController implements Initializable{

    @FXML
 private void ClicImageOnOff(ActionEvent event) {
     // Button was clicked, do something...
        System.out.println("clic image on_off");;
 }
 
 @FXML
 private void ClicBoutonEleves(ActionEvent event) {
     // Button was clicked, do something...
        System.out.println("clic bouton eleves");;
 }
 
 @FXML
 private void ClicBoutonProfs(ActionEvent event) {
     // Button was clicked, do something...
        System.out.println("clic bouton profs");;
 }
 
 @FXML
 private void ClicBoutonQuiz(ActionEvent event) {
     // Button was clicked, do something...
        System.out.println("clic bouton quiz");;
 }
   
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
}
