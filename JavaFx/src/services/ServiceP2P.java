/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Disponibilite;
import entities.Feedback;
import entities.Meet;
import entities.Users;
import interfaces.IServiceP2P;
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
public class ServiceP2P implements IServiceP2P {

    private Connection cnx;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;
    private ServiceDisponibilite serviceDisp;
    private ServiceUsers serviceUser;
    private ServiceFeedback serviceFeedback;

    public ServiceP2P() {
        cnx = Maconnexion.getInstance().getConnection();
        serviceDisp = new ServiceDisponibilite();
        serviceUser = new ServiceUsers();
        serviceFeedback = new ServiceFeedback();
    }

    @Override
    public void Create(Meet variable) {
        String query = "insert into meet (id, specialite,id_student, id_helper, disponibilite_id ) values(?,?,?,?,?)";
        try {
            pst = cnx.prepareStatement(query);
            pst.setString(1, variable.getId());
            pst.setString(2, variable.getSpecialite());
            pst.setInt(3, variable.getId_student());
            pst.setInt(4, variable.getId_helper());
            pst.setInt(5, Integer.valueOf(variable.getTime()));
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceP2P.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void Update(Meet variable) {
        String query = "update meet set disponibilite_id=?, feedback_id=? where id=?";
        try {
            pst = cnx.prepareStatement(query);
            pst.setInt(1, Integer.valueOf(variable.getTime()));
            pst.setInt(2, variable.getFeedback_id());
            pst.setString(3, variable.getId());

            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ServiceP2P.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Meet> Read() {
        List<Meet> list = new ArrayList<>();
        String req = "select * from meet";
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);

            while (rs.next()) {
                Meet meet = new Meet(rs.getString(1), rs.getInt(3), rs.getInt(4), rs.getInt(6), rs.getString(2), rs.getString(5), rs.getInt(7));

                Disponibilite disponibilite = serviceDisp.findOneByEtat(Integer.valueOf(meet.getTime()), 1);
                meet.setTime(disponibilite.getDatedeb() + "->" + disponibilite.getDateFin());

                Users helper = serviceUser.findById(meet.getId_helper());
                meet.setHelperDisplay(helper.getUsername());

                Users student = serviceUser.findById(meet.getId_student());
                meet.setStudentDisplay(student.getUsername());

                Feedback feedback = serviceFeedback.findById(meet.getFeedback_id());
                System.out.println(feedback);
                meet.setFeedbackDisplay(feedback.getFeedback());

                list.add(meet);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    @Override
    public void Delete(Meet variable) {
        String query = "delete from meet where id=?";
        try {
            pst = cnx.prepareStatement(query);
            pst.setString(1, variable.getId());

            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ServiceP2P.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Meet> findAllById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Meet findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Meet findById(String id) {
        String query = "select * from meet where id=?";
        Meet meet = null;
        try {
            pst = cnx.prepareStatement(query);
            pst.setString(1, id);
            rs = pst.executeQuery();
            while (rs.next()) {
                meet = new Meet(rs.getString(1), rs.getInt(3), rs.getInt(4), rs.getInt(6), rs.getString(2), rs.getString(5), rs.getInt(6));

            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceP2P.class.getName()).log(Level.SEVERE, null, ex);
        }
        return meet;
    }

    @Override
    public List<Meet> ReadById(int id) {
        List<Meet> list = new ArrayList<>();
        String req = "select * from meet where id_student=" + id;
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);

            while (rs.next()) {
                Meet meet = new Meet(rs.getString(1), rs.getInt(3), rs.getInt(4), rs.getInt(6), rs.getString(2), rs.getString(5), rs.getInt(7));

                Disponibilite disponibilite = serviceDisp.findOneByEtat(Integer.valueOf(meet.getTime()), 1);
                meet.setTime(disponibilite.getDatedeb() + "->" + disponibilite.getDateFin());

                Users helper = serviceUser.findById(meet.getId_helper());
                meet.setHelperDisplay(helper.getUsername());

                Users student = serviceUser.findById(meet.getId_student());
                meet.setStudentDisplay(student.getUsername());

                Feedback feedback = serviceFeedback.findById(meet.getFeedback_id());
                System.out.println(feedback);
                meet.setFeedbackDisplay(feedback.getFeedback());

                list.add(meet);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    @Override
    public int count() {
        String query = "select count(id) from meet";
        int count = 0;
        try {
            pst = cnx.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceP2P.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }

    @Override
    public int finishedCount() {
        String query = "select count(id) from meet where feedback_id IS NOT NULL";
        int count = 0;
        try {
            pst = cnx.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceP2P.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }

    @Override
    public int ScheduledCount() {
        String query = "select count(id) from meet where feedback_id IS NULL";
        int count = 0;
        try {
            pst = cnx.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceP2P.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }

    @Override
    public void setFinished(String id) {
        String query = "update meet set etat=1 where id=?";
        try {
            pst = cnx.prepareStatement(query);
            pst.setString(1, id);

            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ServiceP2P.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Meet> ReadStudentsById(int id) {
        List<Meet> list = new ArrayList<>();
        String req = "select * from meet where id_helper=" + id;
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);

            while (rs.next()) {
                Meet meet = new Meet(rs.getString(1), rs.getInt(3), rs.getInt(4), rs.getInt(6), rs.getString(2), rs.getString(5), rs.getInt(7));

                Disponibilite disponibilite = serviceDisp.findOneByEtat(Integer.valueOf(meet.getTime()), 1);
                meet.setTime(disponibilite.getDatedeb() + "->" + disponibilite.getDateFin());

                Users helper = serviceUser.findById(meet.getId_helper());
                meet.setHelperDisplay(helper.getUsername());

                Users student = serviceUser.findById(meet.getId_student());
                meet.setStudentDisplay(student.getUsername());

                Feedback feedback = serviceFeedback.findById(meet.getFeedback_id());
                System.out.println(feedback);
                meet.setFeedbackDisplay(feedback.getFeedback());

                list.add(meet);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

}
