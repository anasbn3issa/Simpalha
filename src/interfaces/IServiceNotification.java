/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.Notification;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.control.Label;

/**
 *
 * @author Parsath
 */
public interface IServiceNotification extends Runnable {
    
    public void createNotification(Notification n);
    
    public ArrayList<Notification> notSent();
    
    public int countNotRead() throws SQLException ;
    
    public void modifyLabelNotification(Label l);
    
}
