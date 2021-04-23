/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.users;

import entities.Users;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Base64;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.ServiceUsers;
import utils.UserSession;

/**
 * FXML Controller class
 *
 * @author win10
 */
public class LoginController implements Initializable {

    @FXML
    private TextField tfusername;
    @FXML
    private TextField tfpassword;
    @FXML
    private Text exist;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @FXML
    private void register(ActionEvent event) throws IOException {
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("Ajouter.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }

    }

    @FXML
    private void authentifier(ActionEvent event) throws SQLException {

        if (!tfusername.getText().isEmpty() && !tfpassword.getText().isEmpty()) {
            ServiceUsers us = new ServiceUsers();

            Users u = new Users(tfpassword.getText(), tfusername.getText());
            if (us.check(u)) {
                int id = us.geIdbyUsername(u.getUsername());
                UserSession usr
                        = UserSession.getInstace(id);
                u = us.findById(id);
                System.out.println(u);
                String path = "simpalha/FXMLDocument.fxml";
                if (u.getRole() == 1) {
                    path = "simpalha/admin/FXMLDocument.fxml";
                }
                try {
                    Parent loader;
                   // loader = FXMLLoader.load(getClass().getClassLoader().getResource("simpalha/post/ViewPosts.fxml"));
                   loader = FXMLLoader.load(getClass().getClassLoader().getResource("simpalha/FXMLDocument.fxml"));
                    //Creates a Parent called loader and assign it as ScReen2.FXML

                    Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

                    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

                    app_stage.setScene(scene); //This sets the scene as scene

                    app_stage.show(); // this shows the scene
                } catch (IOException ex) {
                }
            } else {
                exist.setText("inexistant user");
            }

        } else {

            System.out.println("not found  ");
        }

    }

    @FXML
    private void forgot(ActionEvent event) throws IOException {

        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("Forgot.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }

    }
}
