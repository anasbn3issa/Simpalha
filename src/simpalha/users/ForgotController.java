/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.users;

import entities.CurrentUser;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.ServiceUsers;
import utils.SendMail;

/**
 * FXML Controller class
 *
 * @author win10
 */
public class ForgotController implements Initializable {

    @FXML
    private TextField tfusername;
    CurrentUser cu;
    @FXML
    private TextField tfcode;
    @FXML
    private Button resetbutton;
    @FXML
    private Label labelemail;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 
        
        tfcode.setDisable(true);
        resetbutton.setDisable(true);
    }    
 @FXML
    private void sendCode(ActionEvent event) throws SQLException {

        {
            cu = CurrentUser.CurrentUser();
            ServiceUsers us = new ServiceUsers();
            if (us.usernameExist(tfusername.getText())) {
                tfcode.setDisable(false);
                resetbutton.setDisable(false);
                labelemail.setText("un code a été envoyé a votre Email, retapez-le ici");

                String code = us.getAlphaNumericString(8);
                cu.targetId = us.geIdbyUsername(tfusername.getText());
                cu.code = code;

                String email = us.getEmailbyUsername(tfusername.getText());
                us.updateCode(code, us.geIdbyUsername(tfusername.getText()));

                String cn = "Saissisez ce code pour réinitialiser votre mot de passe : " + code;

                String sb = "Mot de passe oublié";
                SendMail.sendMail(email, sb, cn);
            } else {
                labelemail.setText("Username n'existe pas");
            }
        }

    }

    @FXML
    private void reinitialiser(ActionEvent event) throws SQLException, IOException {

        System.out.println(cu);
        if (cu.code.equals(tfcode.getText())) {
            //note that on this line you can substitue "Screen2.fxml" for a string chosen prior to this line.
            Parent loader;
            try {
                loader = FXMLLoader.load(getClass().getResource("Reset.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

                Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

                Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

                app_stage.setScene(scene); //This sets the scene as scene

                app_stage.show(); // this shows the scene
            } catch (IOException ex) {
            }
        } else {
            labelemail.setText("code incorrect");
        }
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
        }

    }



    @FXML
    private void goToViewPosts(MouseEvent event) {
    }

    @FXML
    private void showP2P(MouseEvent event) {
    }

    
}
