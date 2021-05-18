/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author Parsath
 */
public class Answer {
    
    private int id, questionId;
    private String suggestion;

    public Answer(int id, int questionId, String suggestion) {
        this.id = id;
        this.questionId = questionId;
        this.suggestion = suggestion;
    }

    public Answer(int questionId, String suggestion) {
        this.questionId = questionId;
        this.suggestion = suggestion;
    }

    public Answer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Override
    public String toString() {
        return "Answer{" + "id=" + id + ", questionId=" + questionId + ", suggestion=" + suggestion + '}';
    }
    
    
    
    
}
