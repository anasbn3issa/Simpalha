/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.Notification;
import entities.Quizz;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

/**
 *
 * @author Parsath
 */
public interface IServiceNotification extends Runnable {
    
    public void createNotification(Notification n);
    
    public void updateNotification(Notification n);
    
    public ArrayList<Notification> notSent();
    
    public int countNotRead() throws SQLException ;
    
//    show all quizzes in an observablelist
    public ObservableList<Notification> ObservableListAllNotSentNotifications() throws SQLException ;
    
//    show all quizzes in an observablelist
    public ObservableList<Notification> ObservableListAllNotSentNotificationsAndUpdate() throws SQLException ;
    
//    show all quizzes in an observablelist
    public ObservableList<Notification> ObservableListNotifications(int userId) throws SQLException ;
    
//    show all quizzes in an observablelist
    public ObservableList<Notification> ObservableListNotSentNotificationsAndUpdate(int userId) throws SQLException ;
    
}
