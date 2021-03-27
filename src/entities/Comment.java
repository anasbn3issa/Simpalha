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
    
     private int id,id_Post,upvotes,downvotes,OwnerId;
    private Timestamp timestamp;
    private String solution;

    public Comment() {
    }

    public Comment(String solution) {
        this.solution = solution;
    }

    public void setId_Post(int id_Post) {
        this.id_Post = id_Post;
    }

    public int getId_Post() {
        return id_Post;
    }

    public int getId() {
        return id;
    }


    
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getSolution() {
        return solution;
    }


    public void setId(int id) {
        this.id = id;
    }


    
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }

    public int getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(int OwnerId) {
        this.OwnerId = OwnerId;
    }

    @Override
    public String toString() {
        return "Comment{" + "id=" + id + ", id_Post=" + id_Post + ", upvotes=" + upvotes + ", downvotes=" + downvotes + ", OwnerId=" + OwnerId + ", timestamp=" + timestamp + ", solution=" + solution + '}';
    }

    
    
    
 
    
    
}
