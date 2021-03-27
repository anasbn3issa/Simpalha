/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.Comment;
import entities.Post;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author anaso
 */
public interface IServicePost extends IService<Post> {

    public List<Comment> findAllCommentsForThisPost(int postId);
    public List<Post> findPostsByModule(String module);
    public String xTimeAgo(Timestamp timestamp);
    
}
