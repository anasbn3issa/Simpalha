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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.ServiceUsers;

/**
 * FXML Controller class
 *
 * @author win10
 */
public class AjouterController implements Initializable {

    @FXML
    private TextField email;
    @FXML
    private TextField username;
    @FXML
    private TextField passwd;
    @FXML
    private Button confirm;
private ServiceUsers srv;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        

        srv = new ServiceUsers();
    }    

    @FXML
    private void ajouter(ActionEvent event) {
        Users user = new Users(passwd.getText(), email.getText(), username.getText());
        srv.Create(user);
    }

    @FXML
    private void back(ActionEvent event) {
        
        {
        
         //note that on this line you can substitue "Screen2.fxml" for a string chosen prior to this line.
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("Login.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
        
        
    }}
    

    
}
