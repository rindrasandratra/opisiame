/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.database;

import java.sql.*;
import java.util.*;
import java.io.*;

/**
 *
 * @author clement
 */
public class Connection_db {

    static Connection database = null;

    public Connection_db() {
        //chargement du fichier de ressources

        if (database == null) {
            try {
                Properties propriete = new Properties();
                InputStream prop_file = new FileInputStream("propriete_db.properties");

                //chargement du fichier de propriété
                propriete.load(prop_file);
                String driver = propriete.getProperty("driver");
                String url = propriete.getProperty("url");
                String user = propriete.getProperty("user");
                String password = propriete.getProperty("password");

                //connection
                database = DriverManager.getConnection(url);

            } catch (FileNotFoundException ex) {
                System.err.println("erreur de l'ouverture du fichier de propriété de connection");
            } catch (IOException ex) {
                System.err.println("Le fichier de propriete ne peut pas être ouvert");
            } catch (SQLException ex) {
                System.err.println("Connection avec la base de données impossible");
                ex.printStackTrace();
            }
        }
    }
}
