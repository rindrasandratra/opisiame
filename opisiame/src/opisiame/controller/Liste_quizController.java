/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import opisiame.database.*;
import opisiame.model.*;

/**
 * FXML Controller class
 *
 * @author Sandratra
 */
public class Liste_quizController implements Initializable {

    /*
    Injection des élèments depuis la vue (fichier fxml) dans le code (à partir de fx:id)
    */
    @FXML
    private TableView<Quiz> t_liste_quiz; // ex: fx:id de la tableView dans FXML builder est aussi t_liste_quiz (c'est pour les lier)
    @FXML
    private TableColumn<Quiz, String> nom_quiz;
    @FXML
    private TableColumn<Quiz, String> date;
    @FXML
    private TableColumn<Quiz, Integer> id;

    

    /*
    Fonction qui récupère la liste des quizs
    */
    public ObservableList<Quiz> getAllquiz() {
        ObservableList<Quiz> quizs = FXCollections.observableArrayList();
        try {
            Connection connection = Connection_db.getDatabase();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM quiz");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Quiz quiz = new Quiz();
                quiz.setId(rs.getInt(1));
                quiz.setNom(rs.getString(2));
                quiz.setDate_creation(rs.getString(3));
                quizs.add(quiz);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return quizs;
    }

    /*
    Fonction qui initalise la vue
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        nom_quiz.setCellValueFactory(new PropertyValueFactory<Quiz, String>("nom"));
        date.setCellValueFactory(new PropertyValueFactory<Quiz, String>("date_creation"));
        id.setCellValueFactory(new PropertyValueFactory<Quiz, Integer>("id"));
        t_liste_quiz.setItems(getAllquiz());

    }

}
