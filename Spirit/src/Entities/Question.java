/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author Parsath
 */
public class Question {
    private int id;
    private int rightAnswer;
    private String question;
    
    public Question(){
    }

    public int getId() {
        return id;
    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRightAnswer(int rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "Question{" + "id=" + id + ", rightAnswer=" + rightAnswer + ", question=" + question + '}';
    }
    
    
}
