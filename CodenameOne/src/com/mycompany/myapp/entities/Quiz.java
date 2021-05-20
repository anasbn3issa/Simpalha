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
public class Quiz {
    
    private int id,userId;
    private String title,subject;

    public Quiz(int id, int userId, String title, String subject) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.subject = subject;
    }

    public Quiz(int userId, String title, String subject) {
        this.userId = userId;
        this.title = title;
        this.subject = subject;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Quiz{" + "id=" + id + ", userId=" + userId + ", title=" + title + ", subject=" + subject + '}';
    }
    
    
            
}
