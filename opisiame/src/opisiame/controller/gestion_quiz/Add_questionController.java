/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller.gestion_quiz;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import opisiame.dao.Question_dao;

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
    private TextField sous_comp;

    @FXML
    private ComboBox competence;

    @FXML
    private Label label_error;
    
    @FXML
    private ImageView img_view;

    private String libelle, rep_1, rep_2, rep_3, rep_4, sous_competence, url_img;

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

    public Boolean check_form() {
        boolean b = false;
        libelle = enonce.getText();
        rep_1 = rep_a.getText();
        rep_2 = rep_b.getText();
        rep_3 = rep_c.getText();
        rep_4 = rep_d.getText();
        sous_competence = sous_comp.getText();
        
        if ((libelle.compareTo("") != 0)
                && (rep_1.compareTo("") != 0)
                && (rep_2.compareTo("") != 0)
                && (rep_3.compareTo("") != 0)
                && (rep_4.compareTo("") != 0)
                && (sous_competence.compareTo("") != 0)) {
            b = true;
        }
        return b;
    }

    @FXML
    public void ajout_question() {
        if (check_form()) {
            //insert_new_question( quiz_id,String libelle, Integer timer, Integer sous_comp_id, String url_img)
            Integer sous_comp_id = 1;
            question_dao.insert_new_question(this.Quiz_id, libelle, Integer.valueOf(timer.getText()), sous_comp_id, url_img);
        } else {
            label_error.setVisible(true);
        }
    }

    @FXML
    public void terminer() {
        if (check_form()) {

        } else {
            label_error.setVisible(true);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
