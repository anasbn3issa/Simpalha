/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Disponibilite;
import interfaces.IServiceDisponibilite;
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
 * @author α Ω
 */
public class ServiceDisponibilite implements IServiceDisponibilite {

    private Connection cnx;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;

    public ServiceDisponibilite() {
        cnx = Maconnexion.getInstance().getConnection();
    }

    @Override
    public void Create(Disponibilite variable) {
        
         try {
            Statement st = cnx.createStatement();
            String query = "INSERT INTO disponibilite (helperId,dateDeb,dateFin) VALUES ('" + variable.getHelperid()+ "','" + variable.getDatedeb()+ "','" + variable.getDateFin()+  "')";
            st.executeUpdate(query);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("enregistré");
            alert.show();
        } catch (SQLException ex) {
            Logger.getLogger(ServicePost.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    @Override
    public void Update(Disponibilite variable) {
        String query = "update disponibilite set etat=? where id=?";
        try {
            pst = cnx.prepareStatement(query);
            pst.setInt(1, variable.getEtat());
            pst.setInt(2, variable.getId());
            
            pst.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDisponibilite.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public List<Disponibilite> Read() {
        List<Disponibilite> list = new ArrayList<>();
        String req = "select * from disponibilite";
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);

            while (rs.next()) {
                Disponibilite disp = new Disponibilite(rs.getInt(1), rs.getInt(5), rs.getInt(2), rs.getString(3), rs.getString(4));
                list.add(disp);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }

    @Override
    public void Delete(Disponibilite variable) {
    }

    @Override
    public List<Disponibilite> findAllById(int id) {
        List<Disponibilite> list = new ArrayList<>();
        String query = "select dateDeb,dateFin from disponibilite where etat=0 AND helperId=?";
        try {
            pst = cnx.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {
                Disponibilite disponibilite = new Disponibilite();
                disponibilite.setDatedeb(rs.getString(1));
                disponibilite.setDateFin(rs.getString(2));
                list.add(disponibilite);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDisponibilite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Disponibilite findById(int id) {
        String query = "select * from disponibilite where id=?";
        Disponibilite disponibilite = null;
        try {
            pst = cnx.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            while (rs.next()) {
                disponibilite = new Disponibilite();
                disponibilite.setId(rs.getInt(1));
                disponibilite.setHelperid(rs.getInt(2));
                disponibilite.setDatedeb(rs.getString(3));
                disponibilite.setDateFin(rs.getString(4));
                disponibilite.setEtat(rs.getInt(5));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDisponibilite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return disponibilite;
    }

    @Override
    public Disponibilite findByTime(String time) {
        String delim = "->";
        String deb = time.split(delim)[0].trim();
        String fin = time.split(delim)[1].trim();

        Disponibilite disponibilite = new Disponibilite();
        String query = "select id from disponibilite where dateDeb=? AND dateFin=?";
        try {
            pst = cnx.prepareStatement(query);
            pst.setString(1, deb);
            pst.setString(2, fin);
            rs = pst.executeQuery();
            while (rs.next()) {
                disponibilite.setId(rs.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDisponibilite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return disponibilite;
    }

    @Override
    public Disponibilite findOneByEtat(int id, int etat) {
        String query = "select * from disponibilite where id=? AND etat=?";
        Disponibilite disponibilite = null;
        try {
            pst = cnx.prepareStatement(query);
            pst.setInt(1, id);
            pst.setInt(2, etat);
            rs = pst.executeQuery();
            while (rs.next()) {
                disponibilite = new Disponibilite();
                disponibilite.setId(rs.getInt(1));
                disponibilite.setHelperid(rs.getInt(2));
                disponibilite.setDatedeb(rs.getString(3));
                disponibilite.setDateFin(rs.getString(4));
                disponibilite.setEtat(rs.getInt(5));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDisponibilite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return disponibilite;
    }

}
