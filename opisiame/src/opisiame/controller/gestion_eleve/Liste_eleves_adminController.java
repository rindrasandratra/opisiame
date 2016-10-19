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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
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
    private TableColumn<Eleve, Integer> id;
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
        try {
            ObservableList<Eleve> eleves = FXCollections.observableArrayList();
            Connection connection = Connection_db.getDatabase();
            PreparedStatement requette = connection.prepareStatement("SELECT participant.Part_id, participant.Part_nom, participant.Part_prenom, filiere.Filiere, filiere.Annee FROM participant LEFT JOIN filiere ON filiere.Filiere_ID = participant.Filiere_id");
            ResultSet res_requette = requette.executeQuery();
            while (res_requette.next()) {
                Eleve etudiant = new Eleve();
                etudiant.setId(res_requette.getInt(1));
                etudiant.setNom(res_requette.getString(2));
                etudiant.setPrenom(res_requette.getString(3));
                etudiant.setFiliere(res_requette.getString(4));
                etudiant.setAnnee(res_requette.getInt(5));
                eleves.add(etudiant);
                System.out.println(etudiant.getId());
            }

            return eleves;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id.setCellValueFactory(new PropertyValueFactory<Eleve, Integer>("id"));
        Nom.setCellValueFactory(new PropertyValueFactory<Eleve, String>("Nom"));
        Prenom.setCellValueFactory(new PropertyValueFactory<Eleve, String>("Prenom"));
        Filiere.setCellValueFactory(new PropertyValueFactory<Eleve, String>("Filiere"));
        Annee.setCellValueFactory(new PropertyValueFactory<Eleve, Integer>("Annee"));
        Tableau.setItems(getAllEleve());

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

}
