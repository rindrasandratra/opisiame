/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller.gestion_quiz;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import opisiame.database.Connection_db;
import opisiame.model.Quiz;
import session.Session;

/**
 * FXML Controller class
 *
 * @author Sandratra
 */
public class Edit_quizController implements Initializable {
    
    private Integer quiz_id;

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
    
    public void setQuiz_id(Integer quiz_id) {
        this.quiz_id = quiz_id;
        get_quiz_by_id();
    }
    
    public void get_quiz_by_id(){
        try {
            Connection connection = Connection_db.getDatabase();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM quiz WHERE Quiz_id = ? ");
            ps.setInt(1, this.quiz_id);
            ResultSet rs = ps.executeQuery();
            Quiz quiz = new Quiz();
            while (rs.next()) {
                quiz.setId(rs.getInt(1));
                quiz.setNom(rs.getString(2));
                quiz.setDate_creation(rs.getString(3));
                quiz.setTimer(rs.getInt(4));
            }
            nom_quiz.setText(quiz.getNom());
            if (quiz.getTimer() !=  0)
            {
                chkb_timer.setSelected(true);
                timer.setDisable(false);
                timer.setText((quiz.getTimer()).toString());
            }
        } catch (SQLException ex) {
            Logger.getLogger(Delete_quizController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            update_quiz(nom, value_timer);
        }
    }

    private boolean validate_number(String str) {
        return str.matches("[0-9]*");
    }

    public void update_quiz(String value_nom, String value_timer) {
        String SQL;
        if (value_timer.compareTo("") != 0) {
            SQL = "UPDATE quiz SET Quiz_nom = ?, Quiz_timer = ? WHERE Quiz_id = ?";
        } else {
            SQL = "UPDATE quiz SET Quiz_nom = ? WHERE Quiz_id = ?";
        }
        try {
            Connection connection = Connection_db.getDatabase();
            PreparedStatement ps = connection.prepareStatement(SQL);
            Integer num = 1;
            ps.setString(num++, value_nom);
            if (value_timer.compareTo("") != 0) {
                ps.setInt(num++, Integer.valueOf(value_timer));
            } 
            ps.setInt(num,this.quiz_id);
            int succes = ps.executeUpdate();
            if (succes == 0) {
                System.err.println("Échec de la modification du quiz, aucune ligne modifiée dans la table.");
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
