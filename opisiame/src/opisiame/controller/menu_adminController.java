/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller;

import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;

/**
 *
 * @author Audrey
 */
public class menu_adminController implements Initializable {

    @FXML
    public void ClicImageOnOff(ActionEvent event) {
        // Button was clicked, do something...
        System.out.println("clic image on_off");;
    }

    @FXML
    public void ClicBoutonEleves(ActionEvent event) {
        // Button was clicked, do something...
        System.out.println("clic bouton eleves");;
    }

    @FXML
    public void ClicBoutonProfs(ActionEvent event) {
        // Button was clicked, do something...
        System.out.println("clic bouton profs");;
    }

    @FXML
    public void ClicBoutonQuiz(ActionEvent event) throws IOException {
        // Button was clicked, do something...
        System.out.println("clic bouton quiz");;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("opisiame.view.liste_quize.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Liste des quiz");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException e) {

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
