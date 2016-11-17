/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller.gestion_eleve;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import opisiame.model.Import_eleve_excel;

/**
 * FXML Controller class
 *
 * @author clement
 */
public class Import_excelController implements Initializable {

    @FXML
    private TextField zone_url;
    @FXML
    private AnchorPane content;
    
    private String adresse;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @FXML
    public void Valider() throws IOException {

        adresse = zone_url.getText();
        Import_eleve_excel coucou = new Import_eleve_excel(adresse);
        fermer();
    }

    
    @FXML
    private void fermer() throws IOException {
        Stage stage = (Stage) content.getScene().getWindow();
        stage.close();
    }

}
