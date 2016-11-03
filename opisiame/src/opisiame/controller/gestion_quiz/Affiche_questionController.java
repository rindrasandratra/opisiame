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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.*;
import opisiame.database.Connection_db;

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
    private AnchorPane content;

    public void setQuiz_id(Integer quiz_id) {
        this.quiz_id = quiz_id;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    public void btn_confirm_action() {
        try {
            Connection connection = Connection_db.getDatabase();
            PreparedStatement ps = connection.prepareStatement("DELETE FROM quiz WHERE Quiz_id = ?");
            ps.setInt(1, quiz_id);
            int succes = ps.executeUpdate();
            if (succes == 0) {
                System.err.println("Erreur lors de la suppression du quiz");
            }
            Stage stage = (Stage) content.getScene().getWindow();
            stage.close();
        } catch (SQLException ex) {
            Logger.getLogger(Affiche_questionController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    public void btn_cancel_action() {
        Stage stage = (Stage) content.getScene().getWindow();
        stage.close();
    }
}
