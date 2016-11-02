/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller.prof;

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
 * @author Audrey
 */
public class Delete_profController implements Initializable {
/**
     * Initializes the controller class.
     */
    private Integer prof_id;

    @FXML
    private AnchorPane content;

    public void setProf_id(Integer prof_id) {
        this.prof_id = prof_id;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    public void btn_confirm_action() {
        try {
            Connection connection = Connection_db.getDatabase();
            PreparedStatement ps = connection.prepareStatement("DELETE FROM animateur WHERE Anim_id = ?");
            ps.setInt(1, prof_id);
            int succes = ps.executeUpdate();
            if (succes == 0) {
                System.err.println("Erreur lors de la suppression ddes animateurs");
            }
            Stage stage = (Stage) content.getScene().getWindow();
            stage.close();
        } catch (SQLException ex) {
            Logger.getLogger(Delete_profController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    public void btn_cancel_action() {
        Stage stage = (Stage) content.getScene().getWindow();
        stage.close();
    }
}
