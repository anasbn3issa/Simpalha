/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.Comment;
import entities.UpvoteComment;

/**
 *
 * @author anaso
 */
public interface IServiceUpvoteComment extends IService<UpvoteComment> {
    
    /*
    *  this function returns the value of !rs.next();
    * sachant que rs.next() return false if the Rs is empty <=> our table upvote_comment does not contain any column with id_user and id_comment given
    */
    public Boolean upvoteExists(int id_user,int id_comment);
    public void RemoveUpvote(int id_user,int id_comment);
}
