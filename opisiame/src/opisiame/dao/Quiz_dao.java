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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import opisiame.database.Connection_db;
import opisiame.model.Quiz;
import session.Session;

/**
 *
 * @author Sandratra
 */
public class Quiz_dao {

    public Quiz_dao() {
    }

    public Quiz get_quiz_by_id(Integer quiz_id) {
        Quiz quiz = new Quiz();
        try {
            Connection connection = Connection_db.getDatabase();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM quiz WHERE Quiz_id = ? ");
            ps.setInt(1, quiz_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                quiz.setId(rs.getInt(1));
                quiz.setNom(rs.getString(2));
                quiz.setDate_creation(rs.getString(3));
                quiz.setTimer(rs.getInt(4));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return quiz;
    }
    
    public Integer count_nb_quest(Integer quiz_id) {
        try {
            int nb_quest = 0;
            Connection connection = Connection_db.getDatabase();
            PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM question WHERE Quiz_id = ? ");
            ps.setInt(1, quiz_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                nb_quest = rs.getInt(1);
            }
            return nb_quest;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public void delete_quiz(Integer id){
        try {
            Connection connection = Connection_db.getDatabase();
            PreparedStatement ps = connection.prepareStatement("DELETE FROM quiz WHERE Quiz_id = ?");
            ps.setInt(1, id);
            int succes = ps.executeUpdate();
            if (succes == 0) {
                System.err.println("Erreur lors de la suppression du quiz");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public ObservableList<Quiz> getAllquiz() {
        ObservableList<Quiz> quizs = FXCollections.observableArrayList();
        try {
            Connection connection = Connection_db.getDatabase();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM quiz");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Quiz quiz = new Quiz();
                quiz.setId(rs.getInt(1));
                quiz.setNom(rs.getString(2));
                quiz.setDate_creation(rs.getString(3));
                quiz.setTimer(rs.getInt(4));
                quizs.add(quiz);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return quizs;
    }
    
    public ObservableList<Quiz> search_quiz_sql(String str){
        ObservableList<Quiz> quizs = FXCollections.observableArrayList();
        try {
            Connection connection = Connection_db.getDatabase();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM quiz WHERE Quiz_id LIKE ? OR Quiz_nom LIKE ? OR Quiz_timer LIKE ? OR CAST(Quiz_date_creation AS CHAR) LIKE ?");
            ps.setString(1, "%"+str+"%");
            ps.setString(2, "%"+str+"%");
            ps.setString(3, "%"+str+"%");
            ps.setString(4, "%"+str+"%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Quiz quiz = new Quiz();
                quiz.setId(rs.getInt(1));
                quiz.setNom(rs.getString(2));
                quiz.setDate_creation(rs.getString(3));
                quiz.setTimer(rs.getInt(4));
                quizs.add(quiz);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return quizs;
    }
    
    public void update_quiz(Integer quiz_id,String value_nom, String value_timer) {
        String SQL;
        if (value_timer.compareTo("") != 0) {
            SQL = "UPDATE quiz SET Quiz_nom = ?, Quiz_timer = ? WHERE Quiz_id = ?";
        } else {
            SQL = "UPDATE quiz SET Quiz_nom = ? WHERE Quiz_id = ?";
        }
        try {
            Connection connection = Connection_db.getDatabase();
            PreparedStatement ps = connection.prepareStatement(SQL);
            Integer num = 1;
            ps.setString(num++, value_nom);
            if (value_timer.compareTo("") != 0) {
                ps.setInt(num++, Integer.valueOf(value_timer));
            }
            ps.setInt(num, quiz_id);
            int succes = ps.executeUpdate();
            if (succes == 0) {
                System.err.println("Échec de la modification du quiz, aucune ligne modifiée dans la table.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void insert_new_quiz(String value_nom, String value_timer) {
        String SQL;
        if (value_timer.compareTo("") != 0) {
            SQL = "INSERT INTO quiz (Quiz_nom,Quiz_timer,Anim_id) VALUES (?,?,?)";
        } else {
            SQL = "INSERT INTO quiz (Quiz_nom,Anim_id) VALUES (?,?)";
        }
        try {
            Connection connection = Connection_db.getDatabase();
            PreparedStatement ps = connection.prepareStatement(SQL);
            Integer num = 1;
            ps.setString(num++, value_nom);
            if (value_timer.compareTo("") != 0) {
                ps.setInt(num++, Integer.valueOf(value_timer));
            } 
            // à enlever après test :D
            if (Session.getAnim_id() == null) {
                ps.setInt(num, 1);
            } else {
                ps.setInt(num, Session.getAnim_id());
            }
            int succes = ps.executeUpdate();
            if (succes == 0) {
                System.err.println("Échec de la création du quiz, aucune ligne ajoutée dans la table.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
