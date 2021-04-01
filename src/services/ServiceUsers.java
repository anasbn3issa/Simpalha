/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.CurrentUser;
import entities.Users;
import interfaces.IserviceUsers;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import utils.Maconnexion;
import utils.UserSession;

/**
 *
 * @author win10
 */
public class ServiceUsers implements IserviceUsers {

    Connection cnx;
    private ResultSet rs;
    private Statement st;
    PreparedStatement pstmt = null;

    public ServiceUsers() {
        cnx = Maconnexion.getInstance().getConnection();

    }

    @Override
    public void Create(Users variable) {
        try {
            st = cnx.createStatement();
            String query = "INSERT INTO `users` (`Email`, `Password`, `Username`,`about`) VALUES ('"
                    + variable.getEmail()
                    + "','" + variable.getPassword()
                    + "','" + variable.getUsername()
                    + "','" + variable.getAbout()
                    + "')";
            st.execute(query);
            System.out.println("Ajout abouti");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
    }

    @Override
    public void Update(Users variable) {
        String query = "update users set Specialty=? Username=?, Email=?"
                + " where id=?";
        try {
            pstmt = cnx.prepareStatement(query);
            pstmt.setString(1, variable.getUsername());
             pstmt.setString(2, variable.getSpecialty());
            pstmt.setString(3, variable.getEmail());
            pstmt.setInt(4, variable.getId());

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ServiceUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Users> Read() {
        List<Users> Utilisateurs = new ArrayList<>();

        try {

            st = cnx.createStatement();
            String query = "SELECT * FROM users";
            rs = st.executeQuery(query);
            while (rs.next()) {
                Users user = new Users(rs.getInt(1), rs.getString(3), rs.getString(2), rs.getString(4), rs.getString(5), rs.getString(7), rs.getInt(8));
                Utilisateurs.add(user);
            }

        } catch (SQLException ex) {
        }

        return Utilisateurs;
    }

    @Override
    public void Delete(Users variable) {

        try {
            st = cnx.createStatement();

            String sql = "delete from users where id=?";
            pstmt = cnx.prepareStatement(sql);
            pstmt.setInt(1, variable.getId());
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public Boolean usernameExist(String username) {

        try {
            String request = "SELECT username FROM users where username='" + username + "'";
            Statement s = cnx.createStatement();
            ResultSet result = s.executeQuery(request);
            while (result.next()) {
                return true;
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return false;
    }

    @Override
    public Boolean emailExist(String email) {

        try {
            String request = "SELECT email FROM users where email='" + email + "'";
            Statement s = cnx.createStatement();
            ResultSet result = s.executeQuery(request);
            while (result.next()) {
                return true;
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return false;
    }

    public String getAlphaNumericString(int n) {

        // chose a Character random from this String 
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString 
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between 
            // 0 to AlphaNumericString variable length 
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb 
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    public void updateAbout(String about) {
        UserSession cu = UserSession.getInstace(0);
        String requete = "UPDATE users SET about='" + about + "' WHERE id=" + cu.userid;
        try {
            st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("Description modifié");
        } catch (SQLException ex) {
            Logger.getLogger(Maconnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int geIdbyUsername(String username) throws SQLException {

        /* CurrentUser cu = CurrentUser.CurrentUser(); */
        int id = 0;
        try {
            String request = "SELECT id FROM users where username='" + username + "'";
            Statement s = cnx.createStatement();
            ResultSet result = s.executeQuery(request);
            while (result.next()) {
                id = result.getInt("id");
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return id;
    }

    public String getEmailbyUsername(String username) throws SQLException {

        CurrentUser cu = CurrentUser.CurrentUser();
        String str = "";
        try {
            String request = "SELECT email FROM users where username='" + username + "'";
            Statement s = cnx.createStatement();
            ResultSet result = s.executeQuery(request);
            while (result.next()) {
                str = result.getString("email");
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return str;
    }

    public void updateCode(String code, int id) {
        /*CurrentUser cu = CurrentUser.CurrentUser();*/
        String requete = "UPDATE users SET code='" + code + "' WHERE id=" + id;
        try {
            st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("code");
        } catch (SQLException ex) {
            Logger.getLogger(Maconnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updatePassword(Users user) {
        String requete = "UPDATE users SET password='" + user.getPassword() + "',code=NULL WHERE id=" + user.getId();
        try {
            st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("mot de passe modifié");
        } catch (SQLException ex) {
            Logger.getLogger(Maconnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean check(Users user) throws SQLException {
        Boolean res = false;
        try {
            String request = "SELECT * FROM users WHERE Username=? AND Password=?";
            pstmt = cnx.prepareStatement(request);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                res = true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return res;
    }

    @Override
    public List<Users> findAllById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Users findById(int id) {
        String query = "select * from users where id=?";
        Users student = null;
        try {
            pstmt = cnx.prepareStatement(query);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                student = new Users(rs.getInt(1), rs.getString(3), rs.getString(2), rs.getString(4), rs.getString(6), rs.getString(7), rs.getInt(8));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
        return student;
    }

}
