/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.post;

import entities.Post;
import entities.Users;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.application.Platform;

import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.ServicePost;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.stage.FileChooser;
import utils.UserSession;
import services.ServiceUsers;
import simpalha.post.BadWordsDetector;
/**
 * FXML Controller class
 *
 * @author anaso
 */
public class AddNewPostController implements Initializable {

    private File selectedFile;
    @FXML
    private ComboBox<String> comboModule;
    @FXML
    private TextArea textProblem;
    @FXML
    private Hyperlink buttonFile1;
    @FXML
    private Button submit;
    @FXML
    private Text currentUserNameLabel;
    UserSession userSession;
    int userId;
    ServicePost servicePost;
    ServiceUsers serviceUsers;
    Users currentUser;
    List<String> modules;
    BadWordsDetector xx;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            servicePost=new ServicePost();
            serviceUsers= new ServiceUsers();
            userSession=UserSession.getInstace(0);
            userId=userSession.getUserid();
            currentUser=serviceUsers.findById(userId);
            modules=servicePost.ReadModules();            
            currentUserNameLabel.setText(currentUser.getUsername());
            xx= new BadWordsDetector();
            
            xx.loadConfigs();
            comboModule.getItems().removeAll(comboModule.getItems());
            comboModule.getItems().addAll(modules);
            Text moduleLabel = new Text("Module");

            buttonFile1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    FileChooser fc = new FileChooser();
                    //fc.setInitialFileName("w na3tih houni l path"); // hethy tkhali el filechooser yet7al fel page mta3  lpath donné
                    fc.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("img files", "*.jpg", "*.png")
                    );
                    selectedFile = fc.showOpenDialog(null);

                    if (selectedFile != null) {
                        System.out.println(selectedFile.getAbsolutePath());

                    } else {
                        System.out.println("file not valid");

                    }

                }
            });
        });

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

    private void copy(File from) {
        String dir = System.getProperty("user.dir");//get project source path
        File dest = new File(dir + "\\ressources\\" + from.getName());//add the full path /ressources + file name

//check if folder ressources is created, sinon create it
        File file = new File(dir + "\\ressources");
        file.mkdirs();
        /////
        //COPY FILE OPERATION
        InputStream is = null;
        OutputStream os = null;
        try {
            try {
                is = new FileInputStream(from);
                os = new FileOutputStream(dest);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(AddNewPostController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(AddNewPostController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            try {
                is.close();
                os.close();
            } catch (IOException ex) {
                Logger.getLogger(AddNewPostController.class.getName()).log(Level.SEVERE, null, ex);
            }
            /////////////////
        }

    }

    @FXML
    private void buttonSubmitPushed(ActionEvent event) {

        Post p;
        // Partie 1 : Add this post to Database , only need to set 
        String s1 = textProblem.getText();
        String s2 = comboModule.getValue();

//        if(s1.contains("3asba"))
//            System.out.println("feha 3asba --------");
        ArrayList<String> badwordsInProblem =new ArrayList<>();
        badwordsInProblem=xx.badWordsFound(s1);
        System.out.println("-------------bad words : "+badwordsInProblem.toString()+"-------------");

        if(badwordsInProblem.isEmpty())
            System.out.println("no bad words found");
        else System.out.println("bad words found--");

        
        //System.out.println("this is s3 empty normalement "+s3);
        if (s1.trim().equals("") || s2.isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "you need to fill all fields", ButtonType.OK);
            a.show();
        } else if (!badwordsInProblem.isEmpty()) 
        {
            Alert a = new Alert(Alert.AlertType.ERROR, "watch your language young man!", ButtonType.OK);
            a.show();
        }

        else {
            if (selectedFile != null) {
                String s3 = selectedFile.getName();
                p = new Post(s1, s2, s3);
                p.setOwnerId(userId);
                System.out.println("s1" + s1 + "s2" + s2 + "s3" + s3);
                servicePost.Create(p);
                copy(selectedFile);
            } else {
                p = new Post(s1, s2);
                servicePost.Create(p);
            }

            //Partie 2 : go to view Posts
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

    }

    @FXML
    private void goToViewPosts(ActionEvent event) {
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
    private void goToViewPosts(MouseEvent event) {
    }

    
}