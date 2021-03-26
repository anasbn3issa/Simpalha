/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author Parsath
 */
public class Answer {
   
    private int id;
    private String suggestion;
    private Question q;

    public Answer() {
    }

    public Answer(Question q1) {
        Question q = new Question();
        q = q1;
    }

    public Question getQ() {
        return q;
    }

    public void setQ(Question q) {
        this.q = q;
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
    

    @Override
    public String toString() {
        return "Answer{" + "id=" + id + ", suggestion=" + suggestion + ", this suggestion corresponds the question :" + q + '}';
    }
    
}
