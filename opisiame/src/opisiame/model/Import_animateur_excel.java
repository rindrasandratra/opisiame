/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author zhuxiangyu
 */


public class Import_animateur_excel {
    private List<String> Liste_nom = new ArrayList<>();
    private List<String> Liste_prenom = new ArrayList<>();
    private List<String> Liste_login = new ArrayList<>();
    private List<String> Liste_mdp = new ArrayList<>();
    private int nb_element; //nombre d'élément dans le fichier excel
    private XSSFWorkbook classeur;
    
    
    private void ouverture_fichier() throws IOException {
        InputStream is = new FileInputStream("/Users/zhuxiangyu/Downloads/test_teacher.xlsx");
        OPCPackage opc;
        try {
            opc = OPCPackage.open(is);
            classeur = new XSSFWorkbook(opc);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(Import_eleve_excel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    public Import_animateur_excel() throws IOException {
        //openning the document
        System.out.print("appelle constructeur import animateur excel \n");
        nb_element = 0;
        ouverture_fichier();
        System.out.print("je suppose que ça doit marcher vu qu'il n'y a pas d'erreur \n");

        //reading the document
        Sheet sheet = classeur.getSheetAt(0);
        //creer un itérateur sur les colonnes
        Iterator<Row> iterator = sheet.iterator();

       while (iterator.hasNext()) {
            Row row = iterator.next();
            Iterator<Cell> cell_iterator = row.cellIterator();
            
           Cell cell =cell_iterator.next();
           
           Liste_nom.add(cell.getStringCellValue());
           cell = cell_iterator.next();
           Liste_prenom.add(cell.getStringCellValue());
           cell = cell_iterator.next();
           Liste_login.add(cell.getStringCellValue());
           cell = cell_iterator.next();
           //Liste_mdp.add(cell.getStringCellValue());
       }
           
           for (int i = 0; i < Liste_nom.size(); ++i){
               System.out.print(Liste_nom.get(i) + " ");
               System.out.print(Liste_prenom.get(i) + " ");
               System.out.print(Liste_login.get(i) + " ");
               //System.out.print(Liste_mdp.get(i) + " \n");
           }

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
}
