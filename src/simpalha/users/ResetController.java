/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.users;

import entities.CurrentUser;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.ServiceUsers;

/**
 * FXML Controller class
 *
 * @author win10
 */
public class ResetController implements Initializable {

    @FXML
    private PasswordField password;
    @FXML
    private PasswordField passwordconf;
    @FXML
    private Label text;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
    private void resetpasswd(ActionEvent event) throws IOException {
        
     
       
        CurrentUser cu = CurrentUser.CurrentUser();
        if(!password.getText().isEmpty()&& !passwordconf.getText().isEmpty())
        {
         
            if (password.getText().equals(passwordconf.getText()))
            {
                ServiceUsers us = new ServiceUsers();
                
                us.updatePassword(password.getText(), cu.targetId );
                
                Parent loader;
            try {
                loader = FXMLLoader.load(getClass().getResource("Login.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

                Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

                Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

                app_stage.setScene(scene); //This sets the scene as scene

                app_stage.show(); // this shows the scene
            } catch (IOException ex) {
            }
            }
            else
            
               text.setText("Les deux mots de passe ne sont identiques");
            }
        }
        
    @FXML
    private void goToViewPosts(MouseEvent event) {
    }

    @FXML
    private void showP2P(MouseEvent event) {
    }

    
}
