/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller.gestion_quiz;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import opisiame.database.Connection_db;
import opisiame.model.Quiz;
import session.Session;

/**
 * FXML Controller class
 *
 * @author Audrey
 */
public class NouveauQuizController implements Initializable {

    @FXML
    private TextField nom_quiz;

    @FXML
    private TextField timer;

    @FXML
    private CheckBox chkb_timer;

    @FXML
    private Label label_error_login;

    @FXML
    private Label label_error_timer;

    @FXML
    private Label label_number_timer_error;
    
    private TableView<Quiz> t_liste_quiz;

    public TableView<Quiz> getT_liste_quiz() {
        return t_liste_quiz;
    }

    public void setT_liste_quiz(TableView<Quiz> t_liste_quiz) {
        this.t_liste_quiz = t_liste_quiz;
    }

    @FXML
    public void check_timer() {
        if (chkb_timer.isSelected()) {
            timer.setDisable(false);
        } else {
            timer.setDisable(true);
            label_error_timer.setVisible(false);
        }
    }

    @FXML
    public void nom_set_label_error_not_visible() {
        label_error_login.setVisible(false);
    }

    @FXML
    public void timer_set_label_error_not_visible() {
        label_error_timer.setVisible(false);
        label_number_timer_error.setVisible(false);
    }

    @FXML
    public void btn_valider() {
        //System.out.println(t_liste_quiz.getSelectionModel().getSelectedItem().getId());
        String nom = nom_quiz.getText();
        String value_timer = timer.getText();
        Boolean champ_ok = true;
        if (nom.compareTo("") == 0) {
            label_error_login.setVisible(true);
            champ_ok = false;
        }
        if ((chkb_timer.isSelected()) && (value_timer.compareTo("") == 0)) {
            label_error_timer.setVisible(true);
            champ_ok = false;
        }
        if (!validate_number(value_timer)) {
            label_number_timer_error.setVisible(true);
            champ_ok = false;
        }
        if (champ_ok == true) {
            label_error_login.setVisible(false);
            label_error_timer.setVisible(false);
            label_number_timer_error.setVisible(false);
            insert_new_quiz(nom, value_timer);
        }
    }

    private boolean validate_number(String str) {
        return str.matches("[0-9]*");
    }

    public void insert_new_quiz(String value_nom, String value_timer) {
        String SQL;
        if (value_timer.compareTo("") != 0) {
            SQL = "INSERT INTO quiz (Quiz_nom,Quiz_timer,Anim_id) VALUES (?,?,?)";
        } else {
            SQL = "INSERT INTO quiz (Quiz_nom,Anim_id) VALUES (?,?)";
        }
        try {
            Connection connection = Connection_db.getDatabase();
            PreparedStatement ps = connection.prepareStatement(SQL);
            Integer num = 1;
            ps.setString(num++, value_nom);
            if (value_timer.compareTo("") != 0) {
                ps.setInt(num++, Integer.valueOf(value_timer));
            } 
            // à enlever après test :D
            if (Session.getAnim_id() == null) {
                ps.setInt(num, 1);
            } else {
                ps.setInt(num, Session.getAnim_id());
            }
            int succes = ps.executeUpdate();
            if (succes == 0) {
                System.err.println("Échec de la création du quiz, aucune ligne ajoutée dans la table.");
            }
            Stage stage = (Stage) nom_quiz.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void btn_close() {
        Stage stage = (Stage) nom_quiz.getScene().getWindow();
        stage.close();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
