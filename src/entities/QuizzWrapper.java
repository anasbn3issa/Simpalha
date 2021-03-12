/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 * This Class is meant to wrap the Question class and its' status in a certain Quiz, if the Questions'
 * status is true then the Question have been answered else it still hasn't been.
 * @author Parsath
 */
public class QuizzWrapper {
    private Question question;
    private boolean status;
    private String statusTranslation;

    public QuizzWrapper() {
        question = new Question();
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTranslation() {
        return statusTranslation;
    }

//    The translation of the status into a Human readable String.
    public void setTranslation() {
        if (this.status == true){
            this.statusTranslation = "Done";
        }
        else{
            this.statusTranslation = "Missing";
        }
    }
    
    
    
}
