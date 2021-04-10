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
public class UpvoteComment {
    int id_comment;
    int id_user;

    public UpvoteComment() {
    }

    public UpvoteComment(int id_comment, int id_user) {
        this.id_comment = id_comment;
        this.id_user = id_user;
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
        return "UpvoteComment{" + "id_comment=" + id_comment + ", id_user=" + id_user + '}';
    }

   
}
