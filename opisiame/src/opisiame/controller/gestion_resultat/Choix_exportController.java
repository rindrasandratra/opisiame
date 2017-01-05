/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.controller.gestion_resultat;

import java.io.File;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import opisiame.model.Reponse_question;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author Sandratra
 */
public class Choix_exportController implements Initializable {

    @FXML
    AnchorPane content;

    ObservableList<Reponse_question> reponse_questions;

    public void setReponse_questions(ObservableList<Reponse_question> reponse_questions) {
        //reponse_questions = FXCollections.observableArrayList();
        this.reponse_questions = reponse_questions;
    }

    @FXML
    public void excel_export() {
        File excel_file = choix_chemin_enregistrement();
        if (excel_file != null) {
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("Resultat par question");
            sheet.autoSizeColumn(5);
            create_data(sheet, 0, "Question", "Pourcentage reponse A", "Pourcentage reponse B", "Pourcentage reponse C", "Pourcentage reponse D", "Pourcentage bonne réponse");

            Row row = sheet.getRow(0);
            HSSFCellStyle cellStyle = null;
            HSSFFont font = wb.createFont();
            font.setBold(true);
            cellStyle = wb.createCellStyle();
            cellStyle.setFont(font);
            row.setRowStyle(cellStyle);

            for (int i = 0; i < reponse_questions.size(); i++) {
                Reponse_question rq = reponse_questions.get(i);
                create_data(sheet, i + 1, rq.getQuestion(), rq.getStr_pourcentage_rep_a(), rq.getStr_pourcentage_rep_b(), rq.getStr_pourcentage_rep_c(), rq.getStr_pourcentage_rep_d(), rq.getStr_pourcentage());
            }

            FileOutputStream fileOut;
            try {
                fileOut = new FileOutputStream(excel_file);
                wb.write(fileOut);
                fileOut.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //stage.close();
    }

    public void create_data(HSSFSheet sheet, Integer i, String question, String rep_a, String rep_b, String rep_c, String rep_d, String pourcentage) {
        HSSFRow row = sheet.createRow(i); // ligne i

        HSSFCell cell_question = row.createCell((short) 0); // colonne 0
        cell_question.setCellValue(question);

        HSSFCell cell_rep_a = row.createCell((short) 1); // colonne 1
        cell_rep_a.setCellValue(rep_a);

        HSSFCell cell_rep_b = row.createCell((short) 2); // colonne 2
        cell_rep_b.setCellValue(rep_b);

        HSSFCell cell_rep_c = row.createCell((short) 3); // colonne 3
        cell_rep_c.setCellValue(rep_c);

        HSSFCell cell_rep_d = row.createCell((short) 4); // colonne 4
        cell_rep_d.setCellValue(rep_d);

        HSSFCell cell_pourcentage = row.createCell((short) 5); // colonne 5
        cell_pourcentage.setCellValue(pourcentage);
    }

    public File choix_chemin_enregistrement() {
        Stage stage = (Stage) content.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choix d'enregistrement du fichier");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xls)", "*.xls");
        fileChooser.getExtensionFilters().add(extFilter);
        File selected_directory = fileChooser.showSaveDialog(stage);
        System.out.println("file : " + selected_directory.getAbsolutePath());
        return selected_directory;
    }

    @FXML
    public void pdf_export() {
        choix_chemin_enregistrement();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
