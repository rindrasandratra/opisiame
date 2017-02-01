/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller.gestion_eleve;

import com.rapplogic.xbee.XBeePin;
import com.rapplogic.xbee.api.ApiId;
import gnu.io.CommPortIdentifier;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import opisiame.controller.gestion_quiz.Lancer_questionController;
import opisiame.database.Connection_db;
import opisiame.model.Eleve;

import com.rapplogic.xbee.api.ErrorResponse;
import com.rapplogic.xbee.api.RemoteAtRequest;

import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeAddress64;
import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.api.XBeeResponse;
import com.rapplogic.xbee.api.wpan.IoSample;
import com.rapplogic.xbee.api.wpan.RxResponseIoSample;
import java.util.logging.Level;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.input.MouseEvent;

import org.apache.log4j.Logger;

/**
 * FXML Controller class
 *
 * @author Audrey
 */
public class Link_eleve_teleController implements Initializable {

    private ObservableList<Eleve> eleves = FXCollections.observableArrayList();

    @FXML
    private AnchorPane content;
    @FXML
    private Button btn_valider;
    @FXML
    private Button btn_lancer_quiz;
    @FXML
    private TableView<Eleve> Tableau;
    @FXML
    private TableColumn<Eleve, Boolean> Action_col;
    @FXML
    private TableColumn<Eleve, Integer> id;
    @FXML
    private TableColumn<Eleve, String> Nom;
    @FXML
    private TableColumn<Eleve, String> Prenom;
    @FXML
    private TableColumn<Eleve, String> Filiere;
    @FXML
    private TableColumn<Eleve, Integer> Annee;
    @FXML
    private TableColumn<Eleve, String> tel;
    @FXML
    private TextField Champ_recherche;
    @FXML
    private TextField tf_mac_telec;
    @FXML
    private ComboBox choix_port;

    private String num_port;

    private String Cont_recherche = null;
    private Integer quiz_timer;
    private Integer quiz_id;

    private final static Logger log = Logger.getLogger(Link_eleve_teleController.class);

    private XBee xbee = new XBee();

    String led_yellow = "D7";
    String led_green = "D5";
    String led_red = "D4";


    /*
    DIO0 boutton vert
    DIO1 boutton gris
    DIO2 boutton rouge
    DIO3 boutton bleu
     */
    public void setQuiz_timer(Integer qt) {
        quiz_timer = qt;
    }

    public void setQuiz_id(Integer qt) {
        quiz_id = qt;
    }

