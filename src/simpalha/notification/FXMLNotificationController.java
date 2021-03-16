/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.notification;

import entities.Notification;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import services.ServiceNotification;
import services.ServiceWrapper;
import simpalha.quizz.FXMLQuestionTableController;

/**
 * FXML Controller class
 *
 * @author Parsath
 */
public class FXMLNotificationController implements Initializable {

    @FXML
    private TableView<Notification> LAffiche;
    @FXML
    private TableColumn<Notification, Integer> idColumn;
    @FXML
    private TableColumn<Notification, String> titleColumn;
    @FXML
    private TableColumn<Notification, String> contentColumn;
    @FXML
    private TableColumn<Notification, Boolean> readColumn;
    @FXML
    private TableColumn<Notification, Boolean> sentColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
//    Reusable function to reload the table
    public void reloadNotificationsList(int userId){
        ServiceNotification sn = new ServiceNotification();
        
        try {
            LAffiche.setItems(sn.ObservableListNotSentNotificationsAndUpdate(userId));
        } catch (SQLException ex) {
            Logger.getLogger(FXMLNotificationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
