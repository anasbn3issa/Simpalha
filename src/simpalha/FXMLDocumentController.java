/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.ServiceNotification;
import simpalha.notification.FXMLNotificationController;
import simpalha.quizz.FXMLQuizzController;



/**
 *
 * @author α Ω
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    
    private int userId;
    @FXML
    private FontAwesomeIcon btNotificationShow;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeUserId(1);
        launchServiceNotification();
    }    
    
    public void initializeUserId(int uId){
        userId = uId;
    }
    
    public void launchServiceNotification(){
        ServiceNotification sn = new ServiceNotification(userId);
        
        Thread t = new Thread(sn);
        t.setDaemon(true);
        t.start();
    }

    @FXML
    private void notificationsShow(MouseEvent event) {
        FXMLLoader modal = new FXMLLoader(getClass().getResource("/simpalha/notification/FXMLNotification.fxml"));
        Parent root = null;
        try{
            root = modal.load();
        }
        catch(IOException io){};

        FXMLNotificationController editModal = modal.getController();


        editModal.reloadAllNotificationsList(userId);

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Notifications");
        stage.showAndWait();
    }

//    Opens P2P
    @FXML
    private void showP2P(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/simpalha/P2P/P2PFXML.fxml"
                    )
            );

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
            stage.setScene(
                    new Scene(loader.load())
            );
            stage.show();
        } 
        catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    Opens Quizz
    @FXML
    private void showQuizz(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
//                            "/simpalha/quizz/FXMLQuizzTaking.fxml"
                            "/simpalha/quizz/FXMLQuizz.fxml"
                    )
            );
            
            Parent root = loader.load();
            
            FXMLQuizzController tableController = loader.getController();
            
            tableController.initializeUserId(userId);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
            stage.setScene(
                    new Scene(root)
            );
            stage.setTitle("Quiz Section");
            stage.show();
        } 
        catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    Opens Dashboard
    @FXML
    private void showDashboard(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/simpalha/FXMLDocument.fxml"
                    )
            );

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
            stage.setScene(
                    new Scene(loader.load())
            );
            stage.show();
        } 
        catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
