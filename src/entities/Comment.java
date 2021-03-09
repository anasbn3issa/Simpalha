/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Timestamp;
/**
 *
 * @author anaso
 */
public class Comment {
    
     private int id;
    private String owner="Ali Ben la9ab";
    private Timestamp timestamp;
    private String solution="no solution yet lol";
    private int rating;

    public Comment() {
    }

    public Comment(String solution) {
        this.solution = solution;
    }

    public int getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getSolution() {
        return solution;
    }

    public int getRating() {
        return rating;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Comment{" + "id=" + id + ", timestamp=" + timestamp + ", solution=" + solution + ", rating=" + rating + '}';
    }
    
    
}
