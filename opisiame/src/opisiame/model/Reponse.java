/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opisiame.model;

/**
 *
 * @author Sandratra
 */
public class Reponse {
    private Integer id;
    private String libelle;
    private Boolean is_bonne_reponse;
    private Integer quest_id;

    public Reponse() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Boolean getIs_bonne_reponse() {
        return is_bonne_reponse;
    }

    public void setIs_bonne_reponse(Boolean is_bonne_reponse) {
        this.is_bonne_reponse = is_bonne_reponse;
    }

    public Integer getQuest_id() {
        return quest_id;
    }

    public void setQuest_id(Integer quest_id) {
        this.quest_id = quest_id;
    }
    
    
}
