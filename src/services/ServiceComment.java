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
    private PreparedStatement pst;
    private Statement ste;
    private ResultSet rs;

    public ServiceComment() {
        cnx = Maconnexion.getInstance().getConnection();
    }

    @Override
    public void Create(Comment variable) {
        try {
            Statement st = cnx.createStatement();
            String query = "INSERT INTO comment(owner_id,id_Post,solution) VALUES ('" + variable.getOwnerId() + "','" + variable.getId_Post() + "','" + variable.getSolution() + "')";
            st.executeUpdate(query);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("saved");
            alert.show();
        } catch (SQLException ex) {
            Logger.getLogger(ServicePost.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Comment> Read() {
        List<Comment> list = new ArrayList<>();
        String req = "select * from comment";
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                Comment p = new Comment();
                p.setId(rs.getInt("id"));
                p.setOwnerId(rs.getInt("owner_id"));
                p.setUpvotes(rs.getInt("upvotes"));
                p.setSolution(rs.getString("solution"));
                p.setDownvotes(rs.getInt("downvotes"));
                p.setTimestamp(rs.getTimestamp("timestamp"));
                list.add(p);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
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

    @Override
    public void MarkAsSolution(int id_post, int id_comment) {
        
        String query = "update post set solution_id=?, status=? where id=?";
        
        try {
            
            PreparedStatement pst= cnx.prepareStatement(query);
            pst.setInt(1,id_comment);
            pst.setString(2,"SOLVED");
            pst.setInt(3,id_post);
            pst.executeUpdate();
            
           

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        
        
        
    }

    @Override
    public void updateUpvotes(Comment variable) {
        String query = "update comment set upvotes=? where id=?";
        System.out.println(variable.toString());
        try {
            pst = cnx.prepareStatement(query);
            pst.setInt(1, variable.getUpvotes());
            pst.setInt(2, variable.getId());
            pst.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void updateDownvotes(Comment variable) {
        String query = "update comment set downvotes=? where id=?";
        System.out.println(variable.toString());
        try {
            pst = cnx.prepareStatement(query);
            pst.setInt(1, variable.getDownvotes());
            pst.setInt(2, variable.getId());
            pst.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
