/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Users;
import interfaces.IServiceUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Maconnexion;

/**
 *
 * @author α Ω
 */
public class ServiceUser implements IServiceUser{
    
    private Connection cnx;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;

    public ServiceUser() {
        cnx = Maconnexion.getInstance().getConnection();
    }

    @Override
    public void Create(Users variable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Update(Users variable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Users> Read() {
    List<Users> list = new ArrayList<>();
    String req = "select * from users where specialites IS NOT NULL ";
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            
            while(rs.next()){
                Users student = new Users(rs.getInt(1), rs.getString(2), rs.getString(4), rs.getString(5),rs.getString(6), rs.getTimestamp(7));
                list.add(student);
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
    return list;
    }

    @Override
    public void Delete(Users variable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Users> findAllById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Users findById(int id) {
        String query = "select fname,lname, specialites from users where id=?";
        Users student = new Users();
        try {
            pst = cnx.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            while(rs.next()){
                student.setLname(rs.getString(1));
                student.setFname(rs.getString(2));
                student.setSpecialites(rs.getString(3));
            }
            } catch (SQLException ex) {
            Logger.getLogger(ServiceDisponibilite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return student;    }

    
    
}
