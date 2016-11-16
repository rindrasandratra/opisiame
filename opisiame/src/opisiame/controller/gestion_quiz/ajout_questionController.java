/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller.gestion_quiz;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.*;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import opisiame.controller.gestion_eleve.Add_eleveController;
import opisiame.database.Connection_db;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Audrey
 */
public class ajout_questionController implements Initializable {

    @FXML
    private AnchorPane content;
    @FXML
    private TextArea enonce;
    @FXML
    private TextField sous_comp;
    @FXML
    private ComboBox competence;
    @FXML
    private TextField timer;
    @FXML
    private TextField rep_a;
    @FXML
    private TextField rep_b;
    @FXML
    private TextField rep_c;
    @FXML
    private TextField rep_d;
    @FXML
    private Label label_nb_carac;
    

    private List<String> liste_Competence = new ArrayList<>();//contient les champs "competence" pour le combobox

    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        get_competences();
    }

    //ajout des competences dans le combobox
    @FXML
    public void get_competences() {
        Connection database = Connection_db.getDatabase();
        PreparedStatement req;
        try {
            req = database.prepareStatement("SELECT Competence From compétences");
            ResultSet res = req.executeQuery();
            while (res.next()) {
                String comp = res.getString(1);
                liste_Competence.add(comp);
                for (int i = 0; i < liste_Competence.size(); ++i) {
                    //remplissage du combobox si la compétence n'y est pas déjà
                    if (!competence.getItems().contains(liste_Competence.get(i))) {
                        competence.getItems().add(liste_Competence.get(i));
                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Add_eleveController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void ajout_image() throws IOException {

    }

    @FXML
    public void creation_question() throws IOException {

    }

    @FXML
    public void valider_quiz() throws IOException {

    }

    
    //affiche le nombre de caractères disponibles pour l'énoncé de la question
    @FXML
    public void nb_carac_restant() throws IOException{
        
        int nb_carac_dispo = 255-(enonce.getLength());
        
        label_nb_carac.setText("( " + nb_carac_dispo + " caractère(s) restant(s) )");
        
        if (nb_carac_dispo<=0){
            label_nb_carac.setTextFill(Color.web("#FF0000"));
        }
        else {
            label_nb_carac.setTextFill(Color.web("#000000"));
        }
        
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
