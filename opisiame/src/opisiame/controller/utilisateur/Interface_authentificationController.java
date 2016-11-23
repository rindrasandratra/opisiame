/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller.utilisateur;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.util.*;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.shape.*;
import opisiame.database.Connection_db;
import session.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * FXML Controller class
 *
 * @author clement
 */
public class Interface_authentificationController implements Initializable {

    @FXML
    private AnchorPane content;
    @FXML
    private TextField Login_field;
    @FXML
    private PasswordField Passwd_field;
    @FXML
    private Label Message_field;
    @FXML
    private Polygon Submit_field;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public static String md5(String input) {

        String md5 = null;

        if (null == input) {
            return null;
        }

        try {
            //Create MessageDigest object for MD5
            MessageDigest digest = MessageDigest.getInstance("MD5");
            //Update input string in message digest
            digest.update(input.getBytes(), 0, input.length());
            //Converts message digest value in base 16 (hex) 
            md5 = new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5;
    }

    private int lecture_admin() throws SQLException {
        final String log = Login_field.getText();
        final String pass = md5(Passwd_field.getText());
        int count1 = 0;
        int count2 = 0;
        int ok = 0;

        //vérification du login
        //connection avec la base de donnée
        Connection database = Connection_db.getDatabase();
        PreparedStatement pslog = database.prepareStatement("SELECT COUNT(*) AS total FROM administrateur WHERE Admin_login = ? and Admin_mdp = ?");
        pslog.setString(1, log);
        pslog.setString(2, pass);
        ResultSet logres = pslog.executeQuery();
        while (logres.next()) {
            count1 = logres.getInt("total");
        }
        if (count1 == 1) {
            ok = 1;
        }

        PreparedStatement pslog2 = database.prepareStatement("SELECT COUNT(*) AS total2 FROM animateur WHERE Anim_login = ? and Anim_mdp = ?");
        pslog2.setString(1, log);
        pslog2.setString(2, pass);
        ResultSet logres2 = pslog2.executeQuery();
        while (logres2.next()) {
            count2 = logres2.getInt("total2");
        }
        if (count2 == 1) {
            ok = 2;
        }

        return ok;
    }

    @FXML
    public void Submit_passwd() throws IOException, SQLException {

        if (lecture_admin() == 1) {

            final String log = Login_field.getText();
            Session login = new Session(log, "admin");
            Session.setType("admin");
            Stage stage = (Stage) content.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/utilisateur/menu_admin.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else if (lecture_admin() == 2) {

            final String log = Login_field.getText();
            Session login = new Session(log, "anim");
            Session.setAnim_id(get_anim_id(log));
            Session.setType("anim");
            Stage stage = (Stage) content.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/utilisateur/menu_anim.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            Message_field.setText("erreur d'authentification");
            Message_field.setStyle("-fx-font-weight: bold; -fx-text-fill : #f00");
        }
    }

    Integer get_anim_id(String log) {
        Integer anim_id = null;
        Connection connexion = null;
        PreparedStatement ps;
        try {
            Connection connection = Connection_db.getDatabase();
            ps = connection.prepareStatement("SELECT * FROM animateur WHERE Anim_login = ?");
            ps.setString(1, log);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                anim_id = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return anim_id;
    }
}
