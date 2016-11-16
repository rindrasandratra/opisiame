/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller.competence;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.util.Callback;
import opisiame.database.Connection_db;
import opisiame.model.Competence;

/**
 * FXML Controller class
 *
 * @author Audrey
 */
public class Affichage_sous_competencesController implements Initializable {
    
    
    /*
    Injection des élèments depuis la vue (fichier fxml) dans le code (à partir de fx:id)
     */
    @FXML
    private GridPane content;
    @FXML
    private TableView<Competence> t_liste_souscompetence; // ex: fx:id de la tableView dans FXML builder est aussi t_liste_quiz (c'est pour les lier)
    @FXML
    private TableColumn<Competence, String> nom_souscompetence;
    @FXML
    private TableColumn<Competence, Boolean> action;
    @FXML
    private TableColumn<Competence, Boolean> c_selec;
    @FXML
    private TableColumn<Competence, Integer> id;
    @FXML
    private TextField txt_search;

    private List<Integer> liste_supr = new ArrayList<>();
    private String Cont_recherche = null;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
