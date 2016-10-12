/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller.utilisateur;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import opisiame.database.Connection_db;
import session.Session;

/**
 * FXML Controller class
 *
 * @author Audrey
 */
public class ModifParamController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private AnchorPane content;
    @FXML
    private TextField AncienMDP;
    @FXML
    private PasswordField NouveauMDP;
    @FXML
    private PasswordField ConfirmMDP;
    @FXML
    private Label Msg;

    //verification correspondance AncienMDP / login
    private int lecture_admin() throws SQLException {
        final String log = session.Session.getLog();
        final String pass = AncienMDP.getText();
        int ok = 0;

        //vérification du login
        //connection avec la base de donnée
        Connection database = Connection_db.getDatabase();
        PreparedStatement pslog = database.prepareStatement("SELECT COUNT(*) AS total FROM animateur WHERE Anim_login = ? and Anim_mdp = ?");
        pslog.setString(1, log);
        pslog.setString(2, pass);
        ResultSet logres = pslog.executeQuery();
        while (logres.next()) {
            ok = logres.getInt("total");
        }
        return ok;
    }

    @FXML
    public void Modifier_MDP() throws IOException, SQLException {

        if (lecture_admin() == 1) {

            if (NouveauMDP.getText().equals(ConfirmMDP.getText())) {
                Msg.setText("erhjtfj,");
                //modification du mdp dans la base de données
                final String log = session.Session.getLog();
                final String pass = NouveauMDP.getText();
                Connection database = Connection_db.getDatabase();
                PreparedStatement pslog = database.prepareStatement("UPDATE animateur SET Anim_mdp = ? WHERE Anim_login = ?;");
                pslog.setString(1, pass);
                pslog.setString(2, log);
                pslog.execute();

                //ouverture de la fenêtre menu_anim
                Session login = new Session(log, "anim");
                Stage stage = (Stage) content.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/utilisateur/menu_anim.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } else {
                Msg.setText("Le nouveau mot de passe et sa confirmation ne correspondent pas.");
            }

        } else {
            Msg.setText("L'ancien mot de passe ne correspond pas.");
        }
    }

    @FXML
    public void ClicImageOnOff() throws IOException {
        //remise à zéro des variables d'identification (login + mdp)
        session.Session.Logout();
        //ouverture fenêtre interface_authentification
        Stage stage = (Stage) content.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/utilisateur/interface_authentification.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void ClicBoutonRetour() throws IOException {
        //ouverture fenêtre menu_anim
        Stage stage = (Stage) content.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/utilisateur/menu_anim.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}
