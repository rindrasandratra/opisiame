/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller.gestion_resultat;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author clement
 */
public class Choix_resultatController implements Initializable {

    
    
    private int quiz_id;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.print("constructeur appellé \n");
        System.out.print("id de l'objet " + quiz_id +"\n");
    }    
 
    
    
    public void setId(int id){
        quiz_id = id;
    }
}
