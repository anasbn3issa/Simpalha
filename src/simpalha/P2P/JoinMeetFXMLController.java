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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import services.ServiceP2P;
import services.ServiceUsers;
import simpalha.FXMLDocumentController;

/**
 * FXML Controller class
 *
 * @author α Ω
 */
public class JoinMeetFXMLController implements Initializable {

    private String meetId;
    private ServiceUsers serviceUser;
    private ServiceP2P serviceP2P;

    @FXML
    private Button reclamation;
    @FXML
    private Button leave;
    @FXML
    private Text title;
    
    private Meet meet;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(() -> {
                  //  webview.getEngine().load("http://127.0.0.1:8000/");
                  //  webview.setZoom(0.75);  //zoom in 25%.
            serviceUser = new ServiceUsers();
            serviceP2P = new ServiceP2P();

            meet = serviceP2P.findById(meetId);
            Users helper = serviceUser.findById(meet.getId_helper());
            title.setText("Welcome to " + meet.getSpecialite() + " meeting with " + helper.getUsername());
        });
    }

    void initData(String id) {
        meetId = id;
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
    private void reclamation(ActionEvent event) {
    }

    @FXML
    private void leave(ActionEvent event) {

        try {

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
            stage.close();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "NewFeedbackFXML.fxml"
                    )
            );
            Scene scene = new Scene(loader.load()); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"
            //Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
            Stage app_stage = new Stage();//this accesses the window.
            app_stage.initModality(Modality.WINDOW_MODAL);
            app_stage.initOwner(((Node) event.getTarget()).getScene().getWindow());
            app_stage.setScene(scene);

            NewFeedbackFXMLController controller = loader.getController();
            controller.initData(meet.getId());

            app_stage.show();

        } catch (IOException ex) {
        }

        /*Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("P2PFXML.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.close(); //This sets the scene as scene

            app_stage.hide();// this shows the scene
        } catch (IOException ex) {
        }*/
    }

}
