/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.users;

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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.ServiceUsers;

/**
 * FXML Controller class
 *
 * @author win10
 */
public class ModifierProfileController implements Initializable {
    private int userId;
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField email;
    @FXML
    private Button modif;
    @FXML
    private Label spécialité;
    @FXML
    private ImageView img;
            private ServiceUsers service;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(() -> {
            service = new ServiceUsers();
            
            Users user = service.finfById(userId);
            
            nom.setText(user.getUsername());
            spécialité.setText(user.getSpecialité());
            email.setText(user.getEmail());
        });
    }

    void initData(int id) {
userId = id;    }

    @FXML
    private void goToViewPosts(MouseEvent event) {
    }

    @FXML
    private void showP2P(MouseEvent event) {
    }

     @FXML
    private void update(ActionEvent event) {
        
        Users user = service.finfById(userId);
        user.setUsername(nom.getText());
        
        user.setEmail(email.getText());
        service.Update(user);
        
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "ModifierProfile.fxml"
                    )
            );

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
            stage.setScene(
                    new Scene(loader.load())
            );
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ModifierProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void DésactiverCompte(ActionEvent event) {
    }

     @FXML
    private void back(ActionEvent event) {
        
         {

            //note that on this line you can substitue "Screen2.fxml" for a string chosen prior to this line.
            Parent loader;
            try {
                loader = FXMLLoader.load(getClass().getResource("CompteAdmin.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

                Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

                Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

                app_stage.setScene(scene); //This sets the scene as scene

                app_stage.show(); // this shows the scene
            } catch (IOException ex) {
            }
        }
        
    }
}
