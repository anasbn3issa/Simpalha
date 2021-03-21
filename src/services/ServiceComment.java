/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Comment;
import interfaces.IServiceComment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import utils.Maconnexion;

/**
 *
 * @author anaso
 */
public class ServiceComment implements IServiceComment {

    Connection cnx;

    public ServiceComment() {
        cnx = Maconnexion.getInstance().getConnection();
    }

    @Override
    public void Create(Comment variable) {
        try {
            Statement st = cnx.createStatement();
            String query = "INSERT INTO comment(owner,id_Post,solution) VALUES ('" + variable.getOwner() + "','" + variable.getId_Post() + "','" + variable.getSolution() + "')";
            st.executeUpdate(query);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("enregistré");
            alert.show();
        } catch (SQLException ex) {
            Logger.getLogger(ServicePost.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Comment> Read() {
        Statement st = null;
        try {
            st = cnx.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceComment.class.getName()).log(Level.SEVERE, null, ex);
        }
        String query = "select * from comment ";
        ResultSet rst = null;
        try {
            rst = st.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(ServiceComment.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Comment> comments = new ArrayList<>();

        try {
            while (rst.next()) {
                Comment p = new Comment();
                p.setId(rst.getInt("id"));
                p.setTimestamp(rst.getTimestamp("timestamp"));
                p.setSolution(rst.getString("solution"));
                p.setRating(rst.getInt("rating"));
                p.setId_Post(rst.getInt("id_Post"));
                p.setUpvotes(rst.getInt("upvotes"));
                p.setDownvotes(rst.getInt("downvotes"));
                comments.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceComment.class.getName()).log(Level.SEVERE, null, ex);
        }

        return comments;
    }

    @Override
    public void Delete(Comment variable) {
        try {
            String requete;
            requete = "delete from comment where id = ?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, variable.getId());
            pst.executeUpdate();
            int ss = pst.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void Update(Comment variable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Comment> findAllById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Comment findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
