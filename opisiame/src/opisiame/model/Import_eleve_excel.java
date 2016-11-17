/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.model;

import java.io.*;
import java.sql.*;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import opisiame.database.Connection_db;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author clement
 */
//Dans cette classe, on utilise la librarie apache POI pour lire le fichier Excel
public class Import_eleve_excel {

    private List<String> Liste_nom = new ArrayList<>();
    private List<String> Liste_prenom = new ArrayList<>();
    private List<String> Liste_filiere = new ArrayList<>();
    private List<Integer> Liste_annee = new ArrayList<>();
    private int nb_element; //nombre d'élément dans le fichier excel
    private XSSFWorkbook classeur;
    //private String adresse;

    public Import_eleve_excel(String adresse) throws IOException {
        //openning the document
        //System.out.print("appelle constructeur import eleve excel \n");
        nb_element = 0;
        ouverture_fichier(adresse);
        //System.out.print("je suppose que ça doit marcher vu qu'il n'y a pas d'erreur \n");

        //reading the document
        Sheet sheet = classeur.getSheetAt(0);
        //creer un itérateur sur les colonnes
        Iterator<Row> iterator = sheet.iterator();

        nb_element = 0;
        while (iterator.hasNext()) {
            ++nb_element;
            Row row = iterator.next();
            Iterator<Cell> cell_iterator = row.cellIterator();

            Cell cell = cell_iterator.next();

            Liste_nom.add(cell.getStringCellValue());
            cell = cell_iterator.next();
            Liste_prenom.add(cell.getStringCellValue());
            cell = cell_iterator.next();
            Liste_filiere.add(cell.getStringCellValue());
            cell = cell_iterator.next();
            Liste_annee.add((int) cell.getNumericCellValue());
        }

        //test des sorties
       /* for (int i = 0; i < Liste_nom.size(); ++i) {
            System.out.print(Liste_nom.get(i) + " ");
            System.out.print(Liste_prenom.get(i) + " ");
            System.out.print(Liste_filiere.get(i) + " ");
            System.out.print(Liste_annee.get(i) + " \n");
        }*/

        update_database();

        /* while (cell_iterator.hasNext()) {
                Cell cell = cell_iterator.next();
                
                

                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        System.out.print(cell.getStringCellValue() + " 1");
                        System.out.print("\n");
                        
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        System.out.print(cell.getNumericCellValue() + " 2");
                        System.out.print("\n");
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        System.out.print(cell.getBooleanCellValue() + " 3");
                        System.out.print("\n");
                        break;
                }*/
    }

    /*public Import_eleve_excel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/

    private void ouverture_fichier(String adresse) throws IOException {
        InputStream is = new FileInputStream(/*"/home/clement/Lycée/2016_2017/plp/test.xlsx"*/adresse);
        OPCPackage opc;
        try {
            opc = OPCPackage.open(is);
            classeur = new XSSFWorkbook(opc);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(Import_eleve_excel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void update_database() {
        try {
            for (int i = 0; i < nb_element; ++i) {
                Connection connection = Connection_db.getDatabase();
                PreparedStatement ps = connection.prepareStatement("INSERT INTO participant (Part_nom, Part_prenom, Filiere_id) "
                        + "VALUES ( ?, ?, (SELECT Filiere_id FROM filiere Where Filiere = ? AND Annee = ? ) )");
            ps.setString(1, Liste_nom.get(i));
            ps.setString(2, Liste_prenom.get(i));
            ps.setString(3, Liste_filiere.get(i));
            ps.setInt(4, Liste_annee.get(i));
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Import_eleve_excel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

};
