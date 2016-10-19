/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller.prof;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import opisiame.database.Connection_db;
import opisiame.model.Prof;

/**
 * FXML Controller class
 *
 * @author Audrey
 */
public class Liste_profs_adminController implements Initializable {

    //injection des éléments graphiques
    @FXML
    private TableView<Prof> t_liste_prof;
    @FXML
    private TableColumn<Prof, Integer> c_id_prof;
    @FXML
    private TableColumn<Prof, String> c_nom_prof;
    @FXML
    private TableColumn<Prof, String> c_prenom_prof;
    @FXML
    private TableColumn<Prof, String> c_login;
    @FXML
    private TableColumn<Prof, String> c_actions_prof;

    //récupération de la liste des profs dans la BDD, et affichage
    public ObservableList<Prof> getAllProf() {
        ObservableList<Prof> profs = FXCollections.observableArrayList();
        try {
            Connection connection = Connection_db.getDatabase();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM animateur");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Prof prof = new Prof();
                prof.setId(rs.getInt(1));
                prof.setNom(rs.getString(2));
                prof.setPrenom(rs.getString(3));
                prof.setLg(rs.getString(4));
                profs.add(prof);
            }
                
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return profs;
    }

    /**
     * Initializes the controller class.
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        c_id_prof.setCellValueFactory(new PropertyValueFactory<Prof, Integer>("id"));
        c_nom_prof.setCellValueFactory(new PropertyValueFactory<Prof, String>("nom"));
        c_prenom_prof.setCellValueFactory(new PropertyValueFactory<Prof, String>("prenom"));
        c_login.setCellValueFactory(new PropertyValueFactory<Prof, String>("lg"));
        t_liste_prof.setItems(getAllProf());
    }

}
