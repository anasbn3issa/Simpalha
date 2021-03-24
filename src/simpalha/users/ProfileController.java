/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.users;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import services.ServiceUsers;
import utils.UserSession;

/**
 * FXML Controller class
 *
 * @author win10
 */
public class ProfileController implements Initializable {

    @FXML
    private TableView<?> tableinvitations;
    @FXML
    private TableColumn<?, ?> names;
    @FXML
    private TableColumn<?, ?> hiddenid;
    @FXML
    private Button logout;
    @FXML
    private PasswordField changepassword;
    @FXML
    private PasswordField confirmpassword;
    @FXML
    private TextField changeabout;
    @FXML
    private Label erreur;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void goToViewPosts(MouseEvent event) {
    }

    @FXML
    private void showP2P(MouseEvent event) {
    }

    @FXML
    private void candidatures(MouseEvent event) {
    }

   

    @FXML
    private void accepterAmi(ActionEvent event) {
    }

    @FXML
    private void refuserAmi(ActionEvent event) {
    }

    @FXML
    private void selectionInvitations(MouseEvent event) {
    }



    @FXML
    private void logout(ActionEvent event) {
         UserSession cu = UserSession.getInstace(0); 
        
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Login.fxml")); 
        Parent root = loader.load();
        logout.getScene().setRoot(root);
        }
        catch(IOException e) 
        { }
    }

    @FXML
    private void aboutUpdate(ActionEvent event) {
        ServiceUsers us = new ServiceUsers();
       if( !changeabout.getText().isEmpty())
        {
          us.updateAbout(changeabout.getText());
          erreur.setText("Description modifié !");
        } 
    }

    @FXML
    private void passwordUpdate(ActionEvent event) {
        
         ServiceUsers us = new ServiceUsers();
        UserSession cu= UserSession.getInstace(0);
        
        if( !changepassword.getText().isEmpty() && !confirmpassword.getText().isEmpty())
        {
            if (changepassword.getText().equals(confirmpassword.getText()))
            {
                us.updatePassword(changepassword.getText(),cu.userid); 
                erreur.setText("mot de passe modifié !");  
            }
            else
            {
               erreur.setText("Erreur : Les deux mots de passe ne sont pas identiques !"); 
            }
          
        } 
        
    }

    void initData(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
