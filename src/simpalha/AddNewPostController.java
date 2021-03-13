/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha;

import entities.Post;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class AddNewPostController implements Initializable {

    @FXML
    private ComboBox<String> comboModule;
    @FXML
    private TextArea textProblem;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         comboModule.getItems().removeAll(comboModule.getItems());
         comboModule.getItems().addAll("IP Essentials", "Mathématique de base 1","Mathématique de base 2", "Génie Logiciel"); // mba3d nrodou marbout b classe specialité .
         
         
         comboModule.getSelectionModel().select("Math,java .."); // shnowa maktoub par défaut . 
    }    

    @FXML
    private void goToViewPosts(MouseEvent event) {
        
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
            Logger.getLogger(AddNewPostController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void AddNewPost(ActionEvent event) {
    }

    @FXML
    private void buttonAddPostPushed(ActionEvent event) {
        
        String s1 = textProblem.getText();
        String s2 = comboModule.getValue();
        Post p = new Post(s1, s2);
        ServicePost s = new ServicePost();
        s.Create(p);
    }
    
}
