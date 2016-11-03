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
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import opisiame.database.Connection_db;
import opisiame.model.Quiz;

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
    
    private Integer quiz_id;
    
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
            label_date.setText(quiz.getDate_creation());
            label_timer.setText((quiz.getTimer()).toString());
            count_nb_quest();
        } catch (SQLException ex) {
            Logger.getLogger(Delete_quizController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void count_nb_quest(){
        try {
            int nb_quest = 0;
            Connection connection = Connection_db.getDatabase();
            PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM question WHERE Quiz_id = ? ");
            ps.setInt(1, this.quiz_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                nb_quest = rs.getInt(1);
            }
            label_nb_quest.setText(String.valueOf(nb_quest));
        } catch (SQLException ex) {
            Logger.getLogger(Delete_quizController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void voir_questions(){
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("quiz id : "+quiz_id);
        label_date.setText("toto");
    }    
    
    @FXML
    public void ClicImageOnOff() throws IOException {
        //remise à zéro des variables d'identification (login + mdp)
        session.Session.Logout();
        //ouvre la fenêtre Interface_authentification
        Stage stage = (Stage) content.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/utilisateur/interface_authentification.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }
    
}
