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

    public void setTranslation() {
        if (this.status == true){
            this.statusTranslation = "Done";
        }
        else{
            this.statusTranslation = "Missing";
        }
    }
    
    
    
}
