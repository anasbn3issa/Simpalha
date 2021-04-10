/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.time.LocalDateTime;

/**
 *
 * @author Parsath
 */
public class QuizzResult {
 
    private int id;
    private int result;
    private int studentId;
    private int quizzId;
    private LocalDateTime date;

    public QuizzResult() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getStudent() {
        return studentId;
    }

    public void setStudent(int studentId) {
        this.studentId = studentId;
    }

    public int getQuizz() {
        return quizzId;
    }

    public void setQuizz(int quizzId) {
        this.quizzId = quizzId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "QuizzResult{" + "id=" + id + ", result=" + result + ", studentId=" + studentId + ", quizzId=" + quizzId + ", date=" + date + '}';
    }
    
    
    
}
