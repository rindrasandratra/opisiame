/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import opisiame.database.Connection_db;

/**
 *
 * @author Sandratra
 */
public class Sous_comp_dao {
    
    public Integer insert_new_sous_comp(String libelle, Integer comp_id) {
        String SQL = "INSERT INTO souscompetence (SousCompetence, Comp_id) VALUES (?,?)";
        Integer insert_id = null;
        try {
            Connection connection = Connection_db.getDatabase();
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setString(1, libelle);
            ps.setInt(2, comp_id);
            int succes = ps.executeUpdate();
            if (succes == 0) {
                System.err.println("Échec de la création de la question, aucune ligne ajoutée dans la table.");
            }
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()){
                insert_id = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return insert_id;
    }
}