    /*
    Fonction qui récupère la liste des élèves
     */
    public void getAllEleve(/*ObservableList<Eleve> eleves*/) {
        try {
            //ObservableList<Eleve> eleves = FXCollections.observableArrayList();
            Connection connection = Connection_db.getDatabase();
            PreparedStatement requette;

            if (Cont_recherche != null) {
                requette = connection.prepareStatement("SELECT participant.Part_id, participant.Part_nom, participant.Part_prenom, filiere.Filiere, filiere.Annee FROM participant \n"
                        + "LEFT JOIN filiere \n"
                        + "ON filiere.Filiere_ID = participant.Filiere_id\n"
                        + "WHERE participant.Part_id LIKE ?\n"
                        + "OR participant.Part_nom LIKE ?\n"
                        + "OR participant.Part_prenom LIKE ?\n"
                        + "OR filiere.Filiere LIKE ?\n"
                        + "OR filiere.Annee LIKE ?");
                requette.setString(1, "%" + Cont_recherche + "%");
                requette.setString(2, "%" + Cont_recherche + "%");
                requette.setString(3, "%" + Cont_recherche + "%");
                requette.setString(4, "%" + Cont_recherche + "%");
                requette.setString(5, "%" + Cont_recherche + "%");
            } else {
                requette = connection.prepareStatement("SELECT participant.Part_id, participant.Part_nom, participant.Part_prenom, filiere.Filiere, filiere.Annee FROM participant \n"
                        + "LEFT JOIN filiere \n"
                        + "ON filiere.Filiere_ID = participant.Filiere_id");
            }
            ResultSet res_requette = requette.executeQuery();
            while (res_requette.next()) {
                Eleve etudiant = new Eleve();
                etudiant.setId(res_requette.getInt(1));
                etudiant.setNom(res_requette.getString(2));
                etudiant.setPrenom(res_requette.getString(3));
                etudiant.setFiliere(res_requette.getString(4));
                etudiant.setAnnee(res_requette.getInt(5));
                eleves.add(etudiant);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id.setCellValueFactory(new PropertyValueFactory<Eleve, Integer>("id"));
        Nom.setCellValueFactory(new PropertyValueFactory<Eleve, String>("Nom"));
        Prenom.setCellValueFactory(new PropertyValueFactory<Eleve, String>("Prenom"));
        Filiere.setCellValueFactory(new PropertyValueFactory<Eleve, String>("Filiere"));
        Annee.setCellValueFactory(new PropertyValueFactory<Eleve, Integer>("Annee"));
        tel.setCellValueFactory(new PropertyValueFactory<Eleve, String>("Adresse_mac_tel"));

        getAllEleve(/*eleves*/);
        Tableau.setItems(eleves);
        //PropertyConfigurator.configure("log4j.properties");
        init_liste_port();

        Tableau.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Node node = ((Node) event.getTarget()).getParent();
                TableRow row;
                if (node instanceof TableRow) {

                    row = (TableRow) node;
                    select_etudiant();
                }
            }
        });
    }

    public void init_liste_port() {
        Enumeration pList = CommPortIdentifier.getPortIdentifiers();
        System.out.println("taille liste : " + pList.toString());
        // Process the list.
        while (pList.hasMoreElements()) {
            CommPortIdentifier cpi = (CommPortIdentifier) pList.nextElement();
            System.out.print("Port " + cpi.getName() + " ");
            if (cpi.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                System.out.println("is a Serial Port: " + cpi);
                choix_port.getItems().add(cpi.getName());
            }
        }
    }

    public void update_tableau() {
        Tableau.getItems().clear();
        eleves.clear();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/opisiame/view/eleve/Liste_eleves_adminController.fxml"));
        URL url = fxmlLoader.getLocation();
        ResourceBundle rb = fxmlLoader.getResources();
        this.initialize(url, rb);
        Tableau.setItems(eleves);
        Tableau.refresh();
    }

    public void Rechercher() {
        Cont_recherche = Champ_recherche.getText();
        update_tableau();
    }

    @FXML
    public void select_port() {
        num_port = choix_port.getSelectionModel().getSelectedItem().toString();
        System.out.println("choix port : " + num_port);
        try {

            xbee.open(num_port, 9600);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Bouton valider
    @FXML
    public void ClicBoutonValider() throws IOException {
        //ajouter dans l'appli le couple eleve/@mac pour savoir qui a répondu quoi au quiz
        if (!"".equals(tf_mac_telec.getText())) {
            String adr = tf_mac_telec.getText();
            if (!test_if_tel_exists(adr)) {
                Tableau.getSelectionModel().getSelectedItem().setAdresse_mac_tel(adr);
                Tableau.refresh();
            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Attention");
                alert.setHeaderText("Télécommande déjà enregistrée pour un autre étudiant");
                alert.setContentText("Veuillez resélectionner un étudiant et une autre télécommande");
                alert.showAndWait();
            }
        }
        tf_mac_telec.deleteText(0, tf_mac_telec.getText().length());
    }

    public Boolean test_if_tel_exists(String adr) {
        for (int i = 0; i < eleves.size(); i++) {
            if (eleves.get(i).getAdresse_mac_tel() != null) {
                if (eleves.get(i).getAdresse_mac_tel().equals(adr)) {
                    return true;
                }
            }
        }
        return false;
    }

    //Bouton valider
    @FXML
    public void ClicBoutonLancerQuiz() throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/opisiame/view/gestion_quiz/lancer_question.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Lancer_questionController lancer_ques_controller = fxmlLoader.<Lancer_questionController>getController();
            lancer_ques_controller.setQuiz_timer(quiz_timer);
            lancer_ques_controller.setQuiz_id(quiz_id);
            lancer_ques_controller.setEleves(eleves);
            lancer_ques_controller.setXbee(xbee);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Animation quiz");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/opisiame/image/icone.png")));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //boutton log out
    @FXML
    public void ClicBoutonHome() throws IOException {
        //Retour sur la fenetre d'identification
        Stage stage = (Stage) content.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/opisiame/view/utilisateur/interface_authentification.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        session.Session.Logout();
    }

    
    public void select_etudiant() {
        System.out.println("selectes");
        if (Tableau.getSelectionModel().getSelectedItem().getAdresse_mac_tel() == null) {
            try {
                btn_valider.setDisable(true);
                System.out.println("wait");
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Attention");
                alert.setHeaderText(null);
                alert.setContentText("Appuyez sur une des télecommandes");
                alert.show();
                Thread.sleep(2500);
                alert.close();

                if ((num_port != "") && (num_port != null)) {
                    try {
                        tf_mac_telec.setText("Attente appui télécommande");
                        while (true) {
                            xbee.clearResponseQueue();
                            XBeeResponse response = xbee.getResponse();

                            if (response.isError()) {
                                log.info("response contains errors", ((ErrorResponse) response).getException());
                                continue;
                            }

                            ProcessResponse processResponse = new ProcessResponse(response);
                            processResponse.start();

                            break;

                        }
                    } catch (Exception e) {
                        log.error(e);
                    }
                }
            } catch (InterruptedException ex) {
                java.util.logging.Logger.getLogger(Link_eleve_teleController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void switch_on_led(String led_id, XBeeAddress64 address_remote) throws XBeeException {

        RemoteAtRequest request_led_on = new RemoteAtRequest(address_remote, led_id, new int[]{XBeePin.Capability.DIGITAL_OUTPUT_HIGH.getValue()});
        xbee.sendAsynchronous(request_led_on);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            java.util.logging.Logger.getLogger(Link_eleve_teleController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // xbee.clearResponseQueue();
    }

    public void switch_off_led(String led_id, XBeeAddress64 address_remote) throws XBeeException {
        RemoteAtRequest request_led_off = new RemoteAtRequest(address_remote, led_id, new int[]{XBeePin.Capability.DIGITAL_OUTPUT_LOW.getValue()});
        xbee.sendAsynchronous(request_led_off);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            java.util.logging.Logger.getLogger(Link_eleve_teleController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // xbee.clearResponseQueue();
    }

    class ProcessResponse extends Thread {

        XBeeResponse response;

        public ProcessResponse(XBeeResponse response) {
            this.response = response;
        }

        XBeeAddress64 adress_mac;

        public XBeeAddress64 getAdress_mac() {
            return adress_mac;
        }

        public void setAdress_mac(XBeeAddress64 adress_mac) {
            this.adress_mac = adress_mac;
        }

        @Override
        public void run() {
            if (response.getApiId() == ApiId.RX_64_IO_RESPONSE) {
                RxResponseIoSample ioSample = (RxResponseIoSample) response;

                System.err.println("iosample ::: " + ioSample.toString());

                XBeeAddress64 address_remote = (XBeeAddress64) ioSample.getSourceAddress();

                tf_mac_telec.setText(address_remote.toString());

                btn_valider.setDisable(false);

                this.adress_mac = address_remote;

                System.err.println("address64 : " + address_remote);

                try {
                    for (IoSample sample : ioSample.getSamples()) {
                        if (!ioSample.containsAnalog()) {
                            switch_on_led(led_green, address_remote);
                        }
                    }
                } catch (XBeeException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
