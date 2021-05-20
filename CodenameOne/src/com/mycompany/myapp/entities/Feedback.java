/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author α Ω
 */
public class Feedback {
    private int id;
    private String meetId, feedback, timestamp;

    public Feedback() {
    }

    public Feedback(int id, String meetId, String feedback, String timestamp) {
        this.id = id;
        this.meetId = meetId;
        this.feedback = feedback;
        this.timestamp = timestamp;
    }
    
    public Feedback( String meetId, String feedback) {
        this.meetId = meetId;
        this.feedback = feedback;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMeetId() {
        return meetId;
    }

    public void setMeetId(String meetId) {
        this.meetId = meetId;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Feedback{" + "id=" + id + ", meetId=" + meetId + ", feedback=" + feedback + ", timestamp=" + timestamp + '}';
    }
    
    
}
