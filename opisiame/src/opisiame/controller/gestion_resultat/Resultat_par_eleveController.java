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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import opisiame.dao.*;
import opisiame.database.Connection_db;
import opisiame.model.Eleve;
import opisiame.model.Question;
import opisiame.model.Reponse;
import opisiame.model.Reponse_eleve_quiz;

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
    private Label label_note;
    @FXML
    private ComboBox CB_eleves;
    @FXML
    private TabPane onglets;
    @FXML
    private Tab onglet_c;
    @FXML
    private Tab onglet_q;
    @FXML
    private TableView<Reponse_eleve_quiz> tab_comp;
    @FXML
    private TableView<Reponse_eleve_quiz> tab_question;
    @FXML
    private TableColumn<Reponse_eleve_quiz, Integer> num_question;
    @FXML
    private TableColumn<Reponse_eleve_quiz, String> c_bonne_r;
    @FXML
    private TableColumn<Reponse_eleve_quiz, String> c_r_eleve;
    @FXML
    private TableColumn<Reponse_eleve_quiz, String> c_comp;
    @FXML
    private TableColumn<Reponse_eleve_quiz, String> c_pourcent;

    int quiz_id;
    int participation_id;
    int nbre_questions;
    int nbre_bonnes_rep;
    String date_quiz;
    String nom_quiz;
    private ObservableList<Eleve> liste_eleves;
    //private ObservableList
    private ArrayList<Question> liste_questions;
    private ArrayList<Reponse> liste_reponses;
    private ArrayList<Reponse> liste_reponses_eleve;
    private ObservableList<Reponse_eleve_quiz> a_afficher = FXCollections.observableArrayList();

    public void setId(int id) {
        quiz_id = id;
    }

    public void setDate(String d) {
        date_quiz = d;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        nbre_bonnes_rep = 0;

        num_question.setCellValueFactory(new PropertyValueFactory<Reponse_eleve_quiz, Integer>("num_ques"));
        c_bonne_r.setCellValueFactory(new PropertyValueFactory<Reponse_eleve_quiz, String>("rep_quiz"));
        c_r_eleve.setCellValueFactory(new PropertyValueFactory<Reponse_eleve_quiz, String>("rep_eleve"));

        try {
            Connection connection = Connection_db.getDatabase();
            PreparedStatement ps;
            ps = connection.prepareStatement("SELECT Quiz_nom "
                    + "FROM quiz \n"
                    + "WHERE Quiz_id LIKE ?\n");
            ps.setInt(1, quiz_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nom_quiz = rs.getString(1);
            };

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        label_quiz.setText(nom_quiz);

        liste_eleves = resultat_dao.get_participants_quiz(quiz_id, date_quiz);

        //remplissage de la combobox avec le nom des élèves
        int taille = liste_eleves.size();
        String NomPrenom = "";
        for (int i = 0; i < taille; i++) {
            NomPrenom = liste_eleves.get(i).getId().toString();
            CB_eleves.getItems().add(NomPrenom);
        }
        CB_eleves.getSelectionModel().selectFirst();
    }

    @FXML
    public void BtnValider() throws IOException {
        
        tab_question.getItems().clear();
        tab_question.setItems(Reponses());        

        double note = (nbre_bonnes_rep / (float)nbre_questions) * 20.0;
        label_note.setText("Note : " + note + "/20");
    }

    public ObservableList<Reponse_eleve_quiz> Reponses() {
        //récupération du numéro éudiant (part_id), et du participation_id
        try {
            Connection connection = Connection_db.getDatabase();
            PreparedStatement ps;
            ps = connection.prepareStatement("SELECT Participation_id "
                    + "FROM participant_quiz \n"
                    + "WHERE Part_id LIKE ?\n");
            ps.setInt(1, Integer.parseInt(CB_eleves.getSelectionModel().getSelectedItem().toString()));
            ResultSet rs = ps.executeQuery();
            rs.next();
            participation_id = rs.getInt(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        //récupérer les questions / réponses / réponses de l'élève correspondant au quiz
        // 1 - récupérer les questions du quiz
        liste_questions = question_dao.get_questions_by_quiz(quiz_id);
        nbre_questions = liste_questions.size();
        for (int i = 0; i < nbre_questions; i++) {

            Reponse_eleve_quiz afficher = new Reponse_eleve_quiz();
            afficher.setNum_ques(Integer.valueOf(i + 1));

            //recherche des réponses de l'élève
            liste_reponses_eleve = reponse_dao.get_reponses_eleve(participation_id);

            //récupération de toutes les réponses des questions du quiz
            char nom_question = 'a';
            int question = liste_questions.get(i).getId();
            liste_reponses = reponse_dao.get_reponses_by_quest(question);

            //recherche de la bonne reponse
            for (int j = 0; j < 4; j++) {

                if (liste_reponses.get(j).getIs_bonne_reponse() == 1) {
                    afficher.setRep_quiz(Character.toString(nom_question));
                }

                if (liste_reponses.get(j).getId().equals(liste_reponses_eleve.get(i).getId())) {
                    afficher.setRep_eleve(Character.toString(nom_question));
                }
               
                nom_question++;
            }
            
            if (afficher.getRep_eleve().equals(afficher.getRep_quiz())) {
                nbre_bonnes_rep = nbre_bonnes_rep+1;
            }     
            
            a_afficher.add(afficher);
        }

        return a_afficher;
    }

    @FXML
    public void ClicBoutonRetour() throws IOException {
        Stage stage = (Stage) content.getScene().getWindow();
        stage.close();
    }

}
