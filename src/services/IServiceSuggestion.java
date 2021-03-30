/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Suggestion;
import interfaces.IserviceSuggestion;
import java.sql.Connection;
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
public class IServiceSuggestion implements IserviceSuggestion {
    
    Connection cnx;
    
    public IServiceSuggestion() {
        cnx = Maconnexion.getInstance().getConnection();
    }
    
    @Override
    public void Create(Suggestion sugg) {
        try {
            Statement st = cnx.createStatement();
            String query = "insert into suggestion (description, topic, Record) values ('" + sugg.getSuggestion() + "','" + sugg.getSujet() + "','" + sugg.getRecord() + "')";
            System.out.println(query);
            System.out.println(sugg.getSuggestion());
            System.out.println(sugg.getSujet());
            st.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void Update(Suggestion variable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<Suggestion> Read() {
        Statement st;
        List<Suggestion> Suggestion = new ArrayList<>();
        
        try {
            st = cnx.createStatement();
            
            String query = "select * from suggestion";
            ResultSet rst = st.executeQuery(query);
            while (rst.next()) {
                
                Suggestion sugg = new Suggestion();
                sugg.setId_Sugg(rst.getInt(1));
                sugg.setSujet(rst.getString(2));
                sugg.setSuggestion(rst.getString(3));
                sugg.setRecord(rst.getString(4));
                
                Suggestion.add(sugg);
            }
        } catch (SQLException ex) {
        }
        return Suggestion;
    }
    
    @Override
    public void Delete(Suggestion variable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<Suggestion> findAllById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Suggestion findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
