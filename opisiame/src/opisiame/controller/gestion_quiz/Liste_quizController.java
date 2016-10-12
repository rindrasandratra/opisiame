/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller.gestion_quiz;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
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
    @FXML
    private TableColumn<Quiz, Boolean> actionCol;

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

        // Stage stage = (Stage) t_liste_quiz.getScene().getWindow();
        nom_quiz.setCellValueFactory(new PropertyValueFactory<Quiz, String>("nom"));
        date.setCellValueFactory(new PropertyValueFactory<Quiz, String>("date_creation"));
        id.setCellValueFactory(new PropertyValueFactory<Quiz, Integer>("id"));
        t_liste_quiz.setItems(getAllquiz());

        //Insert Button
//        TableColumn col_action = new TableColumn<>("Action");
//        col_action.setSortable(false);
        actionCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Quiz, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Quiz, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });

        actionCol.setCellFactory(new Callback<TableColumn<Quiz, Boolean>, TableCell<Quiz, Boolean>>() {

            @Override
            public TableCell<Quiz, Boolean> call(TableColumn<Quiz, Boolean> p) {
                return new ButtonCell();
            }

        });
        //t_liste_quiz.getColumns().add(actionCol);

    }

    //Define the button cell
    private class ButtonCell extends TableCell<Quiz, Boolean> {

        final Button btn_edit = new Button();
        final Button btn_delete = new Button();
        final Button btn_detail = new Button();

        ButtonCell() {
            btn_edit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    edit_quiz();
                }
            });
            btn_delete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    delete_quiz();
                }
            });
            btn_detail.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    detail_quiz();
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

                Image img_detail = new Image(getClass().getResourceAsStream("/opisiame/image/detail.png"), 20, 20, true, true);
                btn_detail.setGraphic(new ImageView(img_detail));

                box.setPadding(new Insets(5, 0, 5, 0));
                // box.setPrefColumns(1);
                box.getChildren().add(btn_edit);
                box.getChildren().add(btn_delete);
                box.getChildren().add(btn_detail);
                setGraphic(box);
            }
        }
    }

    public void edit_quiz() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/gestion_quiz/edit_quiz.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add( new Image( getClass().getResourceAsStream( "/opisiame/image/icone.png" )));
            stage.setTitle("Modifier quiz");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initOwner(t_liste_quiz.getScene().getWindow());
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(Liste_quizController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete_quiz() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/gestion_quiz/delete_quiz.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Confirmation de suppression");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initOwner(t_liste_quiz.getScene().getWindow());
            stage.getIcons().add( new Image( getClass().getResourceAsStream( "/opisiame/image/icone.png" )));
            stage.setResizable(false);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(Liste_quizController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void detail_quiz() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/gestion_quiz/detail_quiz.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Détail quiz");
            stage.getIcons().add( new Image( getClass().getResourceAsStream( "/opisiame/image/icone.png" )));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initOwner(t_liste_quiz.getScene().getWindow());
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(Liste_quizController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
