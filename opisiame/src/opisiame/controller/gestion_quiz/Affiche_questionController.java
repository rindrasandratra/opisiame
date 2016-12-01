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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
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
        if (questions.size() > 0) {
            print_question(0);
            pagination_quest.setPageCount(questions.size());
            pagination_quest.setCurrentPageIndex(0);
        } else {
            System.out.println("opisiame.controller.gestion_quiz.Affiche_questionController.setQuiz_id()");
            pagination_quest.setPageCount(1);
            label_question.setText("Aucune question enregistrée pour ce quiz");
            open_modal_new_question();
            
                Stage st = (Stage) pagination_quest.getScene().getWindow();
                st.close();

        }
    }

    public void open_modal_new_question() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/opisiame/view/gestion_quiz/popup_no_question.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Popup_no_questionController popup_controller = fxmlLoader.<Popup_no_questionController>getController();
            popup_controller.setQuiz_id(this.quiz_id);

            Stage stage = new Stage();
            //stage.initModality(Modality.APPLICATION_MODAL);
            stage.initModality(Modality.NONE);
            // stage.initOwner(Platform.);
            stage.setTitle("Ajout question");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/opisiame/image/icone.png")));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(true);
            stage.showAndWait();

        } catch (IOException ex) {
            Logger.getLogger(Liste_quizController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void get_all_questions() {
        questions = question_dao.get_questions_by_quiz(this.quiz_id);
    }

    public void print_question(Integer index) {
        if (questions.size() > 0) {
            Question q = questions.get(index);
            label_timer.setText((q.getTimer()).toString());
            label_question.setText(q.getLibelle());
            sous_comp.setText(q.getSous_comp());
            print_image(q.getImg_blob());
            print_reponse(q.getReponses());
        }
    }

    public void print_image(InputStream blob_img) {
        if (vbox_question.getChildren().size() > 1) {
            vbox_question.getChildren().remove(1);
        }
        if (blob_img != null) {
            try {
                BufferedImage buffered_image = ImageIO.read(blob_img);
                if (buffered_image != null) {
                    BorderPane pane = new BorderPane();
                    Image image = SwingFXUtils.toFXImage(buffered_image, null);
                    System.out.println("image size" + image.getWidth() + " * " + image.getHeight());

                    img_view = new ImageView(image);
                    img_view.setSmooth(true);
                    img_view.setCache(true);
                    //img_view.setPreserveRatio(true);

                    pane.resize(5, 5);
                    buffered_image.flush();
                    blob_img.close();
//                    if (image.getWidth() > image.getHeight()) {
//                        //img_view.setFitWidth(vbox_question.getWidth());
//                        ratio = gpane.getWidth() / img_view.getFitWidth();
//                        System.out.println("img v" + img_view.getFitWidth());
//                        System.out.println("gpane" + gpane.getWidth());
//                        System.out.println("ratio : "+ratio);
//                        img_view.resize(20, 20);
//                    } else {
//                       // img_view.setFitHeight(vbox_question.getHeight());
//                        ratio = gpane.getHeight() / img_view.getFitHeight();
//                        System.out.println("img v" + img_view.getFitHeight());
//                        System.out.println("gpane" + gpane.getHeight());
//                        System.out.println("ratio : "+ratio);
//                        img_view.resize(10, 10);
//                    }
                    pane.setCenter(img_view);
                    vbox_question.getChildren().add(pane);
                    System.out.println("xxx " + vbox_question.getWidth());
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
                //img_view.setFitHeight(gpane.getHeight() / ratio);
                img_view.resize(10, 10);
                img_view.setPreserveRatio(true);
                System.out.println("img to " + img_view.getFitWidth() + " " + img_view.getFitHeight());
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
//        img_view = new ImageView();
//        img_view.setPreserveRatio(true);
        gpane.heightProperty().addListener(listener);
    }

}
