/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller.gestion_quiz;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import opisiame.dao.Competence_dao;
import opisiame.dao.Question_dao;
import opisiame.dao.Sous_comp_dao;
import opisiame.model.Competence;
import opisiame.model.Sous_competence;

/**
 * FXML Controller class
 *
 * @author Sandratra
 */
public class Add_questionController implements Initializable {

    private Integer Quiz_id;

    @FXML
    private TextArea enonce;

    @FXML
    private TextArea rep_a;

    @FXML
    private TextArea rep_b;

    @FXML
    private TextArea rep_c;

    @FXML
    private TextArea rep_d;

    @FXML
    private TextField timer;

    @FXML
    private ComboBox combo_sous_comp;

    @FXML
    private ComboBox combo_competence;

    @FXML
    private Label label_error;

    @FXML
    private Label label_ajout_ok;

    @FXML
    private Label error_label_timer;

    @FXML
    private ImageView img_view;

    private String libelle, rep_1, rep_2, rep_3, rep_4, sous_competence, url_img;
    private Integer timer_value;

    Competence_dao competence_dao = new Competence_dao();
    Sous_comp_dao sous_comp_dao = new Sous_comp_dao();

    private ObservableList<Competence> liste_Competence;//contient les champs "competence" pour le combobox
    private ObservableList<Sous_competence> list_sous_comp;

    public Add_questionController() {
        url_img = "";
    }

    Question_dao question_dao = new Question_dao();

    public Integer getQuiz_id() {
        return Quiz_id;
    }

    public void setQuiz_id(Integer Quiz_id) {
        this.Quiz_id = Quiz_id;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        set_data_combo_competence();
    }

    public void reset_null() {
        enonce.setText("");
        rep_a.setText("");
        rep_b.setText("");
        rep_c.setText("");
        rep_d.setText("");
        timer.setText("");
        delete_image();
    }

    public Boolean check_form() {
        boolean b = false;
        libelle = enonce.getText();
        rep_1 = rep_a.getText();
        rep_2 = rep_b.getText();
        rep_3 = rep_c.getText();
        rep_4 = rep_d.getText();

        if ((libelle.compareTo("") != 0)
                && (rep_1.compareTo("") != 0)
                && (rep_2.compareTo("") != 0)
                && (rep_3.compareTo("") != 0)
                && (rep_4.compareTo("") != 0)) {
            b = true;
        }

        return b;
    }

    private Boolean check_timer() {
        timer_value = null;
        Boolean b = true;
        if (timer.getText().compareTo("") != 0) {
            if (validate_number(timer.getText())) {
                timer_value = Integer.valueOf(timer.getText());
                b = true;
            } else {
                b = false;
            }
        }
        return b;
    }

    @FXML
    public void text_change() {
        label_error.setVisible(false);
        error_label_timer.setVisible(false);
        label_ajout_ok.setVisible(false);
    }

    @FXML
    public void ajout_question() {
        if (check_form()) {
            if (check_timer()) {
                Integer sous_comp_id = ((Sous_competence) combo_sous_comp.getSelectionModel().getSelectedItem()).getId();
                question_dao.insert_new_question(this.Quiz_id, libelle, timer_value, sous_comp_id, url_img);
                Stage stage = (Stage) combo_sous_comp.getScene().getWindow();
                stage.close();
            } else {
                error_label_timer.setVisible(true);
            }

        } else {
            label_error.setVisible(true);
        }
    }

    @FXML
    public void terminer_quiz() {
        if (check_form()) {
            if (check_timer()) {
                Integer sous_comp_id = ((Sous_competence) combo_sous_comp.getSelectionModel().getSelectedItem()).getId();
                question_dao.insert_new_question(this.Quiz_id, libelle, timer_value, sous_comp_id, url_img);
                reset_null();
                label_ajout_ok.setVisible(true);
            } else {
                error_label_timer.setVisible(true);
            }

        } else {
            label_error.setVisible(true);
        }
    }

    private void set_data_combo_competence() {
        liste_Competence = competence_dao.get_all_competence();
        combo_competence.getItems().addAll(liste_Competence);
        combo_competence.getSelectionModel().selectFirst();
        set_data_combo_sous_comp((Competence) combo_competence.getSelectionModel().getSelectedItem());
        combo_competence.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Competence>() {
            @Override
            public void changed(ObservableValue<? extends Competence> ov, Competence t, Competence t1) {
                set_data_combo_sous_comp(t1);
            }
        });
    }

    private void set_data_combo_sous_comp(Competence competence) {
        list_sous_comp = sous_comp_dao.get_all_sous_competence(competence.getId());
        combo_sous_comp.getItems().clear();
        combo_sous_comp.getItems().addAll(list_sous_comp);
        if (list_sous_comp.size() > 0) {
            combo_sous_comp.getSelectionModel().selectFirst();
        }
        combo_sous_comp.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Sous_competence>() {
            @Override
            public void changed(ObservableValue<? extends Sous_competence> ov, Sous_competence t, Sous_competence t1) {
                if (t1 != null) {
                    System.out.println("Selected comp : " + t1.getId() + " " + t1.getLibelle());
                }
            }
        });
    }

    @FXML
    public void ajout_image() throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choisir un fichier");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image file", "*.jpg", "*.jpeg", "*.png")
        );
        File selected_file = chooser.showOpenDialog(img_view.getScene().getWindow());
        if (selected_file != null) {

            url_img = selected_file.getAbsolutePath();
            affiche_img();
        }
    }

    private boolean validate_number(String str) {
        return str.matches("[0-9]*");
    }

    public void affiche_img() {
        img_view.setImage(new Image("file:///" + url_img));
    }

    @FXML
    public void delete_image() {
        img_view.setImage(null);
        url_img = "";
    }

    @FXML
    public void open_competence() {

    }

    @FXML
    public void open_sous_comp() {

    }
}
