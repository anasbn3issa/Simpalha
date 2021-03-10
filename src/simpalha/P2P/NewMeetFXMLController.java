/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.P2P;

import entities.Disponibilite;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.ServiceDisponibilite;
import services.ServiceP2P;
import services.ServiceUser;

/**
 * FXML Controller class
 *
 * @author α Ω
 */
public class NewMeetFXMLController implements Initializable {

    private int helperId;
    private ServiceDisponibilite serviceDisp;
    private ServiceUser serviceUser;
    private ServiceP2P serviceP2P;
    
    @FXML
    private Button back;
    @FXML
    private TextField helper;
    @FXML
    private TextField specialite;
    @FXML
    private ComboBox<String> times;
    @FXML
    private Button confirmer;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO        
        Platform.runLater(() -> {
            
            serviceDisp  = new ServiceDisponibilite();
            serviceUser = new ServiceUser();
            serviceP2P = new ServiceP2P();
            
            List<Disponibilite> dispoList = serviceDisp.findAllById(helperId);
            for (Disponibilite d :dispoList){
                System.out.println(d.getDatedeb()+"->"+d.getDateFin());
            }
            System.out.println(helperId);
            times.getItems().addAll(dispoList.stream().map(d-> d.getDatedeb()+" -> "+d.getDateFin()).toArray(String[]::new));
            Users student = serviceUser.findById(helperId);
            helper.setText(student.getFname()+" "+student.getLname());
            specialite.setText(student.getSpecialites());
        });
    }    
    
    public void initData(int id) {
        helperId = id;
    }

    @FXML
    private void showP2P(MouseEvent event) {
    }

    @FXML
    private void Back(ActionEvent event) {
        //note that on this line you can substitue "Screen2.fxml" for a string chosen prior to this line.
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("ListHelpersFXML.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }

    @FXML
    private void reserver(ActionEvent event) {
        Disponibilite disponibilite = serviceDisp.findByTime(times.getValue());
       disponibilite.setEtat(1);
       serviceDisp.Update(disponibilite);
       Meet meet = new Meet(helperId, helperId, specialite.getText(), String.valueOf(disponibilite.getId()));
       serviceP2P.Create(meet);       
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
            Logger.getLogger(NewMeetFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
