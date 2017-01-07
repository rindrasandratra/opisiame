/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller.gestion_resultat;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import opisiame.controller.gestion_quiz.Liste_quizController;
import opisiame.dao.Participation_quiz_dao;
import opisiame.dao.Question_dao;
import opisiame.dao.Quiz_dao;
import opisiame.dao.Reponse_question_dao;
import opisiame.model.Participation_quiz;
import opisiame.model.Question;
import opisiame.model.Quiz;
import opisiame.model.Reponse_question;

/**
 * FXML Controller class
 *
 * @author Sandratra
 */
public class Rep_questionController implements Initializable {
    
    @FXML
    private TableColumn<Reponse_question, Integer> pourcentage;
    
    @FXML
    private TableColumn<Reponse_question, String> rep_a;
    
    @FXML
    private TableColumn<Reponse_question, String> rep_b;
    
    @FXML
    private TableColumn<Reponse_question, String> rep_c;
    
    @FXML
    private TableColumn<Reponse_question, String> rep_d;
    
    @FXML
    private TableColumn<Reponse_question, String> question;
    
    @FXML
    private TableView<Reponse_question> t_liste_rep;
    
    @FXML
    private ComboBox select_quiz;
    
    @FXML
    private Button btn_export;
    
    @FXML
    private ComboBox date_select_quiz;
    
    private ObservableList<Quiz> liste_quiz;
    private ObservableList<Participation_quiz> participation_quizs;
    private ObservableList<Reponse_question> liste_reponses_question;
    
    Quiz quiz_selected;
    
    Participation_quiz participation_quiz;
    
    Quiz_dao quiz_dao = new Quiz_dao();
    Participation_quiz_dao participation_quiz_dao = new Participation_quiz_dao();
    Question_dao question_dao = new Question_dao();
    Reponse_question_dao reponse_question_dao = new Reponse_question_dao();

    public void setQuiz_selected(Quiz quiz_selected) {
        this.quiz_selected = quiz_selected;
    }

    public void setParticipation_quiz(Participation_quiz participation_quiz) {
        this.participation_quiz = participation_quiz;
        show_result();
    }
    
    public void show_result(){
        liste_reponses_question = FXCollections.observableArrayList();
        liste_reponses_question.clear();
        List<Question> questions = question_dao.get_questions_by_quiz(quiz_selected.getId());
        for (Question quest : questions) {
            Reponse_question rq = reponse_question_dao.get_res_by_quest(quest.getId(), participation_quiz);
            rq.setQuestion(quest.getLibelle());
            liste_reponses_question.add(rq);
        }
        t_liste_rep.setItems(liste_reponses_question);
    }

    @FXML
    public void export_result(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/opisiame/view/gestion_resultat/choix_export.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Choix_exportController choix_exportController = fxmlLoader.<Choix_exportController>getController();
            choix_exportController.setReponse_questions(liste_reponses_question);
            
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Selection du format du fichier d'export des résultats");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initOwner(select_quiz.getScene().getWindow());
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/opisiame/image/icone.png")));
            stage.setResizable(false);
            stage.show();
        }
        catch (IOException ex) {
            Logger.getLogger(Liste_quizController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        participation_quizs = participation_quiz_dao.get_participation_quizs();
        liste_quiz = participation_quiz_dao.get_quizs(participation_quizs);
        select_quiz.setItems(liste_quiz);
        
        question.setCellValueFactory(new PropertyValueFactory<Reponse_question, String>("question"));
        rep_a.setCellValueFactory(new PropertyValueFactory<Reponse_question, String>("str_pourcentage_rep_a"));
        rep_b.setCellValueFactory(new PropertyValueFactory<Reponse_question, String>("str_pourcentage_rep_b"));
        rep_c.setCellValueFactory(new PropertyValueFactory<Reponse_question, String>("str_pourcentage_rep_c"));
        rep_d.setCellValueFactory(new PropertyValueFactory<Reponse_question, String>("str_pourcentage_rep_d"));
        pourcentage.setCellValueFactory(new PropertyValueFactory<Reponse_question, Integer>("str_pourcentage"));
        
        select_quiz.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Quiz>() {
            @Override
            public void changed(ObservableValue<? extends Quiz> ov, Quiz t, Quiz t1) {
                if (t1 != null) {
                    date_select_quiz.setItems(participation_quiz_dao.get_dates_participations(t1.getId(), participation_quizs));
                    //setQuiz_selected(t1);
                    date_select_quiz.setDisable(false);
                    setQuiz_selected(t1);
                    btn_export.setDisable(true);
                    t_liste_rep.getItems().clear();
                }
            }
        });
        
        date_select_quiz.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Timestamp>() {
            @Override
            public void changed(ObservableValue<? extends Timestamp> ov, Timestamp t, Timestamp t1) {
                if (t1 != null) {
                    Participation_quiz pq = participation_quiz_dao.get_part_quiz(t1, quiz_selected.getId(), participation_quizs);
                    setParticipation_quiz(pq);
                    btn_export.setDisable(false);
                }
            }
        });
    }
}
