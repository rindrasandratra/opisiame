/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller.gestion_resultat;

import java.io.IOException;
import java.net.URL;
//import java.util.*;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import opisiame.dao.Resultat_dao;
import opisiame.model.Eleve;

/**
 * FXML Controller class
 *
 * @author clement
 */
public class Resultat_par_eleveController implements Initializable {

    Resultat_dao resultat_dao = new Resultat_dao();
    
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
    Date date_quiz;
    private ObservableList<Eleve> liste_eleves;
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        liste_eleves = resultat_dao.get_participants_quiz(quiz_id,date_quiz);
        CB_eleves.getItems().addAll(liste_eleves);
        CB_eleves.getSelectionModel().selectFirst();
    }    
    
    public void setId(int id){
        quiz_id = id;
    }
    
    public void setDate (Date d){
        date_quiz = d;
    }
    
    @FXML
    public void ClicBoutonRetour() throws IOException {
        Stage stage = (Stage) content.getScene().getWindow();
        stage.close();
    }

}
