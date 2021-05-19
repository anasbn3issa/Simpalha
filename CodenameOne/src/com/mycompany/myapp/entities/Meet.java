/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

import com.codename1.io.Util;


/**
 *
 * @author α Ω
 */
public class Meet {
    
    private int id_student,id_helper, feedback_id, etat;
    private String id, specialite, time, feedback, unameStd, unameHlp;
    
    public Meet(){
        
    }

    public Meet(int id_student, int id_helper, String specialite, String disponibilite_id) {
        this.id_student = id_student;
        this.id_helper = id_helper;
        this.time = disponibilite_id;
        this.specialite = specialite;
        this.id = Util.getUUID();
        this.etat = 0;
    }

    public Meet(String id, int id_student, int id_helper, int feedback_id, String specialite, String disponibilite_id, int etat) {
        this.id_student = id_student;
        this.id_helper = id_helper;
        this.time = disponibilite_id;
        this.feedback_id = feedback_id;
        this.id = id;
        this.specialite = specialite;
        this.etat = etat;
    }

    public String getUnameStd() {
        return unameStd;
    }

    public void setUnameStd(String unameStd) {
        this.unameStd = unameStd;
    }

    public String getUnameHlp() {
        return unameHlp;
    }

    public void setUnameHlp(String unameHlp) {
        this.unameHlp = unameHlp;
    }
    
    
    
    

    public int getId_student() {
        return id_student;
    }

    public void setId_student(int id_student) {
        this.id_student = id_student;
    }

    public int getId_helper() {
        return id_helper;
    }

    public void setId_helper(int id_helper) {
        this.id_helper = id_helper;
    }

    public int getFeedback_id() {
        return feedback_id;
    }

    public void setFeedback_id(int feedback_id) {
        this.feedback_id = feedback_id;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
    
    

    public String getTime() {
        return time;
    }

    public void setTime(String disponibilite_id) {
        this.time = disponibilite_id;
    }
    
    

    public String getId() {
        return id;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    @Override
    public String toString() {
        return "Meet{" + "id_student=" + id_student + ", id_helper=" + id_helper + ", feedback_id=" + feedback_id + ", etat=" + etat + ", id=" + id + ", specialite=" + specialite + ", time=" + time + ", feedback=" + feedback + ", unameStd=" + unameStd + ", unameHlp=" + unameHlp + '}';
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }
    
        
    
    
}
