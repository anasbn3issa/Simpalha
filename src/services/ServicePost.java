/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Post;
import interfaces.IServicePost;
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
public class ServicePost implements IServicePost {
    
    Connection cnx;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;
    
    public ServicePost() {
        cnx = Maconnexion.getInstance().getConnection();
    }
    
    @Override
    public void Create(Post variable) {
        try {
            Statement st = cnx.createStatement();
            String query = "INSERT INTO post(module, problem) VALUES ('" + variable.getModule() + "','" + variable.getProblem() + "')";
            st.executeUpdate(query);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("enregistré");
            alert.show();
        } catch (SQLException ex) {
            Logger.getLogger(ServicePost.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void Update(Post variable) {
        String query = "update post set module=?,status=?,problem=? where id=?";
        System.out.println(variable.toString());
        try {
            pst = cnx.prepareStatement(query);
            pst.setString(1, variable.getModule());
            pst.setString(2, variable.getStatus());
            pst.setString(3, variable.getProblem());
            pst.setInt(4, variable.getId());
            pst.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }    
    }
    
    @Override
    public List<Post> Read() {
        
        List<Post> list = new ArrayList<>();
        String req = "select * from post";
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                Post p = new Post();
                //p.setId(rs.getInt("id")); // removed because not needed in Accueil 
                p.setProblem(rs.getString("problem"));
                p.setModule(rs.getString("module"));
                p.setTimestamp(rs.getTimestamp("timestamp"));
                list.add(p);
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;        
        
    }

    @Override
    public void Delete(Post variable) {
        try {
            String requete;
            requete = "delete from post where id = ?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, variable.getId());
            pst.executeUpdate();
            int ss = pst.executeUpdate();
            System.out.println(ss);
            System.out.println("Post Deleted !!!!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public List<Post> findAllById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Post findById(int id) {
        String query = "select * from post where id=?";
        Post p = new Post();
        try {
            pst = cnx.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            while (rs.next()) {
                p.setId(rs.getInt("id"));
                p.setTimestamp(rs.getTimestamp("timestamp"));
                p.setStatus(rs.getString("status"));
                p.setProblem(rs.getString("problem"));
                p.setModule(rs.getString("module"));                
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServicePost.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
        
    }
    
}
