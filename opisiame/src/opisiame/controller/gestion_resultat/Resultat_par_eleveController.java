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
import java.util.ArrayList;
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
import opisiame.dao.*;
import opisiame.model.Eleve;
import opisiame.model.Question;
import opisiame.model.Reponse;

/**
 * FXML Controller class
 *
 * @author clement
 */
public class Resultat_par_eleveController implements Initializable {

    Resultat_dao resultat_dao = new Resultat_dao();
    Question_dao question_dao = new Question_dao();
    Reponse_dao reponse_dao = new Reponse_dao();

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
    int participation_id;
    String date_quiz;
    private ObservableList<Eleve> liste_eleves;
    //private ObservableList
    private ArrayList<Question> liste_questions;
    private ArrayList<Reponse> liste_reponses;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        liste_eleves = resultat_dao.get_participants_quiz(quiz_id, date_quiz);

        //remplissage de la combobox avec le nom des élèves
        int taille = liste_eleves.size();
        String NomPrenom = "";
        for (int i = 0; i < taille; i++) {
            NomPrenom = liste_eleves.get(i).getId().toString();// + liste_eleves.get(i).getNom() + " " + liste_eleves.get(i).getPrenom();
            CB_eleves.getItems().add(NomPrenom);
        }
        CB_eleves.getSelectionModel().selectFirst();
    }

    @FXML
    public void BtnValider() throws IOException {
        //récupérer les questions / réponses / réponses de l'élève correspondant au quiz

        // 1 - récupérer les questions du quiz
        liste_questions = question_dao.get_questions_by_quiz(quiz_id);
        int taille = liste_questions.size();
        for (int i = 0; i < taille; i++) {

            char nom_question = 'a';
            int question = liste_questions.get(i).getId();
            liste_reponses = reponse_dao.get_reponses_by_quest(question);

            for (int j = 0; j < 4; j++) {
                liste_reponses.get(j).setLibelle(Character.toString(nom_question));
                if (liste_reponses.get(j).getIs_bonne_reponse() == 1) {
                    liste_reponses.get(j).setIs_bonne_reponse(1);
                }
                nom_question++;
            }
            
            

        }

    }

    public void setId(int id) {
        quiz_id = id;
    }

    public void setDate(String d) {
        date_quiz = d;
    }

    @FXML
    public void ClicBoutonRetour() throws IOException {
        Stage stage = (Stage) content.getScene().getWindow();
        stage.close();
    }

}
