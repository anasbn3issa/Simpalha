/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha;

import entities.Post;
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
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
    }
    
}
