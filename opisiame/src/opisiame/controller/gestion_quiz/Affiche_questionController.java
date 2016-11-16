/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller.gestion_quiz;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import opisiame.model.Question;
import opisiame.dao.*;
import opisiame.model.Reponse;

/**
 * FXML Controller class
 *
 * @author Sandratra
 */
public class Affiche_questionController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Integer quiz_id;

    @FXML
    private Button label_timer;

    @FXML
    private Button rep_1;

    @FXML
    private Button rep_2;

    @FXML
    private Button rep_3;

    @FXML
    private Button rep_4;

    @FXML
    private Button sous_comp;

    @FXML
    private VBox vbox_question;

    @FXML
    private Pagination pagination_quest;

    @FXML
    private Label label_question;

    private ArrayList<Question> questions;

    Question_dao question_dao = new Question_dao();

    public void setQuiz_id(Integer quiz_id) {
        this.quiz_id = quiz_id;
        get_all_questions();
        if (questions.size() > 0) {
            print_question(0);
            pagination_quest.setPageCount(questions.size());
            pagination_quest.setCurrentPageIndex(0);
        } else {
            label_question.setText("Aucune question enregistrée pour ce quiz");
        }
    }

    private void get_all_questions() {
        questions = question_dao.get_questions_by_quiz(this.quiz_id);
    }

    public void print_question(Integer index) {
        Question q = questions.get(index);
        label_timer.setText((q.getTimer()).toString());
        label_question.setText(q.getLibelle());
        sous_comp.setText(q.getSous_comp());
        print_image(q.getUrl_img());
        print_reponse(q.getReponses());
    }

    public void print_image(String url) {
        if (vbox_question.getChildren().size() > 1) {
            vbox_question.getChildren().remove(1);
        }
        if (url != null) {
            url = "/ressource/images/" + url;
            BorderPane pane = new BorderPane();
            ImageView img = new ImageView(url);
            pane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            pane.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
            pane.setCenter(img);
            vbox_question.getChildren().add(pane);
        }
    }

    public void print_reponse(ArrayList<Reponse> reponses) {
        ArrayList<Button> bts = new ArrayList(Arrays.asList(rep_1, rep_2, rep_3, rep_4));
        for (int i = 0; i < reponses.size(); i++) {
            affiche_rep(reponses.get(i), bts.get(i));
        }
    }

    public void affiche_rep(Reponse rep, Button b) {
        if (rep.getIs_bonne_reponse()) {
            b.setStyle("-fx-background-color: blue");
        } else {
            b.setStyle("-fx-background-color: gray");
        }
        b.setText(rep.getLibelle());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pagination_quest.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer p) {
                print_question(p);
                return new VBox();
            }

        });
    }

}
