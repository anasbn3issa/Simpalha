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
public class ServicePost implements IServicePost{

    Connection cnx;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;
    
    public ServicePost(){
        cnx = Maconnexion.getInstance().getConnection();
    }
    
    @Override
    public void Create(Post variable) {
        try {
            Statement st=cnx.createStatement();
            String query="INSERT INTO post(module, problem) VALUES ('"+variable.getModule()+"','"+variable.getProblem()+"')";
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Post> Read() {
     
            List<Post> list = new ArrayList<>();
            String req="select * from post ";
            try {
                ste=cnx.createStatement();
                rs=ste.executeQuery(req);
                while(rs.next()){
                    Post p=new Post(rs.getInt("id"),rs.getString("problem"),rs.getString("module"),rs.getTimestamp("timestamp")); 
                }
                
            }
            catch (SQLException ex) {
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
            int ss=pst.executeUpdate();
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
