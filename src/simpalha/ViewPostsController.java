/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha;

import entities.Post;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.ServicePost;

/**
 * FXML Controller class
 *
 * @author anaso
 */
public class ViewPostsController implements Initializable {
    
    @FXML
    private TableView<Post> table;
    @FXML
    private TableColumn<Post,Integer> id;
    @FXML
    private TableColumn<Post, String> problem;
    @FXML
    private TableColumn<Post, Timestamp> timestamp;
    @FXML
    private TableColumn<Post, String> module;
    @FXML
    private TableColumn<Post, String> status;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServicePost cs = new ServicePost();
         List<Post> lc = null;
         lc = cs.Read(); 
         ObservableList<Post> data =FXCollections.observableArrayList(lc );
         id.setCellValueFactory(
                new PropertyValueFactory<>("id"));
          
        problem.setCellValueFactory(
                new PropertyValueFactory<>("problem"));
 
       
        timestamp.setCellValueFactory(
                new PropertyValueFactory<>("timestamp"));
 
        
        module.setCellValueFactory(
                new PropertyValueFactory<>("module"));
        
        

 
        table.setItems(data);
    }    

    @FXML
    private void goToViewPosts(MouseEvent event) {
        
        // ya besh na3mlelha copier coller 3ali 3maltou f FXMLDocumentController wala nfarkselha 3la fonction refresh page b java ..
    }

    @FXML
    private void AddNewPost(ActionEvent event) {
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("AddNewPost.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        
    }
    
}
}