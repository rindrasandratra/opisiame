/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller.gestion_resultat;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author clement
 */
public class Resultat_par_eleveController implements Initializable {

    @FXML
    private GridPane content;
    @FXML
    private Label label_quiz;
    @FXML
    private ComboBox CB_eleves;
    @FXML
    private TabPane onglets;
    @FXML
    private Tab onglet_c;
    @FXML
    private Tab onglet_q;
    @FXML
    private TableView tab_comp;
    @FXML
    private TableView tab_question;
    @FXML
    private TableColumn c_question;
    @FXML
    private TableColumn c_bonne_r;
    @FXML
    private TableColumn c_r_eleve;
    @FXML
    private TableColumn c_comp;
    @FXML
    private TableColumn c_pourcent;
    
    int quiz_id;
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setId(int id){
        quiz_id = id;
    }
    
    @FXML
    public void ClicBoutonRetour() throws IOException {
        Stage stage = (Stage) content.getScene().getWindow();
        stage.close();
    }

}
