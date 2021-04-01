/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Reclamation;
import interfaces.IserviceReclamation;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utils.Maconnexion;

/**
 *
 * @author PC
 */
public class IServiceReclamation implements IserviceReclamation 
{
    
    Connection cnx;
    
    public IServiceReclamation() {
        cnx = Maconnexion.getInstance().getConnection();
    }
    
    @Override
    public void Create(Reclamation Rec) {
       
        try {
            int Status =0;
            Statement st = cnx.createStatement();
            Date dateRec = Date.valueOf(Rec.getDateRec());
            Date dateResolution = Date.valueOf(Rec.getDateResolution());
            String query = "insert into reclamation values ('" + Rec.getId() +"','"+ Rec.getIdreported()+"','" + Rec.getIdreportee()+"','" + Rec.getDescription()+"','" + Rec.getFileSelected()+"','"+ Rec.getRecord()+"','" + dateRec +"','"+ dateResolution+"'," + Rec.getValidHelper()+"," + Rec.getValidStudent()+" , "+Status+")";
            System.out.println(query);
            System.out.println(Rec.getDescription());
            System.out.println(Rec.getFileSelected());
            

            st.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
     @Override
    public void Update(Reclamation Rec) {
        PreparedStatement pt = null;
        try {
            pt = cnx.prepareStatement("update reclamation set description = ?, FileSelected = ?, Record = ? where Id = ? ");
            pt.setString(1, Rec.getDescription());
            pt.setString(2, Rec.getFileSelected());
            pt.setString(3, Rec.getRecord());
            pt.setInt(4, Rec.getId());
            pt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    
    @Override
    public void UpdateStatus(Reclamation Rec) {
    PreparedStatement pt = null;
        try {
            pt = cnx.prepareStatement("update reclamation set Status=1 where Id = ? ");
            pt.setInt(1, Rec.getStatus());
            pt.setInt(2, Rec.getId());
            pt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
     
    }

    @Override
    public List<Reclamation> Read() {
        Statement st;
        List<Reclamation> Reclamation = new ArrayList<>();

        try {
            st = cnx.createStatement();

            String query = "select * from reclamation";
            ResultSet rst = st.executeQuery(query);
            while (rst.next()) {

                Reclamation rec = new Reclamation();
                rec.setId(rst.getInt(1));
                rec.setIdreportee(rst.getString(2));
                rec.setIdreported(rst.getString(3));
                rec.setDescription(rst.getString(4));
                rec.setFileSelected(rst.getString(5));
                rec.setRecord(rst.getString(6));
                rec.setDateRec(rst.getDate(7).toLocalDate());
                rec.setDateResolution(rst.getDate(8).toLocalDate());
                rec.setValidStudent(rst.getInt(9));
                rec.setValidHelper(rst.getInt(10));
                rec.setStatus(rst.getInt(11));
                Reclamation.add(rec);
            }
        } catch (SQLException ex) {
        }
        return Reclamation;

    }

    
     @Override
    public void Delete(Reclamation Rec) {
        try {
            PreparedStatement pt = cnx.prepareStatement("delete from reclamation where Id =?");
            pt.setInt(1, Rec.getId());
            pt.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

    }
   

    @Override
    public void Validate(Reclamation Rec) {
        PreparedStatement pt = null;
        try {
            pt = cnx.prepareStatement("update reclamation set ValidStudent=1, ValidHelper=1 where Id = ? ");
            pt.setInt(1, Rec.getId());
            pt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void ValidateStatus(Reclamation Rec, String UserType)
    { PreparedStatement pt = null;
        try { 
            if(UserType=="Student")
                pt = cnx.prepareStatement("update reclamation set ValidStudent=1 where Id = ? ");
            else
                pt = cnx.prepareStatement("update reclamation set ValidHelper=1 where Id = ? ");
            pt.setInt(1, Rec.getId());
            pt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Reclamation> findAllById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Reclamation findById(int id) {
Reclamation rec = new Reclamation();
        try {
            PreparedStatement pt = cnx.prepareStatement("select * from reclamation where Id =?");
            pt.setInt(1,id);
            ResultSet rst = pt.executeQuery();
            
            while (rst.next()) {

                rec.setId(rst.getInt(1));
                rec.setIdreportee(rst.getString(2));
                rec.setIdreported(rst.getString(3));
                rec.setDescription(rst.getString(4));
                rec.setFileSelected(rst.getString(5));
                rec.setRecord(rst.getString(6));
                rec.setDateRec(rst.getDate(7).toLocalDate());
                rec.setDateResolution(rst.getDate(8).toLocalDate());
                rec.setValidStudent(rst.getInt(9));
                rec.setValidHelper(rst.getInt(10));
                rec.setStatus(rst.getInt(11));
              
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return rec;
    }

    @Override
    public int count() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
