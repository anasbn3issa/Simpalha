/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.DownvoteComment;
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
import interfaces.IServiceDownvoteComment;
import java.sql.ResultSet;

/**
 *
 * @author anaso
 */
public class ServiceDownvoteComment implements IServiceDownvoteComment{

    
    Connection cnx;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;
    public ServiceDownvoteComment() {
         cnx = Maconnexion.getInstance().getConnection();
    }

    @Override
    public void Create(DownvoteComment variable) {
         try {
            Statement st=cnx.createStatement();
            String query="INSERT INTO downvote_comment(id_comment,id_user) VALUES ('"+variable.getId_comment()+"','"+variable.getId_user()+"')";
            st.executeUpdate(query);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("enregistré");
            alert.show();
        } catch (SQLException ex) {
            Logger.getLogger(ServicePost.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    @Override
    public void Update(DownvoteComment variable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DownvoteComment> Read() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Delete(DownvoteComment variable) {
         try {
            String requete;
            requete = "delete from downvote_comment where (id_user = ? AND id_post=?)";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, variable.getId_user());
            pst.setInt(2, variable.getId_comment());
            pst.executeUpdate();
            int ss=pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<DownvoteComment> findAllById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DownvoteComment findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    /*
    *  this function returns the value of !rs.next();
    * sachant que rs.next() return false if the Rs is empty <=> our table downvote_comment does not contain any column with id_user and id_comment given
    */
    @Override
    public Boolean downvoteExists(int id_user, int id_comment) {
        String query = "select * from downvote_comment where (id_user=? AND id_comment)";
        
        try {
            pst = cnx.prepareStatement(query);
            pst.setInt(1, id_user);
            pst.setInt(2, id_comment);
            rs = pst.executeQuery();
            return !rs.next();
            
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDownvoteComment.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return null;
    }

    @Override
    public int count() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
