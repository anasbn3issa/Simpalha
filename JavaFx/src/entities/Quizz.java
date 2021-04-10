/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.ArrayList;

/**
 *
 * @author Parsath
 */
public class Quizz {
     
    private int id;
    private String title;
    private String subject;
    private int helperId;
    private String helperName;
    private ArrayList<Question> questions;

    public Quizz() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHelper() {
        return helperId;
    }

    public void setHelper(int helper) {
        this.helperId = helper;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getName() {
        return helperName;
    }

    public void setName(String helperName) {
        this.helperName = helperName;
    }

    @Override
    public String toString() {
        return "Quizz{" + "id=" + id + ", title=" + title + ", subject=" + subject + ", helperId=" + helperId + ", helperName=" + helperName + ", questions=" + questions + '}';
    }
    
    
    
    
    
    
}
