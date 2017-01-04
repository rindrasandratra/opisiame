/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.dao;

import java.util.ArrayList;
import java.util.List;
import opisiame.model.Reponse;
import opisiame.model.Reponse_question;

/**
 *
 * @author Sandratra
 */
public class Reponse_question_dao {

    Reponse_dao reponse_dao = new Reponse_dao();

    public Reponse_question get_res_by_quest(Integer quest_id) {
        Reponse_question rq = new Reponse_question();
        List<Reponse> reponses = reponse_dao.get_reponses_by_quest(quest_id);
        List<Integer> nb_reps = new ArrayList<>();
        Integer nb_bonne_rep = 0;
        for (Reponse reponse : reponses) {
            Integer value = reponse_dao.get_repondant_rep(reponse.getId()).size();
            nb_reps.add(value);
            if (reponse.getIs_bonne_reponse() == 0) {
                nb_bonne_rep = value;
            }
        }
        Double tot = 0.0;
        for (Integer nb_rep : nb_reps) {
            tot += nb_rep;
        }
        if (tot == 0.0) {
            rq.setPourcentage_rep_a((double) nb_reps.get(0));
            rq.setPourcentage_rep_b((double) nb_reps.get(1));
            rq.setPourcentage_rep_c((double) nb_reps.get(2));
            rq.setPourcentage_rep_d((double) nb_reps.get(3));
            rq.setPourcentage((double) nb_bonne_rep);
        } else {
            rq.setPourcentage_rep_a((double)nb_reps.get(0) *100 / tot);
            rq.setPourcentage_rep_b((double)nb_reps.get(1) *100 / tot);
            rq.setPourcentage_rep_c((double) nb_reps.get(2) *100 / tot);
            rq.setPourcentage_rep_d((double) nb_reps.get(3) *100 / tot);
            rq.setPourcentage((double) nb_bonne_rep / tot);
        }
        return rq;
    }
}
