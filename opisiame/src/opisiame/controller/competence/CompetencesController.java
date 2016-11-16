/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller.competence;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.util.Callback;
import opisiame.database.Connection_db;
import opisiame.model.Competence;

/**
 * FXML Controller class
 *
 * @author Audrey
 */
public class CompetencesController implements Initializable {

    /*
    Injection des élèments depuis la vue (fichier fxml) dans le code (à partir de fx:id)
     */
    @FXML
    private GridPane content;
    @FXML
    private TableView<Competence> t_liste_competence; // ex: fx:id de la tableView dans FXML builder est aussi t_liste_quiz (c'est pour les lier)
    @FXML
    private TableColumn<Competence, String> nom_competence;
    @FXML
    private TableColumn<Competence, Boolean> action;
    @FXML
    private TableColumn<Competence, Boolean> c_selec;
    @FXML
    private TableColumn<Competence, Integer> id;
    @FXML
    private TextField txt_search;

    private List<Integer> liste_supr = new ArrayList<>();
    private String Cont_recherche = null;

    //récupération de la liste des competences dans la BDD, et affichage
    public ObservableList<Competence> getAllComp() {

        ObservableList<Competence> comps = FXCollections.observableArrayList();
        try {
            Connection connection = Connection_db.getDatabase();
            liste_supr.clear();
            PreparedStatement ps;

            if (Cont_recherche != null) {
                ps = connection.prepareStatement("SELECT * FROM compétences \n"
                        + "WHERE Comp_id LIKE ?\n"
                        + "OR Competence LIKE ?\n");
                ps.setString(1, "%" + Cont_recherche + "%");
                ps.setString(2, "%" + Cont_recherche + "%");
            } else {
                ps = connection.prepareStatement("SELECT * FROM compétences");
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Competence comp = new Competence();
                comp.setId(rs.getInt(1));
                comp.setNom(rs.getString(2));
                comps.add(comp);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return comps;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //remplissage de la table
        id.setCellValueFactory(new PropertyValueFactory<Competence, Integer>("id"));
        nom_competence.setCellValueFactory(new PropertyValueFactory<Competence, String>("nom"));
        t_liste_competence.setItems(getAllComp());

        //Insert Button
        action.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Competence, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Competence, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });

        action.setCellFactory(new Callback<TableColumn<Competence, Boolean>, TableCell<Competence, Boolean>>() {

            @Override
            public TableCell<Competence, Boolean> call(TableColumn<Competence, Boolean> p) {
                return new CompetencesController.ButtonCell();
            }

        });

        c_selec.setCellFactory(new Callback<TableColumn<Competence, Boolean>, TableCell<Competence, Boolean>>() {
            @Override
            public TableCell<Competence, Boolean> call(TableColumn<Competence, Boolean> param) {
                return new CompetencesController.CheckBoxCell();
            }
        });

    }

    public void Rechercher() throws IOException {
        Cont_recherche = txt_search.getText();
        //System.out.println(Cont_recherche);
        update_tableau();

        //appel de la fonction initialize, permet d'afficher correctement les checkbox/boutons
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/opisiame/view/competence/competences.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        URL url = fxmlLoader.getLocation();
        ResourceBundle rb = fxmlLoader.getResources();
        this.initialize(url, rb);

    }

    private class CheckBoxCell extends TableCell<Competence, Boolean> {

        final CheckBox check = new CheckBox();

        CheckBoxCell() {
            check.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (check.isSelected()) {
                        Integer id = t_liste_competence.getSelectionModel().getSelectedItem().getId();
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
    private class ButtonCell extends TableCell<Competence, Boolean> {

        final Button btn_edit = new Button();
        final Button btn_detail = new Button();

        ButtonCell() {

            btn_edit.setStyle("-fx-background-color: gray");
            btn_edit.setCursor(Cursor.HAND);

            btn_detail.setStyle("-fx-background-color: green");
            btn_detail.setCursor(Cursor.HAND);

            btn_edit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {

                    editer_competence();
                }
            });

            btn_detail.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    detail_competence();
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

                Image img_detail = new Image(getClass().getResourceAsStream("/opisiame/image/detail.png"), 20, 20, true, true);
                btn_detail.setGraphic(new ImageView(img_detail));

                box.setPadding(new Insets(5, 0, 5, 0));
                // box.setPrefColumns(1);
                box.getChildren().add(btn_edit);
                setGraphic(box);
            }
        }
    }
    
    
    public void detail_competence() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/opisiame/view/competence/affichage_sous_competences.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Affichage_sous_competencesController detail_controller = fxmlLoader.<Affichage_sous_competencesController>getController();
//            detail_controller.setComp_id(t_liste_competence.getSelectionModel().getSelectedItem().getId());
            
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Sous compétences");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/opisiame/image/icone.png")));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initOwner(t_liste_competence.getScene().getWindow());
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(CompetencesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editer_competence() {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/opisiame/view/competence/editer_competence.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Editer_competenceController edit_controller = fxmlLoader.<Editer_competenceController>getController();
            int compID = t_liste_competence.getSelectionModel().getSelectedItem().getId();
            edit_controller.setComp_id(compID);

            URL url = fxmlLoader.getLocation();
            ResourceBundle rb = fxmlLoader.getResources();
            edit_controller.initialize(url, rb);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Modification");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initOwner(t_liste_competence.getScene().getWindow());
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/opisiame/image/icone.png")));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    update_tableau();
                }
            });

        } catch (IOException ex) {
            Logger.getLogger(CompetencesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update_tableau() {
        t_liste_competence.getItems().clear();
        t_liste_competence.setItems(getAllComp());

    }

    //Clic bouton on/off
    @FXML
    public void ClicImageOnOff() throws IOException {
        //Retour sur la fenetre d'identification
        Stage stage = (Stage) content.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/utilisateur/interface_authentification.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        session.Session.Logout();
    }

    //Clic bouton de retour
    @FXML
    public void ClicBoutonRetour() throws IOException {
        //Retour sur la fenetre menu
        Stage stage = (Stage) content.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/utilisateur/menu_anim.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    @FXML
    public void ClicBoutonAjoutComp() throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/competence/ajout_comp.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Ajout compétence");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/opisiame/image/icone.png")));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initOwner(t_liste_competence.getScene().getWindow());
            stage.show();

            stage.setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    update_tableau();
                }
            });

        } catch (IOException ex) {
            Logger.getLogger(CompetencesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void ClicBoutonSupprSelec() throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/opisiame/view/competence/delete_comp.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Delete_compController delete_controller = fxmlLoader.<Delete_compController>getController();
            delete_controller.setComp_id(liste_supr);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Confirmation de suppression");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initOwner(t_liste_competence.getScene().getWindow());
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/opisiame/image/icone.png")));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    try {
                        Stage stage = (Stage) content.getScene().getWindow();
                        Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/competence/competences.fxml"));
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setResizable(true);
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(CompetencesController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

        } catch (IOException ex) {
            Logger.getLogger(CompetencesController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}