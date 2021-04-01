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

            String query = "INSERT INTO candidatures (specialty,Document, idu) VALUES (?,?,?)";
            pst = cnx.prepareStatement(query);
            
           pst.setString(1, variable.getSpécialité());
            pst.setString(2, variable.getFichier());
            pst.setInt(3, variable.getId());
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

  /*public ArrayList<Candidature> getAllCandidatures(){
        ArrayList<Candidature> candidatures=new ArrayList<>();
        try{
            String request="SELECT * FROM candidatures ";
            Statement s=cnx.createStatement();
            ResultSet result=s.executeQuery(request);
            while(result.next()){
                Candidature c=new Candidature();
                c.setIdc(result.getInt(1));
             c.getStatusToString();
              c.getId();
               c.getUserrname();
               c.getSpécialité();
                c.setDate(result.getTimestamp(6).toLocalDateTime());
                            
                candidatures.add(c);
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }
        return candidatures;
    }*/

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

    

    public Candidature findby(int id){
        Candidature cdr=null;
        try {
            String query="Select * from candidatures where idu=?";
            pst=cnx.prepareStatement(query);
            pst.setInt(1, id);
           rs= pst.executeQuery();
          while(rs.next()){
              cdr = new Candidature(rs.getString("specialty"),rs.getString("Document"), rs.getInt("idu"));
          }  
        } catch (SQLException ex) {
            Logger.getLogger(ServiceCandidature.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cdr;
        
    }

    @Override
    public ArrayList<Candidature> Read() {
        
List<Candidature> cdr = new ArrayList<>();

        try {

            ste = cnx.createStatement();
            String query = "SELECT * FROM candidatures";
            rs = ste.executeQuery(query);
            while (rs.next()) {
                Candidature cand = new Candidature(
                        rs.getString(3), rs.getInt(5),
                        rs.getInt(1),rs.getString(4), 
                        rs.getTimestamp(6).toLocalDateTime(),
                        rs.getInt(2)
                );
                cdr.add(cand);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return (ArrayList<Candidature>) cdr ;
    }
 public void modifierEtatCandidature(Candidature c, int status) {
        try {
            String request="UPDATE candidatures SET status = ? WHERE idc = ?";
            PreparedStatement pre=cnx.prepareStatement(request);
            pre.setInt(1, status);
            pre.setInt(2, c.getIdc());
            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
  
    public ArrayList<Candidature> trierCandidature(String student, boolean attente, boolean passee, boolean refusee) {
        ArrayList<Candidature> liste=new ArrayList<>();
        String request="SELECT c.idc, c.idu, c.datec, c.status FROM candidatures c JOIN users u ON u.Id = c.idu WHERE";
        if (attente || passee || refusee) {
            request+=" ( ";
            if (attente)
                request+="status = 1";
            if (passee && attente)
                request+=" OR status = 2";
            else if (passee)
                request+="status = 2";
            if (refusee && (attente || passee))
                request+=" OR status = 3";
            else if (refusee)
                request+="status = 3";
            request+=" ) ";
        } else
            request+="status <> 0";
     
        try {
            PreparedStatement pre=cnx.prepareStatement(request);
            if (!student.isEmpty())
                pre.setString(1, "%"+student+"%");
            ResultSet result=pre.executeQuery();
            while(result.next()){
                Candidature c=new Candidature();
                c.setId(result.getInt("c.idc"));
                c.setId(result.getInt("c.idu"));
                c.setDate(result.getTimestamp(3).toLocalDateTime());
                c.setStatus(result.getInt("c.status"));
             
                liste.add(c);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return liste;
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
    /*public ArrayList<Candidature> trierCommande(String client, boolean attente, boolean confirme, boolean annulee) {
        ArrayList<Candidature> liste=new ArrayList<>();
        String request="SELECT c.idc, c.idu, c.date, c.status, u.username FROM candidatures c JOIN users u ON u.id = c.idu WHERE";
        if (attente || confirme || annulee) {
            request+=" ( ";
            if (attente)
                request+="status = 1";
            if (confirme && attente)
                request+=" OR status = 2";
            else if (confirme)
                request+="status = 2";
            if (annulee && (attente || confirme))
                request+=" OR status = 3";
            else if (annulee)
                request+="status = 3";
            request+=" ) ";
        } else
            request+="status <> 0";
        if (!client.isEmpty())
            request+=" AND u.username LIKE ?";
        try {
            PreparedStatement pre=cnx.prepareStatement(request);
            if (!client.isEmpty())
                pre.setString(1, "%"+client+"%");
            ResultSet result=pre.executeQuery();
            while(result.next()){
                Candidature c=new Candidature();
                c.setId(result.getInt("c.id"));
                c.setId(result.getInt("c.user_id"));
                c.setDate(result.getTimestamp(3).toLocalDateTime());
                c.setStatus(result.getInt("c.status"));
                c.setUserrname(result.getString("u.username"));
                
                liste.add(c);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return liste;
    }*/
    }

