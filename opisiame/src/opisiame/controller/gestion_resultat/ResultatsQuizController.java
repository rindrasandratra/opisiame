/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller.gestion_resultat;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import opisiame.database.Connection_db;
import opisiame.model.Quiz;

/**
 * FXML Controller class
 *
 * @author Audrey
 */
public class ResultatsQuizController implements Initializable {

    @FXML
    private GridPane content;
    @FXML
    private TableView Tableau;
    @FXML
    private TableColumn c_quiz;
    @FXML
    private TableColumn c_selection;
    @FXML
    private TextField Champs_recherche;
    @FXML
    private TableColumn c_date;

    private String Cont_recherche = null;
    private ObservableList<Quiz> quiz = FXCollections.observableArrayList();
    private String date_part;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        c_quiz.setCellValueFactory(new PropertyValueFactory<Quiz, String>("nom"));
        c_date.setCellValueFactory(new PropertyValueFactory<Quiz, String>("date_participation"));
        getAllQuiz();
        Tableau.setItems(quiz);

        //Insert Button    
        c_selection.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Quiz, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Quiz, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });

        c_selection.setCellFactory(new Callback<TableColumn<Quiz, Boolean>, TableCell<Quiz, Boolean>>() {

            @Override
            public TableCell<Quiz, Boolean> call(TableColumn<Quiz, Boolean> p) {
                return new ButtonCell();
            }

        });

        Tableau.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Node node = ((Node) event.getTarget()).getParent();
                TableRow row;
                if (node instanceof TableRow) {
                    row = (TableRow) node;
                    Tableau.getSelectionModel().select(row.getIndex());
                }
            }
        });
    }

    private class ButtonCell extends TableCell<Quiz, Boolean> {

        final Button btn_info = new Button();

        ButtonCell() {
            btn_info.setStyle("-fx-background-color: gray");
            btn_info.setCursor(Cursor.HAND);

            btn_info.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    info_evaluation();
                    Button bt = (Button) t.getSource();
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

                Image img_detail = new Image(getClass().getResourceAsStream("/opisiame/image/detail.png"), 20, 20, true, true);
                btn_info.setGraphic(new ImageView(img_detail));

                box.setPadding(new Insets(5, 0, 5, 0));//ajout de marge à l'interieur du bouton
                // box.setPrefColumns(1);
                box.getChildren().add(btn_info);
                setGraphic(box);
            }
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

    public void info_evaluation() {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/opisiame/view/gestion_resultat/Choix_resultat.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Choix_resultatController edit_controller = fxmlLoader.<Choix_resultatController>getController();
            Quiz s = (Quiz) Tableau.getFocusModel().getFocusedItem();
            int a = s.getId();
            String d = s.getDate_participation();
            edit_controller.setId(a);
            edit_controller.setDate(d);
            

            URL url = fxmlLoader.getLocation();
            ResourceBundle rb = fxmlLoader.getResources();
            edit_controller.initialize(url, rb);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Choix du type d'évaluation");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initOwner(Tableau.getScene().getWindow());
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/opisiame/image/icone.png")));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    //update_tableau();
                }
            });

        } catch (IOException ex) {
            Logger.getLogger(Choix_resultatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update_tableau() {
        Tableau.getItems().clear();
        quiz.clear();
        //getAllEleve(/*eleves*/);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/opisiame/view/gestion_resultat/Choix_resultatController.fxml"));
        URL url = fxmlLoader.getLocation();
        ResourceBundle rb = fxmlLoader.getResources();
        this.initialize(url, rb);
        Tableau.setItems(quiz);
    }

    public void Rechercher() {
        Cont_recherche = Champs_recherche.getText();
        //System.out.println(Cont_recherche);
        update_tableau();
    }

    @FXML
    public void ClicBoutonRetour() throws IOException {
        //ouverture fenêtre menu_anim
        Stage stage = (Stage) content.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/utilisateur/menu_ens.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void getAllQuiz() {
        try {
            Connection connection = Connection_db.getDatabase();
            PreparedStatement requette;

            if (Cont_recherche != null) {
                requette = connection.prepareStatement("SELECT DISTINCT quiz.Quiz_id, quiz.Quiz_nom, participant_quiz.Date_participation "
                        + "FROM quiz, participant_quiz "
                        + "WHERE quiz.Ens_id = 1 AND quiz.Quiz_nom LIKE ?");
                requette.setString(1, "%" + Cont_recherche + "%");
            } else {
                requette = connection.prepareStatement("SELECT DISTINCT quiz.Quiz_id, quiz.Quiz_nom, participant_quiz.Date_participation "
                        + "FROM quiz, participant_quiz "
                        + "WHERE quiz.Ens_id = 1");
            }
            ResultSet res_requette = requette.executeQuery();
            while (res_requette.next()) {
                Quiz curent_quiz = new Quiz();
                curent_quiz.setId(res_requette.getInt(1));
                curent_quiz.setNom(res_requette.getString(2));
                curent_quiz.setDate_participation(res_requette.getString(3));
                quiz.add(curent_quiz);
                }
                //System.out.print("quiz ajouter à la liste " + curent_quiz.getNom() + "\n");
                //System.out.print("quiz dans la liste " + quiz.get(quiz.size() - 1).getNom() + "\n");
            
            //System.out.println("taille : "+quiz.size());

            //System.out.println(etudiant.getId());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
    
   
}
