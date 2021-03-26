/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.Comment;

/**
 *
 * @author anaso
 */
public interface IServiceComment extends IService<Comment>  {
    public void MarkAsSolution(int id_post, int id_comment);
    
}
