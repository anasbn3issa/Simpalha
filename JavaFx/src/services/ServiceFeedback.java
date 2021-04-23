/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Feedback;
import interfaces.IServiceFeedback;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Maconnexion;

/**
 *
 * @author α Ω
 */
public class ServiceFeedback implements IServiceFeedback {

    private Connection cnx;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;

    public ServiceFeedback() {
        cnx = Maconnexion.getInstance().getConnection();
    }

    @Override
    public void Create(Feedback variable) {
        String query = "insert into feedback (id_meet, description) values(?,?)";
        try {
            pst = cnx.prepareStatement(query);
            pst.setString(1, variable.getMeetId());
            pst.setString(2, variable.getFeedback());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(ServiceP2P.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void Update(Feedback variable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Feedback> Read() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Delete(Feedback variable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Feedback> findAllById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Feedback findById(int id) {
        Feedback f = new Feedback();
        String query = "select * from feedback where id=?";
        try {
            pst = cnx.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            while (rs.next()) {
                f.setId(rs.getInt(1));
                f.setMeetId(rs.getString(2));
                f.setFeedback(rs.getString(3));
                f.setTimestamp(rs.getTimestamp(4).toString());
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDisponibilite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return f;
    }

    @Override
    public Feedback findByMeetId(String id) {
        Feedback f = new Feedback();
        String query = "select * from feedback where id_meet=?";
        try {
            pst = cnx.prepareStatement(query);
            pst.setString(1, id);
            rs = pst.executeQuery();
            while (rs.next()) {
                f.setId(rs.getInt(1));
                f.setMeetId(rs.getString(2));
                f.setFeedback(rs.getString(3));
                f.setTimestamp(rs.getTimestamp(4).toString());
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDisponibilite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return f;
    }

    @Override
    public int count() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
