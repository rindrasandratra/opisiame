/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller.gestion_quiz;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.util.Callback;
import opisiame.dao.Quiz_dao;
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
    private GridPane content;
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
    @FXML
    private TableColumn<Quiz, Integer> timer;
    @FXML
    private TextField txt_search;
    
    Quiz_dao quiz_dao = new Quiz_dao();

    /*
    Fonction qui récupère la liste des quizs
     */
    public ObservableList<Quiz> getAllquiz() {
        return quiz_dao.getAllquiz();
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
        timer.setCellValueFactory(new PropertyValueFactory<Quiz, Integer>("timer"));
        t_liste_quiz.setItems(getAllquiz());

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
            btn_edit.setStyle("-fx-background-color: gray");
            btn_edit.setCursor(Cursor.HAND);
            
            btn_detail.setStyle("-fx-background-color: green");
            btn_detail.setCursor(Cursor.HAND);
            
            btn_delete.setStyle("-fx-background-color: red");
            btn_delete.setCursor(Cursor.HAND);
            
            btn_edit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    edit_quiz();
                    Button bt = (Button) t.getSource();
                    // System.out.println(btn_delete.getParent().getParent().toString());

                    // System.out.println("index : "+bt.getParent().getParent().toString());
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
                
                box.setAlignment(Pos.CENTER);

                Image img_edit = new Image(getClass().getResourceAsStream("/opisiame/image/edit.png"), 20, 20, true, true);
                btn_edit.setGraphic(new ImageView(img_edit));

                Image img_delete = new Image(getClass().getResourceAsStream("/opisiame/image/delete.png"), 20, 20, true, true);
                btn_delete.setGraphic(new ImageView(img_delete));

                Image img_detail = new Image(getClass().getResourceAsStream("/opisiame/image/detail.png"), 20, 20, true, true);
                btn_detail.setGraphic(new ImageView(img_detail));

                box.setPadding(new Insets(5, 0, 5, 0));//ajout de marge à l'interieur du bouton
                // box.setPrefColumns(1);
                box.getChildren().add(btn_detail);
                box.getChildren().add(btn_edit);
                box.getChildren().add(btn_delete);
                setGraphic(box);
            }
        }
    }

    public void edit_quiz() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/opisiame/view/gestion_quiz/edit_quiz.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Edit_quizController edit_controller = fxmlLoader.<Edit_quizController>getController();
            edit_controller.setQuiz_id(t_liste_quiz.getSelectionModel().getSelectedItem().getId());
            
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/opisiame/image/icone.png")));
            stage.setTitle("Modifier quiz");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initOwner(t_liste_quiz.getScene().getWindow());
            stage.show();
            
            stage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    update_tableau();
                }
            });

        } catch (IOException ex) {
            Logger.getLogger(Liste_quizController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete_quiz() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/opisiame/view/gestion_quiz/delete_quiz.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Delete_quizController delete_controller = fxmlLoader.<Delete_quizController>getController();
            delete_controller.setQuiz_id(t_liste_quiz.getSelectionModel().getSelectedItem().getId());

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Confirmation de suppression");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initOwner(t_liste_quiz.getScene().getWindow());
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/opisiame/image/icone.png")));
            stage.setResizable(false);
            stage.show();

            stage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    update_tableau();
                }
            });

        } catch (IOException ex) {
            Logger.getLogger(Liste_quizController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update_tableau() {
        t_liste_quiz.getItems().clear();
        t_liste_quiz.setItems(getAllquiz());
    }

    public void detail_quiz() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/opisiame/view/gestion_quiz/affichage_quiz.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Affichage_quizController detail_controller = fxmlLoader.<Affichage_quizController>getController();
            detail_controller.setQuiz_id(t_liste_quiz.getSelectionModel().getSelectedItem().getId());
            
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Détail quiz");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/opisiame/image/icone.png")));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initOwner(t_liste_quiz.getScene().getWindow());
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(Liste_quizController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void ajout_quiz() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/gestion_quiz/nouveau_quiz.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Ajout quiz");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/opisiame/image/icone.png")));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initOwner(t_liste_quiz.getScene().getWindow());
            stage.show();

            stage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    update_tableau();
                }
            });

        } catch (IOException ex) {
            Logger.getLogger(Liste_quizController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
        
    @FXML
    public void search_quiz(){
        String str = txt_search.getText();
        ObservableList<Quiz> quizs = quiz_dao.search_quiz_sql(str);
        t_liste_quiz.getItems().clear();
        t_liste_quiz.setItems(quizs);
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
    
    
    
}
