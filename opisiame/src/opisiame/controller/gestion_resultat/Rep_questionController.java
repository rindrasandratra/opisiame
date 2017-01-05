/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller.gestion_resultat;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import opisiame.dao.Question_dao;
import opisiame.dao.Quiz_dao;
import opisiame.dao.Reponse_question_dao;
import opisiame.model.Competence;
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
    private Button export_result;
    
    @FXML
    private TableView<Reponse_question> t_liste_rep;
    
    @FXML
    private ComboBox select_quiz;
    
    private ObservableList<Quiz> liste_quiz;
    private ObservableList<Reponse_question> liste_reponses_question;
    
    Quiz quiz_selected;
    
    Quiz_dao quiz_dao = new Quiz_dao();
    Question_dao question_dao = new Question_dao();
    Reponse_question_dao reponse_question_dao = new Reponse_question_dao();

    public void setQuiz_selected(Quiz quiz_selected) {
        this.quiz_selected = quiz_selected;
        show_result();
    }
    
    public void show_result(){
        liste_reponses_question = FXCollections.observableArrayList();
        liste_reponses_question.clear();
        List<Question> questions = question_dao.get_questions_by_quiz(quiz_selected.getId());
        for (Question quest : questions) {
            Reponse_question rq = reponse_question_dao.get_res_by_quest(quest.getId());
            rq.setQuestion(quest.getLibelle());
            liste_reponses_question.add(rq);
        }
        t_liste_rep.setItems(liste_reponses_question);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        liste_quiz = quiz_dao.getAllquiz();
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
                    setQuiz_selected(t1);
                }
            }
        });
    }
}
