/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller.gestion_quiz;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import opisiame.model.Quiz;
import opisiame.dao.*;

/**
 * FXML Controller class
 *
 * @author zhuxiangyu
 */
public class Affichage_quizController implements Initializable {

    @FXML
    private AnchorPane content;

    @FXML
    private Label label_date;

    @FXML
    private Label label_timer;

    @FXML
    private Label label_nb_quest;
    
    @FXML
    private Text label_titre;

    private Integer quiz_id;

    Quiz_dao quiz_dao = new Quiz_dao();

    public void setQuiz_id(Integer quiz_id) {
        this.quiz_id = quiz_id;
        get_quiz_by_id();
    }

    public void get_quiz_by_id() {
        Quiz quiz = quiz_dao.get_quiz_by_id(this.quiz_id);
        label_titre.setText(quiz.getNom());
        label_date.setText(quiz.getDate_creation());
        label_timer.setText((quiz.getTimer()).toString());
        label_nb_quest.setText(String.valueOf(quiz_dao.count_nb_quest(this.quiz_id)));
    }

    @FXML
    public void voir_questions() throws IOException {
        Stage stage = (Stage) label_nb_quest.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/opisiame/view/gestion_quiz/affiche_question.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Affiche_questionController question_controller = fxmlLoader.<Affiche_questionController>getController();
        question_controller.setQuiz_id(this.quiz_id);

        Stage stage_affiche = new Stage();
        stage_affiche.initModality(Modality.APPLICATION_MODAL);
        stage_affiche.setTitle("Détail quiz");
        stage_affiche.getIcons().add(new Image(getClass().getResourceAsStream("/opisiame/image/icone.png")));
        Scene scene = new Scene(root);
        stage_affiche.setScene(scene);
        stage_affiche.initOwner(stage.getOwner());
        stage_affiche.show();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

//    @FXML
//    public void ClicImageOnOff() throws IOException {
//        //remise à zéro des variables d'identification (login + mdp)
//        session.Session.Logout();
//        //ouvre la fenêtre Interface_authentification
//        Stage stage = (Stage) content.getScene().getWindow();
//        Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/utilisateur/interface_authentification.fxml"));
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.setResizable(true);
//        stage.show();
//    }
}
