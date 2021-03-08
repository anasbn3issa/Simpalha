/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.P2P;

import entities.Meet;
import entities.Users;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.ServiceP2P;
import services.ServiceUser;

/**
 * FXML Controller class
 *
 * @author α Ω
 */
public class JoinMeetFXMLController implements Initializable {

    
    private String meetId;
    private ServiceUser serviceUser;
    private ServiceP2P serviceP2P;
    
    @FXML
    private Button reclamation;
    @FXML
    private Button leave;
    @FXML
    private Text title;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(() -> {
                    serviceUser = new ServiceUser();
                    serviceP2P = new ServiceP2P();
                    
                    Meet meet = serviceP2P.findById(meetId);
                    Users helper = serviceUser.findById(meet.getId_helper());
                    title.setText("Welcome to "+meet.getSpecialite()+" meeting with "+helper.getFname()+" "+helper.getLname());
                });
    } 
    
    void initData(String id) {
        meetId = id;
    }

    @FXML
    private void showP2P(MouseEvent event) {
    }

    @FXML
    private void reclamation(ActionEvent event) {
    }

    @FXML
    private void leave(ActionEvent event) {
        //note that on this line you can substitue "Screen2.fxml" for a string chosen prior to this line.
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("P2PFXML.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.close(); //This sets the scene as scene

            app_stage.hide();// this shows the scene
        } catch (IOException ex) {
        }
    }
    
}
