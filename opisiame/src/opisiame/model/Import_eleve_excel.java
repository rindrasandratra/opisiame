/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.model;

import java.io.*;
import java.util.Iterator;
/*import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;*/
import java.util.*;
import java.util.Collection.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/*import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;*/

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
    /*private XSSFWorkbook classeur;*/

    public Import_eleve_excel() throws IOException {
        System.out.print("appelle constructeur import eleve excel \n");
        nb_element = 0;
       /* ouverture_fichier();*/
        System.out.print("je suppose que ça doit marcher vu qu'il n'y a pas d'erreur \n");
    }
/*
    private void ouverture_fichier() throws IOException {
        InputStream is = new FileInputStream("/home/clement/Lycée/2016_2017/plp/test.xlsx");
        OPCPackage opc;
        try {
            opc = OPCPackage.open(is);
            classeur = new XSSFWorkbook(opc);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(Import_eleve_excel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }*/

};
