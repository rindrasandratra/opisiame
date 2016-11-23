/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller.gestion_quiz;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
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
public class Affiche_questionController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Integer quiz_id;

    @FXML
    private GridPane gpane;

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
    private ImageView img_view;
    private double ratio;

    Question_dao question_dao = new Question_dao();

    public void setQuiz_id(Integer quiz_id) {
        this.quiz_id = quiz_id;
        get_all_questions();
        //calcul_init_size_vbox();
        if (questions.size() > 0) {
             //print_question(0);
            pagination_quest.setPageCount(questions.size());
            pagination_quest.setCurrentPageIndex(0);
        } else {
            label_question.setText("Aucune question enregistrée pour ce quiz");
        }
    }

    Double ratio_vbox_gpane_h = null;
    Double ratio_vbox_gpane_w = null;

    public void calcul_init_size_vbox() {
        ratio_vbox_gpane_h = gpane.getHeight() / vbox_question.getHeight();
        ratio_vbox_gpane_w = gpane.getWidth() / vbox_question.getWidth();
        //System.out.println("init vbox_question.getHeight()"+ vbox_question.getHeight());
    }

    private void get_all_questions() {
        questions = question_dao.get_questions_by_quiz(this.quiz_id);
    }

    public void print_question(Integer index) {
        if (index != null){
        if (questions.size() > 0) {
            Question q = questions.get(index);
            System.out.println("question : " + q.getId() + " img : " + q.getImg_blob());
            label_timer.setText((q.getTimer()).toString());
            label_question.setText(q.getLibelle());
            sous_comp.setText(q.getSous_comp());
            //if (q.getImg_blob() != null) {
            print_image(q.getImg_blob());
            // }
            print_reponse(q.getReponses());
        }}
    }

    public void print_image(InputStream blob_img) {
        if (vbox_question.getChildren().size() > 1) {
            vbox_question.getChildren().remove(1);
           // vbox_question.setMaxSize(gpane.getWidth()/ratio_vbox_gpane_w, gpane.getHeight()/ratio_vbox_gpane_h);
            //vbox_question.resize();
        }
        if (blob_img != null) {
            try {
                BufferedImage buffered_image = ImageIO.read(blob_img);
                BorderPane pane = new BorderPane();
                if (buffered_image != null) {
                    Image image = SwingFXUtils.toFXImage(buffered_image, null);
                    img_view = new ImageView(image);
                    img_view.setPreserveRatio(true);
                    img_view.setFitHeight(vbox_question.getHeight());
                    ratio = gpane.getHeight() / img_view.getFitHeight();
                    pane.setCenter(img_view);
                    vbox_question.getChildren().add(pane);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

    final ChangeListener<Number> listener = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            if (img_view != null) {
                img_view.setFitHeight(gpane.getHeight() / ratio);
                img_view.setPreserveRatio(true);
            }
        }
    };

    public void print_reponse(ArrayList<Reponse> reponses) {
        ArrayList<Button> bts = new ArrayList(Arrays.asList(rep_1, rep_2, rep_3, rep_4));
        for (int i = 0; i < reponses.size(); i++) {
            affiche_rep(reponses.get(i), bts.get(i));
        }
    }

    public void affiche_rep(Reponse rep, Button b) {
        if (rep.getIs_bonne_reponse() == 1) {
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
        gpane.heightProperty().addListener(listener);
    }
}
