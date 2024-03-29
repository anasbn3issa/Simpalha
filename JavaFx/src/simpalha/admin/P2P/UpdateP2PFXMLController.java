/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.admin.P2P;

import simpalha.P2P.*;
import entities.Disponibilite;
import entities.Feedback;
import entities.Meet;
import entities.Users;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.ServiceDisponibilite;
import services.ServiceFeedback;
import services.ServiceP2P;
import services.ServiceUsers;
import simpalha.FXMLDocumentController;
import utils.UserSession;

/**
 * FXML Controller class
 *
 * @author α Ω
 */
public class UpdateP2PFXMLController implements Initializable {

    
    private String idMeet;
    private int idHelper;
    private ServiceP2P service;
    private ServiceDisponibilite serviceDisp;
    private ServiceUsers serviceUser;
    private ServiceFeedback serviceFeedback;
    
    @FXML
    private Button back;
    
    @FXML
    private TextField specialite;
    @FXML
    private TextField time;
    @FXML
    private TextField student;
    @FXML
    private TextField helper;
    @FXML
    private TextArea feedback;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            service = new ServiceP2P();
            serviceDisp = new ServiceDisponibilite();
            serviceUser = new ServiceUsers();
            serviceFeedback = new ServiceFeedback();
            
            
            Meet meet = service.findById(idMeet);
            
            Disponibilite dis = serviceDisp.findById(Integer.valueOf(meet.getTime()));
            
            time.setText(dis.getDatedeb());
            Users hlp = serviceUser.findById(idHelper);
            System.out.println(hlp);
            helper.setText(hlp.getUsername());
            
            Users std = serviceUser.findById(meet.getId_student());
            student.setText(std.getUsername());
            
            specialite.setText(hlp.getSpecialty());
            
            Feedback fdb = serviceFeedback.findById(meet.getFeedback_id());
            feedback.setText(fdb.getFeedback());

        });
    }   
    
    void initData(String id, int idH) {
        idMeet = id;
        idHelper = idH;
    }

    @FXML
    private void showP2P(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "P2PFXML.fxml"
                    )
            );

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
            stage.setScene(
                    new Scene(loader.load())
            );
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Back(ActionEvent event) {
        //note that on this line you can substitue "Screen2.fxml" for a string chosen prior to this line.
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("P2PFXML.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }    

    @FXML
    private void logout(MouseEvent event) {
        UserSession.getInstace(0).cleanUserSession();
       //note that on this line you can substitue "Screen2.fxml" for a string chosen prior to this line.
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("/simpalha/users/Login.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }
}
