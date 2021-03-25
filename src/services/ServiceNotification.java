/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Notification;
import entities.Quizz;
import interfaces.IServiceNotification;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import simpalha.notification.FXMLNotificationController;
import utils.Maconnexion;

/**
 *
 * @author Parsath
 */
public class ServiceNotification implements IServiceNotification {
    
    private volatile boolean running = true;
    private int userId;
    Connection cnx;
    
    public boolean isRunning(){
        return running;
    }
    
    public ServiceNotification(){
        cnx = Maconnexion.getInstance().getConnection();
    }
    
    public ServiceNotification(int uId){
        cnx = Maconnexion.getInstance().getConnection();
        userId = uId;
    }

    @Override
    public void updateNotification(Notification n) {
        
        int read = n.isRead() ? 1 : 0;
        int sent = n.isSent() ? 1 : 0;
        
        try{
            Statement stm = cnx.createStatement();
            
//            String query="UPDATE `quizz` SET `title`='"+q.getTitle()+"',`subject`='"+q.getSubject()+"' WHERE `id`='"+q.getId()+"'";
            String query="UPDATE `notification` SET `title`='"+n.getTitle()+"',`content`='"+n.getContent()+"',`user_id`='"+n.getUser()+"',`is_read`='"+read+"',`is_sent`='"+sent+"' WHERE `id`='"+n.getId()+"'";
            stm.executeUpdate(query);
        }
        catch (SQLException ex) { 
            Logger.getLogger(ServiceQuizz.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    public ObservableList<Notification> ObservableListAllNotSentNotifications() throws SQLException {
        
            Statement stm = cnx.createStatement();
            
            String query="SELECT * FROM `notification` WHERE `is_sent`=0";
            ResultSet rst = stm.executeQuery(query);
            
            List<Notification> notifications = new ArrayList<>();
            
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
            
            
            ObservableList<Notification> notificationsObservable = FXCollections.observableArrayList();
            
            notificationsObservable.addAll(notifications);
            
            return notificationsObservable;
    }

    @Override
    public ObservableList<Notification> ObservableListAllNotSentNotificationsAndUpdate() throws SQLException {
        
            Statement stm = cnx.createStatement();
            
            String query="SELECT * FROM `notification` WHERE `is_sent`=0";
            ResultSet rst = stm.executeQuery(query);
            
            List<Notification> notifications = new ArrayList<>();
            
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
                
                n.setSent(true);
                n.setRead(true);
                updateNotification(n);
            }
            
            
            ObservableList<Notification> notificationsObservable = FXCollections.observableArrayList();
            
            notificationsObservable.addAll(notifications);
            
            return notificationsObservable;
    }

    @Override
    public ObservableList<Notification> ObservableListNotifications(int userId) throws SQLException {
        
            
            Statement stm = cnx.createStatement();
            String query="SELECT * FROM `notification` WHERE `user_id`='"+userId+"'";
            ResultSet rst = stm.executeQuery(query);
            
            List<Notification> notifications = new ArrayList<>();
            
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
                
                n.setSent(true);
                n.setRead(true);
                updateNotification(n);
            }
            
            
            ObservableList<Notification> notificationsObservable = FXCollections.observableArrayList();
            
            notificationsObservable.addAll(notifications);
            
            return notificationsObservable;
    }

    @Override
    public ObservableList<Notification> ObservableListNotSentNotificationsAndUpdate(int userId) throws SQLException {
        
            
            Statement stm = cnx.createStatement();
            String query="SELECT * FROM `notification` WHERE `is_sent`=0 AND `user_id`='"+userId+"'";
            ResultSet rst = stm.executeQuery(query);
            
            List<Notification> notifications = new ArrayList<>();
            
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
                
                n.setSent(true);
                n.setRead(true);
                updateNotification(n);
            }
            
            
            ObservableList<Notification> notificationsObservable = FXCollections.observableArrayList();
            
            notificationsObservable.addAll(notifications);
            
            return notificationsObservable;
    }

    @Override
    public int countNotRead() throws SQLException {
        
        Integer notificationCount;
        String query="SELECT COUNT(*) FROM `notification` WHERE `is_read`=0 AND `user_id`='"+userId+"'";
        
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
                    try {
                        if(countNotRead() > 0){
                            FXMLLoader modal = new FXMLLoader(getClass().getResource("/simpalha/notification/FXMLNotification.fxml"));
                            Parent root = null;
                            try{
                                root = modal.load();
                            }
                            catch(IOException io){};

                            FXMLNotificationController editModal = modal.getController();


                            editModal.reloadNotificationsList(userId);

                            Stage stage = new Stage();
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stage.setTitle("Notifications");
                            stage.showAndWait();
                            System.out.println(countNotRead());
                        }
                    } 
                    catch (SQLException ex) {
                        Logger.getLogger(ServiceNotification.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            
            try{
                Thread.sleep(10000);
            }catch(Exception e){}
        }
    }
    
}
