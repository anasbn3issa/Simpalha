/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.ArrayList;

/**
 *
 * @author Parsath
 */
public class Question {
    private int id;
    private int rightAnswer;
    private String question;
    private ArrayList<Answer> answers;
    
    public Question(){
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
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

    @Override
    public String toString() {
        return "Question{" + "id=" + id + ", rightAnswer=" + rightAnswer + ", question=" + question + ", answers=" + answers + '}';
    }
    
    
}
