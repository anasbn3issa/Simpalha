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
public class Question { 
    private int id;
    private int rightAnswer;
    private String question;
    private int quizzId;
    
    public Question(){
    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(int rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public int getId() {
        return id;
    }

    public int getAnswer() {
        return rightAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAnswer(int rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getQuizz() {
        return quizzId;
    }

    public void setQuizz(int quizzId) {
        this.quizzId = quizzId;
    }

    @Override
    public String toString() {
        return  question;
    }
    
}
