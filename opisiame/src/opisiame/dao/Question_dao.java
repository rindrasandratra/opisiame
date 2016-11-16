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
import opisiame.model.Question;

/**
 *
 * @author Sandratra
 */
public class Question_dao {

    public Question_dao() {
    }
    
    Reponse_dao reponse_dao = new Reponse_dao();
    
    public ArrayList<Question> get_questions_by_quiz(Integer quiz_id){
        ArrayList<Question> questions = new ArrayList<>();
        String SQL = "SELECT question.*, souscompetence.SousCompetence  FROM question"+
                     " JOIN souscompetence ON souscompetence.SousComp_id = question.SousComp_id "+
                "WHERE Quiz_id = ?";
        try {
            Connection connection = Connection_db.getDatabase();
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setInt(1, quiz_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Question quest = new Question();
                quest.setId(rs.getInt(1));
                quest.setLibelle(rs.getString(2));
                quest.setTimer(rs.getInt(3));
                quest.setQuiz_id(rs.getInt(4));
                quest.setSous_comp_id(rs.getInt(5));
                quest.setUrl_img(rs.getString(6));
                quest.setSous_comp(rs.getString(7));
                quest.setReponses(reponse_dao.get_reponses_by_quest(quest.getId()));
                questions.add(quest);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return questions;
    }
    
    public void update_question(Integer id,String libelle, Integer timer, Integer sous_comp_id, String url_img) {
        String SQL = "UPDATE question SET Quest_libelle = ?, Quest_timer = ? , SousComp_id = ?, Quest_img = ? WHERE Quest_id = ?";
        try {
            Connection connection = Connection_db.getDatabase();
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setString(1, libelle);
            ps.setInt(2, timer);
            ps.setInt(3, sous_comp_id);
            ps.setString(4, url_img);
            ps.setInt(5, id);
            int succes = ps.executeUpdate();
            if (succes == 0) {
                System.err.println("Échec de la modification de la question, aucune ligne modifiée dans la table.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void insert_new_quiz(Integer quest_id,String libelle, Integer timer, Integer sous_comp_id, String url_img) {
        String SQL = "INSERT INTO question (Quest_libelle, Quest_timer, Quiz_id, SousComp_id, Quest_img) VALUES (?,?,?,?,?)";
        try {
            Connection connection = Connection_db.getDatabase();
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setString(1, libelle);
            ps.setInt(2, timer);
            ps.setInt(3, quest_id);
            ps.setInt(4, sous_comp_id);
            ps.setString(5, url_img);
            int succes = ps.executeUpdate();
            if (succes == 0) {
                System.err.println("Échec de la création de la question, aucune ligne ajoutée dans la table.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void delete_question(Integer id){
        try {
            Connection connection = Connection_db.getDatabase();
            PreparedStatement ps = connection.prepareStatement("DELETE FROM question WHERE Quest_id = ?");
            ps.setInt(1, id);
            int succes = ps.executeUpdate();
            if (succes == 0) {
                System.err.println("Erreur lors de la suppression de la question");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
