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

    @Override
    public String toString() {
        return "QuizzWrapper{" + "question=" + question + ", status=" + status + '}';
    }
    
}
