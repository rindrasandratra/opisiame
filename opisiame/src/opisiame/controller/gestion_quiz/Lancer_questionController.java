/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller.gestion_quiz;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.imageio.ImageIO;
import opisiame.model.Question;
import opisiame.dao.*;
import opisiame.model.Reponse;

/**
 * FXML Controller class
 *
 * @author Sandratra
 */
public class Lancer_questionController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Integer quiz_id;
    private Integer quiz_timer;

    private Integer current_question_no;

    @FXML
    private GridPane gpane;

//    @FXML
//    private Button label_timer;

    @FXML
    private Button rep_1;

    @FXML
    private Button rep_2;

    @FXML
    private Button rep_3;

    @FXML
    private Button rep_4;

    @FXML
    private ImageView question_img_view;

    @FXML
    private Label label_question;

    @FXML
    private Button btn_next_question;

    private ArrayList<Question> questions;
    //private ImageView img_view;
    private double ratio_height;
    private double ratio_width;

    Question_dao question_dao = new Question_dao();

    Question current_question;

    Timer t;

    public void setQuiz_id(Integer quiz_id) {
        this.quiz_id = quiz_id;
        get_all_questions();
        if (questions.size() > 0) {
            current_question_no = 0;
            print_question();
//            pagination_quest.setPageCount(questions.size());
//            pagination_quest.setCurrentPageIndex(0);
        }
    }

    public Integer getQuiz_timer() {
        return quiz_timer;
    }

    public void setQuiz_timer(Integer quiz_timer) {
        this.quiz_timer = quiz_timer;
    }

    private void get_all_questions() {
        questions = question_dao.get_questions_by_quiz(this.quiz_id);
    }

    public void print_question() {
        if (current_question_no < questions.size()) {
            Question q = questions.get(current_question_no);
            current_question = q;
            //label_timer.setText((q.getTimer()).toString());
            label_question.setText(q.getLibelle());
            print_image(q.getImg_blob());
            print_reponse(q.getReponses());
            int timer = get_quest_timer();
            if (timer > 0) {
                run_timer(timer);
                btn_next_question.setDisable(true);
            } else {
                btn_next_question.setDisable(false);
                btn_next_question.setText(" >> ");
            }
        }
    }

    public void run_timer(Integer duree) {
        btn_next_question.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if ("0".equals(newValue)) {
                    next_question();
                }
            }
        });
        Timer_update_label thread_class = new Timer_update_label(btn_next_question, duree);
        thread_class.start();
    }

    public void print_image(InputStream blob_img) {
        if (blob_img != null) {
            try {
                BufferedImage buffered_image = ImageIO.read(blob_img);
                if (buffered_image != null) {
                    Image image = SwingFXUtils.toFXImage(buffered_image, null);
                    question_img_view.setImage(image);
                    question_img_view.setSmooth(true);
                    question_img_view.setCache(true);
                    question_img_view.setPreserveRatio(true);
                    ratio_height = gpane.getHeight() / question_img_view.getFitHeight();
                    ratio_width = gpane.getWidth() / question_img_view.getFitWidth();
                    buffered_image.flush();
                    blob_img.reset();
                    blob_img.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        } else {
            question_img_view.setImage(null);
        }
    }

    final ChangeListener<Number> listener = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            if (ratio_height == 0) {
                ratio_height = gpane.getHeight() / question_img_view.getFitHeight();
                ratio_width = gpane.getWidth() / question_img_view.getFitWidth();
            }
            question_img_view.setFitHeight(gpane.getHeight() / ratio_height);
            question_img_view.setFitWidth(gpane.getWidth() / ratio_width);
            question_img_view.setSmooth(true);
        }
    };

    public void print_reponse(ArrayList<Reponse> reponses) {
        ArrayList<Button> bts = new ArrayList(Arrays.asList(rep_1, rep_2, rep_3, rep_4));
        for (int i = 0; i < reponses.size(); i++) {
            affiche_rep(reponses.get(i), bts.get(i));
        }
    }

    public void affiche_rep(Reponse rep, Button b) {
        b.setText(rep.getLibelle());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gpane.heightProperty().addListener(listener);
    }

    @FXML
    private void next_question() {
        current_question_no++;
        if (current_question_no >= questions.size() - 1) {
            end_quiz();
        }
        print_question();
    }

    public int get_quest_timer() {
        if (current_question.getTimer() > 0) {
            return current_question.getTimer();
        } else if (quiz_timer > 0) {
            return quiz_timer;
        } else {
            return 0;
        }
    }

    public void end_quiz() {
        btn_next_question.setText("Terminer");
        btn_next_question.setDisable(false);
        btn_next_question.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                close_window();
            }
        });
    }

    public void close_window() {
        Stage stage = (Stage) rep_1.getScene().getWindow();
        stage.close();
    }

}
