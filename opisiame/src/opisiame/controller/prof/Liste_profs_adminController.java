/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller.prof;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.util.Callback;
import opisiame.controller.gestion_quiz.Liste_quizController;
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
    private GridPane content;
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
    private TableColumn<Prof, Boolean> c_actions_prof;
    @FXML
    private TableColumn<Prof, Boolean> c_selec;

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
     * GESTION DE LA TABLE
     *
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //remplissage de la table
        c_id_prof.setCellValueFactory(new PropertyValueFactory<Prof, Integer>("id"));
        c_nom_prof.setCellValueFactory(new PropertyValueFactory<Prof, String>("nom"));
        c_prenom_prof.setCellValueFactory(new PropertyValueFactory<Prof, String>("prenom"));
        c_login.setCellValueFactory(new PropertyValueFactory<Prof, String>("lg"));
        t_liste_prof.setItems(getAllProf());

        //Insert Button
        c_actions_prof.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Prof, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Prof, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });

        c_actions_prof.setCellFactory(new Callback<TableColumn<Prof, Boolean>, TableCell<Prof, Boolean>>() {

            @Override
            public TableCell<Prof, Boolean> call(TableColumn<Prof, Boolean> p) {
                return new Liste_profs_adminController.ButtonCell();
            }

        });
        
        

    }

    //Define the button cell
    private class ButtonCell extends TableCell<Prof, Boolean> {

        final Button btn_edit = new Button();
        final Button btn_delete = new Button();

        ButtonCell() {
            btn_edit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    //t_liste_prof.getSelectionModel().select
                    editer_prof();
                }
            });
            btn_delete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    supprimer_prof();
                }
            });
        }

        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {

                HBox box = new HBox(3);

                Image img_edit = new Image(getClass().getResourceAsStream("/opisiame/image/edit.png"), 20, 20, true, true);
                btn_edit.setGraphic(new ImageView(img_edit));

                Image img_delete = new Image(getClass().getResourceAsStream("/opisiame/image/delete.png"), 20, 20, true, true);
                btn_delete.setGraphic(new ImageView(img_delete));

                box.setPadding(new Insets(5, 0, 5, 0));
                // box.setPrefColumns(1);
                box.getChildren().add(btn_edit);
                box.getChildren().add(btn_delete);
                setGraphic(box);
            }
        }
    }

    public void editer_prof() {
        try {

            Stage stage = (Stage) content.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/prof/edit_prof.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(Liste_quizController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void supprimer_prof() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/prof/delete_prof.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Confirmation de suppression");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initOwner(t_liste_prof.getScene().getWindow());
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/opisiame/image/icone.png")));
            stage.setResizable(false);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(Liste_quizController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //EN DEHORS DE LA TABLE
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
        Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/utilisateur/menu_admin.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void ClicBoutonSupprSelec() throws IOException {
        //cf clément
    }

    @FXML
    public void ClicBoutonAjoutAnim() throws IOException {
        //ouverture fenêtre menu_anim
        Stage stage = (Stage) content.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/prof/ajout_prof.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void ClicBoutonImport() throws IOException {

    }

}
