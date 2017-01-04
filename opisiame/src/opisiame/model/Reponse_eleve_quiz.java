/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Audrey
 */
public class Reponse_eleve_quiz {

    private IntegerProperty num_ques = new SimpleIntegerProperty();
    private StringProperty rep_quiz = new SimpleStringProperty();
    private StringProperty rep_eleve = new SimpleStringProperty();

    public Reponse_eleve_quiz() {
    }

    public Reponse_eleve_quiz(IntegerProperty num, StringProperty rep_q, StringProperty rep_e) {
        this.num_ques = num;
        this.rep_quiz = rep_q;
        this.rep_eleve = rep_e;
    }
    
     public Reponse_eleve_quiz(Integer num, String rep_q, String rep_e) {
        this.num_ques.set(num);
        this.rep_quiz.set(rep_q);
        this.rep_eleve.set(rep_e);
    }

    public Integer getNum_ques() {
        return num_ques.get();
    }

    public void setNum_ques(Integer num_ques) {
        this.num_ques.set(num_ques);
    }

    public String getRep_quiz() {
        return rep_quiz.get();
    }

    public void setRep_quiz(String rep_quiz) {
        this.rep_quiz.set(rep_quiz);
    }

    public String getRep_eleve() {
        return rep_eleve.get();
    }

    public void setRep_eleve(String rep_eleve) {
        this.rep_eleve.set(rep_eleve);
    }
     
      
//    public Integer getnum_question() {
//        return num_ques.get();
//    }
//
//    public void setnum_question(Integer id) {
//        this.num_ques.set(id);
//    }
//
//    public String getrep_q() {
//        return rep_quiz.get();
//    }
//
//    public void setrep_q(String nom) {
//        this.rep_quiz.set(nom);
//    }
//
//    public String getrep_e() {
//        return rep_eleve.get();
//    }
//
//    public void setrep_e(String lg) {
//        this.rep_eleve.setValue(lg);
//    }


}
