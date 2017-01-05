/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import opisiame.database.Connection_db;
import opisiame.model.Participation_quiz;
import opisiame.model.Quiz;

/**
 *
 * @author Sandratra
 */
public class Participation_quiz_dao {
    
    Quiz_dao quiz_dao = new Quiz_dao();
    
    public ObservableList<Quiz> get_quizs(ObservableList<Participation_quiz> participation_quizs){
        ObservableList<Quiz> quizs = FXCollections.observableArrayList();
        List<Integer> ids = new ArrayList<>();
        for (Participation_quiz participation_quiz : participation_quizs) {
            if (!ids.contains(participation_quiz.getQuiz_id())){
                ids.add(participation_quiz.getQuiz_id());
                Quiz q = quiz_dao.get_quiz_by_id(participation_quiz.getQuiz_id());
                System.out.println("nono : "+ q.getNom());
                System.out.println("id : "+ q.getId());
                quizs.add(q);
            }
        }
        return quizs;
    }
    
    public ObservableList<Timestamp> get_dates_participations(Integer quiz_id, ObservableList<Participation_quiz> participation_quizs ){
        ObservableList<Timestamp> dates = FXCollections.observableArrayList();
        for (Participation_quiz participation_quiz : participation_quizs) {
            if (Objects.equals(participation_quiz.getQuiz_id(), quiz_id))
                dates.add(participation_quiz.getDate_participation());
        }
        return dates;
    }
    
    public Participation_quiz get_part_quiz(Timestamp d, Integer i, ObservableList<Participation_quiz> participation_quizs ){
        for (Participation_quiz participation_quiz : participation_quizs) {
            if ((participation_quiz.getDate_participation() == d) && Objects.equals(participation_quiz.getQuiz_id(), i)){
                return participation_quiz;
            }
        }
        return null;
    }

    public ObservableList<Participation_quiz> get_participation_quizs() {
        ObservableList<Participation_quiz> participation_quizs = FXCollections.observableArrayList();
        String SQL = "SELECT DISTINCT(Date_participation), Quiz_id, Participation_id FROM participant_quiz";
        try {
            Connection connection = Connection_db.getDatabase();
            PreparedStatement ps = connection.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Participation_quiz participation_quiz = new Participation_quiz();
                participation_quiz.setId(rs.getInt(3));
                participation_quiz.setDate_participation(rs.getTimestamp(1));
                System.out.println("oo : "+ rs.getDate(1) + " ii : "+ rs.getInt(2));
                participation_quiz.setQuiz_id(rs.getInt(2));
                List<Integer> liste_parts = get_participants_quizs(participation_quiz);
                participation_quiz.setParticipants(liste_parts);
                participation_quizs.add(participation_quiz);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return participation_quizs;
    }
    
    public List<Integer> get_participants_quizs(Participation_quiz participation_quiz) {
        List<Integer> liste_participants = new ArrayList<>();
        String SQL = "SELECT Part_id FROM participant_quiz WHERE Date_participation = ? AND Quiz_id = ?";
        try {
            Connection connection = Connection_db.getDatabase();
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setTimestamp(1, participation_quiz.getDate_participation());
            ps.setInt(2, participation_quiz.getQuiz_id());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                liste_participants.add(rs.getInt(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return  liste_participants;
    }
}
