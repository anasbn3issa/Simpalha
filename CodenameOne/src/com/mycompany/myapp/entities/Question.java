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
public class Question {
    
    private int id, rightAnswer, quizId;
    private String question;

    public Question(int id, int rightAnswer, int quizId, String question) {
        this.id = id;
        this.rightAnswer = rightAnswer;
        this.quizId = quizId;
        this.question = question;
    }

    public Question(int quizId, int rightAnswer, String question) {
        this.rightAnswer = rightAnswer;
        this.quizId = quizId;
        this.question = question;
    }

    public Question(int quizId, String question) {
        this.quizId = quizId;
        this.question = question;
    }
    
    public Question() {
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(int rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "Question{" + "id=" + id + ", rightAnswer=" + rightAnswer + ", quizId=" + quizId + ", question=" + question + '}';
    }
    
    
    
}
