/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.users;

import entities.Candidature;
import entities.Disponibilite;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
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
import services.ServiceCandidature;
import services.ServiceDisponibilite;
import services.ServiceUsers;
import tornadofx.control.DateTimePicker;
import utils.UserSession;

/**
 * FXML Controller class
 *
 * @author win10
 */
public class AddAvailabilityController implements Initializable {

    @FXML
    private Button back;
    @FXML
    private Button confirmer;
  
    @FXML
    private DateTimePicker start;
    @FXML
    private DateTimePicker end;
    private ServiceDisponibilite srv;
    private int helperid;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        helperid = UserSession.getInstace(0).getUserid();
        System.out.println(helperid);
        srv = new ServiceDisponibilite();
    }    

    @FXML
    private void showP2P(MouseEvent event) {
    }

    @FXML
    private void Back(ActionEvent event) {
           Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("Profile.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }

    @FXML
    private void reserver(ActionEvent event) {
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String started = start.getDateTimeValue().format(formatter);
        String finished = end.getDateTimeValue().format(formatter);
        Disponibilite disp=new Disponibilite(helperid, started, finished);
         srv.Create(disp);
         
       Disponibilite can = srv.findById(helperid);
        System.out.println(can);
    }
    
}
