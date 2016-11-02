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

    private List<Integer> liste_supr = new ArrayList<>();

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

        c_selec.setCellFactory(new Callback<TableColumn<Prof, Boolean>, TableCell<Prof, Boolean>>() {
            @Override
            public TableCell<Prof, Boolean> call(TableColumn<Prof, Boolean> param) {
                return new Liste_profs_adminController.CheckBoxCell();
            }
        });

    }

    private class CheckBoxCell extends TableCell<Prof, Boolean> {

        final CheckBox check = new CheckBox();

        CheckBoxCell() {
            check.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (check.isSelected()) {
                        Integer id = t_liste_prof.getSelectionModel().getSelectedItem().getId();
                        liste_supr.add(id);
                    }
                }
            });
        }

        //Affichage des boutons
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                HBox lay = new HBox(1);
                lay.getChildren().add(check);
                setGraphic(lay);
            }
        }
    };

    //Define the button cell
    private class ButtonCell extends TableCell<Prof, Boolean> {

        final Button btn_edit = new Button();

        ButtonCell() {
            btn_edit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    
                    editer_prof();
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

                box.setPadding(new Insets(5, 0, 5, 0));
                // box.setPrefColumns(1);
                box.getChildren().add(btn_edit);
                setGraphic(box);
            }
        }
    }

    
    
    public void editer_prof() {
        try {
            
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/opisiame/view/prof/edit_prof.fxml"));
            Parent root = (Parent)fxmlLoader.load();        
            Edit_profController edit_controller = fxmlLoader.<Edit_profController>getController();
            int animID = t_liste_prof.getSelectionModel().getSelectedItem().getId();
            edit_controller.setAnim_id(animID);
            
            
            URL url = fxmlLoader.getLocation();
            ResourceBundle rb = fxmlLoader.getResources();
            edit_controller.initialize(url,rb);
            
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Modification");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initOwner(t_liste_prof.getScene().getWindow());
            stage.getIcons().add( new Image( getClass().getResourceAsStream( "/opisiame/image/icone.png" )));
            stage.setResizable(false);
            stage.show();
            
            stage.setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    update_tableau();
                }
            });
            

        } catch (IOException ex) {
            Logger.getLogger(Liste_profs_adminController.class.getName()).log(Level.SEVERE, null, ex);
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

    public void update_tableau() {
        t_liste_prof.getItems().clear();
        t_liste_prof.setItems(getAllProf());
    }

    @FXML
    public void ClicBoutonSupprSelec() throws IOException {
        //ouvrir delete_prof
    }

    @FXML
    public void ClicBoutonAjoutAnim() throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/prof/ajout_prof.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Ajout animateur");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/opisiame/image/icone.png")));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initOwner(t_liste_prof.getScene().getWindow());
            stage.show();

            stage.setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    update_tableau();
                }
            });

        } catch (IOException ex) {
            Logger.getLogger(Liste_profs_adminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void ClicBoutonImport() throws IOException {

    }

}
