/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller.gestion_resultat;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.*;
import opisiame.controller.gestion_quiz.Liste_quizController;

/**
 * FXML Controller class
 *
 * @author clement
 */
public class Choix_resultatController implements Initializable {

    @FXML
    private AnchorPane content;

    private int quiz_id;
    private String date_part;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //System.out.print("constructeur appellé \n");
        //System.out.print("id de l'objet " + quiz_id +"\n");
    }

    @FXML
    public void par_eleve() throws IOException {

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/opisiame/view/gestion_resultat/Resultat_par_eleve.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Resultat_par_eleveController controller = fxmlLoader.<Resultat_par_eleveController>getController();
            int a = quiz_id;
            String d = date_part;
            controller.setDate(d);
            controller.setId(a);

            URL url = fxmlLoader.getLocation();
            ResourceBundle rb = fxmlLoader.getResources();
            controller.initialize(url, rb);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Résultat par élève");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            //stage.initOwner(content.getParent().getScene().getWindow());
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/opisiame/image/icone.png")));
            stage.setResizable(true);
            stage.show();
            Stage st = (Stage) content.getScene().getWindow();
            st.close();

        } catch (IOException ex) {
            Logger.getLogger(Choix_resultatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void par_quiz() {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/opisiame/view/gestion_resultat/Resultat_par_quiz.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Resultat_par_quizController controller = fxmlLoader.<Resultat_par_quizController>getController();
            int a = quiz_id;
            String d = date_part;
            controller.setDate(d);
            controller.setId(a);

            URL url = fxmlLoader.getLocation();
            ResourceBundle rb = fxmlLoader.getResources();
            controller.initialize(url, rb);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Résultat par quiz");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            //stage.initOwner(content.getParent().getScene().getWindow());
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/opisiame/image/icone.png")));
            stage.setResizable(true);
            stage.show();
            Stage st = (Stage) content.getScene().getWindow();
            st.close();

        } catch (IOException ex) {
            Logger.getLogger(Choix_resultatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setId(int id) {
        quiz_id = id;
    }

    public void setDate(String date) {
        date_part = date;
    }
    
    @FXML
    public void evaluation_par_question(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/gestion_resultat/resultat_questions.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Résultat quiz");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/opisiame/image/icone.png")));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
                        
            Stage st = (Stage) content.getScene().getWindow();
            st.close();


        } catch (IOException ex) {
            Logger.getLogger(Liste_quizController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
