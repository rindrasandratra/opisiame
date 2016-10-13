/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller.gestion_eleve;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import opisiame.database.*;
import opisiame.model.Eleve;

/**
 * FXML Controller class
 *
 * @author Audrey
 */
public class Liste_eleves_adminController implements Initializable {

    @FXML
    private AnchorPane content;
    @FXML
    private TableView<Eleve> Tableau;
    @FXML
    private TableColumn<Eleve, Boolean> Action;
    @FXML
    private TableColumn<Eleve, Integer> Identifiant;
    @FXML
    private TableColumn<Eleve, String> Nom;
    @FXML
    private TableColumn<Eleve, String> Prenom;
    @FXML
    private TableColumn<Eleve, String> Filiere;
    @FXML
    private TableColumn<Eleve, Integer> Annee;
    
    
     /*
    Fonction qui récupère la liste des quizs
     */
    public ObservableList<Eleve> getAllEleve() {
        ObservableList<Eleve> eleves = FXCollections.observableArrayList();
        Connection connection = Connection_db.getDatabase();
        Eleve etudiant = new Eleve();
        
        
        
        return eleves;
    }
    
            
       
    
    //Boutton de retour
    @FXML
    public void ClicBoutonRetour() throws IOException {
        //Retour sur la fenetre menu
        Stage stage = (Stage) content.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/utilisateur/menu_admin.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }
    
    @FXML
    public void ClicBoutonHome() throws IOException {
        //Retour sur la fenetre d'identification
        Stage stage = (Stage) content.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/utilisateur/interface_authentification.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
        session.Session.Logout();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

}
