/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.post;

import entities.Post;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.ServicePost;

/**
 * FXML Controller class
 *
 * @author anaso
 */
public class ModifyThisPostController implements Initializable {

    private int idPost;
    private ServicePost myService;
    @FXML
    private ComboBox<String> comboStatus;
    @FXML
    private ComboBox<String> comboModule;
    
    Post p ;
    @FXML
    private TextArea problemText;
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(() -> {
        
        myService = new ServicePost();
            

            p = myService.findById(idPost);

 
        comboModule.getItems().removeAll(comboModule.getItems());
        comboModule.getItems().addAll("IP Essentials", "Mathématique de base 1","Mathématique de base 2", "Génie Logiciel"); // mba3d nrodou marbout b classe specialité .  
        comboModule.getSelectionModel().select(p.getModule()); // shnowa maktoub par défaut . 

        comboStatus.getItems().removeAll(comboStatus.getItems());
        comboStatus.getItems().addAll("PENDING","SOLVED","OPEN"); 
        comboStatus.getSelectionModel().select(p.getStatus()); // shnowa maktoub par défaut . 
        System.out.println(p.getStatus());
        
        problemText.setText(p.getProblem());
        
         
        
        
        
        
         });
    } 
    
    void initData(int id)
    {
        idPost=id;
    }

    @FXML
    private void goToViewPosts(MouseEvent event) {
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("ViewPosts.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }

    @FXML
    private void AddNewPost(ActionEvent event) {
    }

    @FXML
    private void SaveChangesButtonPushed(ActionEvent event) {
        
        p.setModule(comboModule.getValue());
        p.setStatus(comboStatus.getValue());
        p.setProblem(problemText.getText());
        
        myService.Update(p);
        
        
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "ViewPosts.fxml"
                    )
            );

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
            stage.setScene(
                    new Scene(loader.load())
            );
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ModifyThisPostController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }

    @FXML
    private void CancelButtonPushed(ActionEvent event) {
        
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("ViewPosts.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
        
    }
    
}
