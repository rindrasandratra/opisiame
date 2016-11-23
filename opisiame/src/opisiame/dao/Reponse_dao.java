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
import java.util.ArrayList;
import opisiame.database.Connection_db;
import opisiame.model.Reponse;

/**
 *
 * @author Sandratra
 */
public class Reponse_dao {

    public Reponse_dao() {
    }
    public void insert_new_reponse(Reponse rep){
        String SQL = "INSERT INTO reponse (Rep_libelle, Rep_bonne,Quest_id) VALUES (?,?,?)";
        Integer insert_id = null;
        try {
            Connection connection = Connection_db.getDatabase();
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setString(1, rep.getLibelle());
            ps.setInt(2, rep.getIs_bonne_reponse());
            ps.setInt(3, rep.getQuest_id());
            int succes = ps.executeUpdate();
            if (succes == 0) {
                System.err.println("Échec de la création de la réponse, aucune ligne ajoutée dans la table.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Reponse> get_reponses_by_quest(Integer quest_id){
        ArrayList<Reponse> reponses = new ArrayList<>();
        String SQL = "SELECT *  FROM reponse WHERE Quest_id = ?";
        try {
            Connection connection = Connection_db.getDatabase();
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setInt(1, quest_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reponse rep = new Reponse();
                rep.setId(rs.getInt(1));
                rep.setLibelle(rs.getString(2));
                rep.setIs_bonne_reponse(rs.getInt(3));
                rep.setQuest_id(rs.getInt(4));
                reponses.add(rep);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return reponses;
    }
}
