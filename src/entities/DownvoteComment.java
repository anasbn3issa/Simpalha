/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author anaso
 */
public class DownvoteComment {
    int id_comment;
    int id_user;

    public DownvoteComment(int id_post, int id_user) {
        this.id_comment = id_post;
        this.id_user = id_user;
    }

    public DownvoteComment() {
    }

    public int getId_comment() {
        return id_comment;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_comment(int id_comment) {
        this.id_comment = id_comment;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    @Override
    public String toString() {
        return "Downvote{" + "id_post=" + id_comment + ", id_user=" + id_user + '}';
    }
    
    
}
