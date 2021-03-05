/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Parsath
 */
public class DataSource {
    
    private String url="jdbc:mysql://127.0.0.1:3306/spirit";
    private String login="root";
    private String pwd="";
    
    static DataSource instance = null;
    
    private Connection cnx;
    
    private DataSource() {
        try {
            cnx = DriverManager.getConnection(url,login,pwd);
            System.out.println("connexion etablie");
        }
        catch (SQLException ex) {
            Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("connexion ma khedmetch");
        }
    }
    
    public static DataSource getInstance(){
        if(instance == null)
        {
            instance = new DataSource();
        }
        
        return instance;
    }
    
    public Connection getConnection(){
        return cnx;
    }
}
