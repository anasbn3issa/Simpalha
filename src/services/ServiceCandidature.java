/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Candidature;
import interfaces.IServiceCandidature;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import utils.Maconnexion;

/**
 *
 * @author win10
 */
public class ServiceCandidature implements IServiceCandidature {

    Connection cnx;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;

    public ServiceCandidature() {
        this.cnx = Maconnexion.getInstance().getConnection();

    }

    @Override
    public void Create(Candidature variable) {
        System.out.println(variable.getFichier());
        try {

            String query = "INSERT INTO candidatures (email,specialite,fichier) VALUES (?,?,?)";
            pst = cnx.prepareStatement(query);
            pst.setString(1, variable.getEmail());
            
           pst.setString(2, variable.getSpécialité());
            pst.setBlob(3, variable.getFichier());
            pst.executeUpdate();
            System.out.println("Candidature ajoutée");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }

    }

   
    public void Modifier (Candidature c,int status) {
        
        
        try {
            String request="UPDATE candidatures SET status = ? WHERE id = ?";
            PreparedStatement pre=cnx.prepareStatement(request);
            pre.setInt(1, status);
            pre.setInt(2, c.getId());
            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        
    }

  public ArrayList<Candidature> getAllCandidatures(){
        ArrayList<Candidature> candidatures=new ArrayList<>();
        try{
            String request="SELECT * FROM candidatures ";
            Statement s=cnx.createStatement();
            ResultSet result=s.executeQuery(request);
            while(result.next()){
                Candidature c=new Candidature();
                c.setIdc(result.getInt(1));
               /* c.setId(result.getInt("c.idu"));*/
                c.setDate(result.getTimestamp(6).toLocalDateTime());
                            
                candidatures.add(c);
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }
        return candidatures;
    }

    @Override
    public void Delete(Candidature variable) {
        
        try {
            ste = cnx.createStatement();

            String sql = "delete from candidatures where idc=?";
            pst = cnx.prepareStatement(sql);
            pst.setInt(1, variable.getIdc());
            pst.executeUpdate();
            

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    

    public Candidature findby(String email){
        Candidature cdr=null;
        try {
            String query="Select * from candidatures where email=?";
            pst=cnx.prepareStatement(query);
            pst.setString(1, email);
           rs= pst.executeQuery();
          while(rs.next()){
              cdr = new Candidature(rs.getString("email"),rs.getString("specialite"),rs.getBlob("fichier"));
          }  
        } catch (SQLException ex) {
            Logger.getLogger(ServiceCandidature.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cdr;
        
    }

    @Override
    public List<Candidature> Read() {
        
List<Candidature> cdr = new ArrayList<>();

        try {

            ste = cnx.createStatement();
            String query = "SELECT * FROM candidatures";
            rs = ste.executeQuery(query);
            while (rs.next()) {
                Candidature cand = new Candidature(rs.getString(2),
                        rs.getString(3), rs.getInt(5),
                        rs.getInt(1),rs.getBlob(4), 
                        rs.getTimestamp(6).toLocalDateTime());
                cdr.add(cand);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return cdr ;
    }
 public void modifierEtatCandidature(Candidature c, int status) {
        try {
            String request="UPDATE candidatures SET status = ? WHERE id = ?";
            PreparedStatement pre=cnx.prepareStatement(request);
            pre.setInt(1, status);
            pre.setInt(2, c.getId());
            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void Update(Candidature variable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Candidature> findAllById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Candidature findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    }

