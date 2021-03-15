/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import services.ServiceNotification;



/**
 *
 * @author α Ω
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private Label lNotificationNotRead;
    
    private Service<Void> backgroundThread;
    private ServiceNotification sn;
    private IntegerProperty counter;
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }

    public IntegerProperty getCounter()
    {
        return sn.countNotRead;
    }

    public void setCounter(int value)
    {
//        counter.set(value);
    }
    
    public IntegerProperty counterProperty() {
        return sn.countNotRead;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sn = new ServiceNotification();
        System.out.println(sn.countNotRead);
//        NumberFormat nf;
//        lNotificationNotRead.textProperty().bindBidirectional(String.valueOf(sn.countNotRead));




//        backgroundThread = new Service<Void>(){
//            @Override
//            protected Task<Void> createTask(){
//                return new Task<Void>(){
//                    @Override
//                    protected Void call() throws Exception {
//                        ServiceNotification sn = new ServiceNotification();
//                        while(sn.isRunning()){
//                            System.out.println(sn.countNotRead());
//                            lNotificationNotRead.setText(String.valueOf(sn.countNotRead()));
//                            Thread.sleep(5000);
//                        }
//                        return null;
//                    }
//                };
//            }
//        };
//        
//        lNotificationNotRead.textProperty().bind(backgroundThread.messageProperty());
////        lNotificationNotRead.setText("maz");
//        backgroundThread.start();
    }    
    
    public void notificationsUpdate() throws SQLException{
        int count = sn.countNotRead();
        lNotificationNotRead.setText(String.valueOf(count));
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
                            "/simpalha/quizz/FXMLQuizzTaking.fxml"
//                            "/simpalha/quizz/FXMLQuizzTable.fxml"
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
