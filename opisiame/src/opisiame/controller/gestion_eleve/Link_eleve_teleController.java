/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller.gestion_eleve;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.comm.CommPortIdentifier;
import opisiame.controller.gestion_quiz.Lancer_questionController;
import opisiame.controller.gestion_quiz.Liste_quizController;
import opisiame.database.Connection_db;
import opisiame.model.Eleve;
import session.Session;

/**
 * FXML Controller class
 *
 * @author Audrey
 */
public class Link_eleve_teleController implements Initializable {

    private ObservableList<Eleve> eleves = FXCollections.observableArrayList();

    @FXML
    private AnchorPane content;
    @FXML
    private Button btn_valider;
    @FXML
    private Button btn_lancer_quiz;
    @FXML
    private TableView<Eleve> Tableau;
    @FXML
    private TableColumn<Eleve, Boolean> Action_col;
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
    @FXML
    private TextField Champ_recherche;
    @FXML
    private ComboBox choix_port;

    private String Cont_recherche = null;
    private Integer quiz_timer;
    private Integer quiz_id;

    public void setQuiz_timer(Integer qt) {
        quiz_timer = qt;
    }

    public void setQuiz_id(Integer qt) {
        quiz_id = qt;
    }

    /*
    Fonction qui récupère la liste des élèves
     */
    public void getAllEleve(/*ObservableList<Eleve> eleves*/) {
        try {
            //ObservableList<Eleve> eleves = FXCollections.observableArrayList();
            Connection connection = Connection_db.getDatabase();
            PreparedStatement requette;

            if (Cont_recherche != null) {
                requette = connection.prepareStatement("SELECT participant.Part_id, participant.Part_nom, participant.Part_prenom, filiere.Filiere, filiere.Annee FROM participant \n"
                        + "LEFT JOIN filiere \n"
                        + "ON filiere.Filiere_ID = participant.Filiere_id\n"
                        + "WHERE participant.Part_id LIKE ?\n"
                        + "OR participant.Part_nom LIKE ?\n"
                        + "OR participant.Part_prenom LIKE ?\n"
                        + "OR filiere.Filiere LIKE ?\n"
                        + "OR filiere.Annee LIKE ?");
                requette.setString(1, "%" + Cont_recherche + "%");
                requette.setString(2, "%" + Cont_recherche + "%");
                requette.setString(3, "%" + Cont_recherche + "%");
                requette.setString(4, "%" + Cont_recherche + "%");
                requette.setString(5, "%" + Cont_recherche + "%");
            } else {
                requette = connection.prepareStatement("SELECT participant.Part_id, participant.Part_nom, participant.Part_prenom, filiere.Filiere, filiere.Annee FROM participant \n"
                        + "LEFT JOIN filiere \n"
                        + "ON filiere.Filiere_ID = participant.Filiere_id");
            }
            ResultSet res_requette = requette.executeQuery();
            while (res_requette.next()) {
                Eleve etudiant = new Eleve();
                etudiant.setId(res_requette.getInt(1));
                etudiant.setNom(res_requette.getString(2));
                etudiant.setPrenom(res_requette.getString(3));
                etudiant.setFiliere(res_requette.getString(4));
                etudiant.setAnnee(res_requette.getInt(5));
                eleves.add(etudiant);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id.setCellValueFactory(new PropertyValueFactory<Eleve, Integer>("id"));
        Nom.setCellValueFactory(new PropertyValueFactory<Eleve, String>("Nom"));
        Prenom.setCellValueFactory(new PropertyValueFactory<Eleve, String>("Prenom"));
        Filiere.setCellValueFactory(new PropertyValueFactory<Eleve, String>("Filiere"));
        Annee.setCellValueFactory(new PropertyValueFactory<Eleve, Integer>("Annee"));
        getAllEleve(/*eleves*/);
        Tableau.setItems(eleves);
        init_liste_port();
    }

    public void init_liste_port() {
        Enumeration pList = CommPortIdentifier.getPortIdentifiers();
        System.out.println("taille liste : "+pList.toString());
        // Process the list.
        while (pList.hasMoreElements()) {
            CommPortIdentifier cpi = (CommPortIdentifier) pList.nextElement();
            System.out.print("Port " + cpi.getName() + " ");
            if (cpi.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                System.out.println("is a Serial Port: " + cpi);
                choix_port.getItems().add(cpi);
            } else if (cpi.getPortType() == CommPortIdentifier.PORT_PARALLEL) {
                System.out.println("is a Parallel Port: " + cpi);
            } else {
                System.out.println("is an Unknown Port: " + cpi);
            }
        }
    }

    public void update_tableau() {
        Tableau.getItems().clear();
        eleves.clear();
        //getAllEleve(/*eleves*/);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/opisiame/view/eleve/Liste_eleves_adminController.fxml"));
        URL url = fxmlLoader.getLocation();
        ResourceBundle rb = fxmlLoader.getResources();
        this.initialize(url, rb);
        Tableau.setItems(eleves);
        Tableau.refresh();
    }

    public void Rechercher() {
        Cont_recherche = Champ_recherche.getText();
        update_tableau();
    }

    @FXML
    public void select_port() {

    }

    //Bouton valider
    @FXML
    public void ClicBoutonValider() throws IOException {
        //ajouter dans l'appli le couple eleve/@mac pour savoir qui a répondu quoi au quiz
    }

    //Bouton valider
    @FXML
    public void ClicBoutonLancerQuiz() throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/opisiame/view/gestion_quiz/lancer_question.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Lancer_questionController lancer_ques_controller = fxmlLoader.<Lancer_questionController>getController();
            lancer_ques_controller.setQuiz_timer(Integer.valueOf(quiz_timer));
            lancer_ques_controller.setQuiz_id(quiz_id);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Animation quiz");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/opisiame/image/icone.png")));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(Liste_quizController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //boutton log out
    @FXML
    public void ClicBoutonHome() throws IOException {
        //Retour sur la fenetre d'identification
        Stage stage = (Stage) content.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/utilisateur/interface_authentification.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        session.Session.Logout();
    }

}
