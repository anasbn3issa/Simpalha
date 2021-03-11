/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

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
import entities.Ressources;
import interfaces.IServiceRessources;

/**
 *
 * @author cyrin
 */
public class ServiceRessources implements IServiceRessources{
     Connection cnx;
    private PreparedStatement pst;
     private static ServiceRessources instance;

    public ServiceRessources() {
        cnx = Maconnexion.getInstance().getConnection();
    }

    @Override
    public void Create(Ressources R) {
        try {
            Statement stm = cnx.createStatement();
            String query = "INSERT INTO `ressources`(`path`, `title`, `description`) VALUES ('" + R.getPath() + "','" + R.getTitle() + "','" + R.getDescription() + "')";
            stm.executeUpdate(query);

        } catch (SQLException ex) {
            Logger.getLogger(ServiceRessources.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public List<Ressources> Read() throws SQLException {

        Statement stm = cnx.createStatement();
        String query = " SELECT * FROM `ressources`";

        //pour ne pas perdre le resultat de select *
        ResultSet resultat = stm.executeQuery(query);

        //stocker les ressources trouves dans une liste ressources
        List<Ressources> ressources = new ArrayList<>();

        //remplir la liste a partir du resultat de la requete
        while (resultat.next()) {
            Ressources R1 = new Ressources();
            R1.setIdR(resultat.getInt("idR"));
            R1.setDescription(resultat.getString("description"));
            R1.setPath(resultat.getString("path"));
            R1.setTitle(resultat.getString("title"));
            ressources.add(R1);
        }

        return ressources;
    }

    @Override
    public void Update(Ressources R) {
        try {
            //Statement stm = cnx.createStatement();
           
            String query = "UPDATE `ressources` SET `path`= ? ,`title`= ? ,`description`= ? WHERE idR=?";
            pst = cnx.prepareStatement(query);
            pst.setString(1,R.getPath());
            pst.setString(2,R.getTitle());
            pst.setString(3,R.getDescription());
            pst.setInt(4,R.getIdR());

            pst.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());        }
    }

    public Ressources Search(int id) {

        // Statement stm=cnx.createStatement();
        String query = " SELECT path, title, description FROM ressources WHERE idR = ?";
            Ressources R1 = new Ressources();
        try {
            //            if (query.isEmpty())
//                { exit(); }
//            else {
            pst = cnx.prepareStatement(query);
            pst.setInt(1, id);
            
            //pour ne pas perdre le resultat de select *
            
            ResultSet resultat = pst.executeQuery();
            while (resultat.next()) {

            //stocker les resultat dans une variable de type ressources
        
//                R1.setIdR(resultat.getInt("idR"));
            R1.setPath(resultat.getString("path"));
            R1.setTitle(resultat.getString("title"));
            R1.setDescription(resultat.getString("description"));}

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());;
            
        }
        return R1;
    }

    @Override
    public void Delete(Ressources R) {

        try {
            Statement stm = cnx.createStatement();
            String query = "DELETE FROM `ressources` WHERE idR='" + R.getIdR() + "'";
            stm.executeUpdate(query);

        } catch (SQLException ex) {
            Logger.getLogger(ServiceRessources.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
       
    public List<Ressources> Tri_title() throws SQLException {

        Statement stm = cnx.createStatement();
        String query = " SELECT * FROM `ressources` ORDER BY title";

        //pour ne pas perdre le resultat de select *
        ResultSet resultat = stm.executeQuery(query);

        //stocker les ressources trouves dans une liste ressources
        List<Ressources> ressources = new ArrayList<>();

        //remplir la liste a partir du resultat de la requete
        while (resultat.next()) {
            Ressources R1 = new Ressources();
            R1.setIdR(resultat.getInt("idR"));
            R1.setDescription(resultat.getString("description"));
            R1.setPath(resultat.getString("path"));
            R1.setTitle(resultat.getString("title"));
            ressources.add(R1);
        }

        return ressources;
    }
    
      
    public List<Ressources> Tri_id() throws SQLException {

        Statement stm = cnx.createStatement();
        String query = " SELECT * FROM `ressources` ORDER BY id";

        //pour ne pas perdre le resultat de select *
        ResultSet resultat = stm.executeQuery(query);

        //stocker les ressources trouves dans une liste ressources
        List<Ressources> ressources = new ArrayList<>();

        //remplir la liste a partir du resultat de la requete
        while (resultat.next()) {
            Ressources R1 = new Ressources();
            R1.setIdR(resultat.getInt("idR"));
            R1.setDescription(resultat.getString("description"));
            R1.setPath(resultat.getString("path"));
            R1.setTitle(resultat.getString("title"));
            ressources.add(R1);
        }

        return ressources;
    }

     public static ServiceRessources getInstance(){
        if(instance==null) 
            instance=new ServiceRessources();
        return instance;
    }
}
