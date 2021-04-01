/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Comment;
import entities.DownvoteComment;
import entities.UpvoteComment;
import interfaces.IService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import utils.Maconnexion;
import interfaces.IServiceUpvoteComment;
import java.sql.ResultSet;

/**
 *
 * @author anaso
 */
public class ServiceUpvoteComment implements IServiceUpvoteComment {

    Connection cnx;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;

    public ServiceUpvoteComment() {
        cnx = Maconnexion.getInstance().getConnection();
    }

    @Override
    public void Create(UpvoteComment variable) {
        try {
            Statement st = cnx.createStatement();
            String query = "INSERT INTO upvote_comment(id_comment,id_user) VALUES ('" + variable.getId_comment() + "','" + variable.getId_user() + "')";
            st.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(ServicePost.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void Update(UpvoteComment variable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UpvoteComment> Read() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Delete(UpvoteComment variable) {
        try {
            String requete;
            requete = "delete from upvote_comment where (id_user = ? AND id_comment=?)";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, variable.getId_user());
            pst.setInt(2, variable.getId_comment());
            pst.executeUpdate();
            int ss = pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<UpvoteComment> findAllById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UpvoteComment findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean upvoteExists(int id_user, int id_comment) {
        String query = "select * from upvote_comment where (id_user=? AND id_comment=?)";

        try {
            pst = cnx.prepareStatement(query);
            pst.setInt(1, id_user);
            pst.setInt(2, id_comment);
            rs = pst.executeQuery();
            return rs.next();

        } catch (SQLException ex) {
            Logger.getLogger(ServiceUpvoteComment.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public int count() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void RemoveUpvote(int id_user, int id_comment) {
        // remove from upvotecomment table 
         try {
            String requete;
            requete = "delete from upvote_comment where (id_user = ? AND id_comment=?)";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, id_user);
            pst.setInt(2, id_comment);
            pst.executeUpdate();
            int ss = pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
         
    }
}
