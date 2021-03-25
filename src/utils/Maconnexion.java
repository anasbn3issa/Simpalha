package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author α Ω
 */
public class Maconnexion {
    
    private String URL="jdbc:mysql://127.0.0.1/simpalha";
    private String LOGIN="root";
    private String PASS="";
    static Maconnexion instance = null;
    private Connection cnx;

    private Maconnexion() {
        try { 
            cnx = DriverManager.getConnection(URL, LOGIN, PASS);
            System.out.println("cnx done");
        } catch (SQLException ex) {
            System.out.println("cnx error");
            System.out.println(ex.getMessage());

        }
    }
    
    public static Maconnexion getInstance(){
        if(instance ==null)
            instance = new Maconnexion();
        return instance;
    }
    
    public Connection getConnection(){
        return cnx;
    }
    
    
    
}
