/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Notification;
import interfaces.IServiceNotification;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import simpalha.FXMLDocumentController;
import utils.DataSource;

/**
 *
 * @author Parsath
 */
public class ServiceNotification implements IServiceNotification {
    
    public static IntegerProperty countNotRead;
    private volatile boolean running = true;
    private int count;
    Connection cnx;
    
    public boolean isRunning(){
        return running;
    }
    
    public ServiceNotification(){
        cnx = DataSource.getInstance().getConnection();
    }


    @Override
    public void createNotification(Notification n) {
        int read = n.isRead() ? 1 : 0;
        int sent = n.isSent() ? 1 : 0;
        
        try{
            Statement stm = cnx.createStatement();
            
            String query="INSERT INTO `notification`(`title`, `content`, `user_id`, `is_read`, `is_sent`) VALUES ('"+n.getTitle()+"','"+n.getContent()+"','"+n.getUser()+"','"+read+"','"+sent+"')";
            stm.executeUpdate(query);
        }
        catch (SQLException ex) { 
            Logger.getLogger(ServiceNotification.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ArrayList<Notification> notSent() {
        
        ArrayList<Notification> notifications = new ArrayList<>();
        String query="SELECT * FROM `notification` WHERE `is_sent`='"+false+"'";

        try{
            Statement stm = cnx.createStatement();
            ResultSet rst = stm.executeQuery(query);


            while(rst.next())
            {
                Notification n = new Notification();
                
                n.setId(rst.getInt("id"));
                n.setTitle(rst.getString("title"));
                n.setContent(rst.getString("content"));
                n.setRead(rst.getBoolean("is_read"));
                n.setSent(rst.getBoolean("is_sent"));
                n.setUser(rst.getInt("user_id"));

                notifications.add(n);
            }
        }
        catch(SQLException ex){
            Logger.getLogger(ServiceNotification.class.getName()).log(Level.SEVERE, null, ex);
        }

        return notifications;
    }

    @Override
    public int countNotRead() throws SQLException {
        
        Integer notificationCount;
        String query="SELECT COUNT(*) FROM `notification` WHERE `is_read`=0";
        
        Statement stm = cnx.createStatement();
        ResultSet rst = stm.executeQuery(query);
        rst.next();
        notificationCount = rst.getInt(1);

        return notificationCount;
    }
    
    public void terminate(){
        running = false;
    }

    @Override
    public void run() {
        while(running){
            Platform.runLater(new Runnable(){
                @Override
                public void run(){
                    countNotRead.setValue(3);
                }
            });
            try{
                count = countNotRead();
            }catch(SQLException ex){};
            System.out.println(notSent());
            try{
                Thread.sleep(10000);
            }catch(Exception e){}
        }
    }

    @Override
    public void modifyLabelNotification(Label l) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
