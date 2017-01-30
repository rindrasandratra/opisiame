/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller.gestion_resultat;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author clement
 */
public class Resultat_par_quizController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private AnchorPane content;
    
    int quiz_id;
    String date_part;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setId(int id){
        quiz_id = id;
    }
    
    public void Retour(){
        Stage stage = (Stage)content.getScene().getWindow();
        stage.close();
    }
     public void setDate(String d){
        date_part = d;
    }
}
