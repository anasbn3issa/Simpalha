/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.quizz;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import simpalha.FXMLDocumentController;

/**
 * FXML Controller class
 *
 * @author Parsath
 */
public class FXMLQuizzController implements Initializable {
    
    private int userId;
    @FXML
    private Button btShowQuizzCrud;
    @FXML
    private Button btTakeQuizz;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void initializeUserId(int uId){
        userId = uId;
    }

    @FXML
    private void showQuizzCrud(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/simpalha/quizz/FXMLQuizzTable.fxml"
                    )
            );
            
            Parent root = loader.load();
            
            FXMLQuizzTableController tableController = loader.getController();
            
            tableController.initializeUserId(userId);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
            stage.setScene(
                    new Scene(root)
            );
            stage.setTitle("Quiz Management");
            stage.show();
        } 
        catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void takeQuizz(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/simpalha/quizz/FXMLQuizzTaking.fxml"
                    )
            );
            
            Parent root = loader.load();
            
            FXMLQuizzTakingController tableController = loader.getController();
            
            tableController.initializeUserId(userId);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
            stage.setScene(
                    new Scene(root)
            );
            stage.setTitle("Quiz List");
            stage.show();
        } 
        catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
