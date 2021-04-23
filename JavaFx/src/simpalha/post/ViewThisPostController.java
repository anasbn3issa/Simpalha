/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.post;

import entities.Post;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import services.ServicePost;

/**
 * FXML Controller class
 *
 * @author anaso
 */
public class ViewThisPostController implements Initializable {

    @FXML
    private Text currentUserNameLabel;
    @FXML
    private ImageView postImageView;
    @FXML
    private HBox module;
    @FXML
    private TextField moduleText;
    @FXML
    private TextArea problemText;
    @FXML
    private TextArea solutionText;

    private int postId;
    ServicePost servicePost;
    String dir = System.getProperty("user.dir");
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         Platform.runLater(() -> {
             
              servicePost=new ServicePost();
              Post post=servicePost.findById(postId);
              String postImagePath=dir+"\\ressources\\"+post.getImageName();
                      
                      
             try {
                 //get project source path

                 createImageView(postImagePath);
             } catch (FileNotFoundException ex) {
                 Logger.getLogger(ViewThisPostController.class.getName()).log(Level.SEVERE, null, ex);
             }
             
              });
        
        
    }    
    
    
    public void createImageView(String path) throws FileNotFoundException{
        FileInputStream post= new FileInputStream(path);
        Image u = new Image(post);
        postImageView = new ImageView(u);
    }

    @FXML
    private void goToViewPosts(MouseEvent event) {
    }

    @FXML
    private void CancelButtonPushed(ActionEvent event) {
    }

    @FXML
    private void AddNewPost(ActionEvent event) {
    }

    @FXML
    private void submitButtonPushed(ActionEvent event) {
    }
    
    
    public void initData(int postid){
        postId=postid;
        
        
    }
    
}
