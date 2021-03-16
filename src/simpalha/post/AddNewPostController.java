/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.post;

import entities.Post;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
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
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.ServicePost;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author anaso
 */
public class AddNewPostController implements Initializable {

    private ComboBox<String> comboModule;
    private TextArea textProblem;
    @FXML
    private VBox addProblemContainer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            // first Label in page 
            Text shareYourProblemText = new Text("Share your problem");
            shareYourProblemText.setStyle("-fx-fill: linear-gradient(from 0% 0% to 100% 200%, repeat, aqua 0%, red 50%);\n"
                    + "    -fx-stroke: black;\n"
                    + "    -fx-stroke-width: 1;");
            // the combobox to Set module
            comboModule = new ComboBox<String>();
            comboModule.getItems().removeAll(comboModule.getItems());
            comboModule.getItems().addAll("IP Essentials", "Mathématique de base 1", "Mathématique de base 2", "Génie Logiciel"); // mba3d nrodou marbout b classe specialité .
            comboModule.getSelectionModel().select("Math,java .."); // shnowa maktoub par défaut . 
            Text moduleLabel = new Text("Module");
            HBox hboxModule = new HBox();
            hboxModule.getChildren().addAll(moduleLabel, comboModule);

            // the problem to Set problem 
            Text problemLabel = new Text("Problem");
            TextArea textProblem = new TextArea();
            Button submit = new Button("Submit");

            HBox hboxProblem = new HBox();
            hboxProblem.getChildren().addAll(problemLabel, textProblem);

            // the file / files 
            Button buttonFile1 = new Button("Add File");
            Text fileLabel = new Text("Import File");
            HBox hboxFile1 = new HBox();
            hboxFile1.getChildren().addAll(fileLabel, buttonFile1);

            Button buttonFile2 = new Button("Add Files");
            Text filesLabel = new Text("Import Files");
            HBox hboxFile2 = new HBox();
            hboxFile2.getChildren().addAll(filesLabel, buttonFile2);
            buttonFile1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    FileChooser fc = new FileChooser();
                    //fc.setInitialFileName("w na3tih houni l path"); // hethy tkhali el filechooser yet7al fel page mta3  lpath donné
                    fc.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("img files", "*.jpg", "*.png")
                    );
                    File selectedFile = fc.showOpenDialog(null);

                    if (selectedFile != null) {
                        System.out.println(selectedFile.getAbsolutePath());
                        copy(selectedFile);

                    } else {
                        System.out.println("file not valid");

                    }

                }
            });
            buttonFile2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    FileChooser fc = new FileChooser();
                    //fc.setInitialFileName("w na3tih houni l path"); // hethy tkhali el filechooser yet7al fel page mta3  lpath donné
                    fc.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("img files", "*.jpg", "*.png")
                    );
                    List<File> selectedFiles = fc.showOpenMultipleDialog(null);

                    if (selectedFiles != null) {
                        for (int i = 0; i < selectedFiles.size(); i++) {
                            System.out.println(selectedFiles.get(i).getAbsolutePath());
                        }

                    } else {
                        System.out.println("files not valid");

                    }

                }
            });

            addProblemContainer.getChildren().addAll(shareYourProblemText, hboxModule, hboxProblem, hboxFile1, hboxFile2, submit);

            submit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    // Partie 1 : Add this post to Database , only need to set 
                    String s1 = textProblem.getText();
                    String s2 = comboModule.getValue();
                    Post p = new Post(s1, s2);
                    System.out.println("s1" + s1 + "s2" + s2);
                    ServicePost s = new ServicePost();
                    s.Create(p);
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
            });

        });

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

    private void buttonAddPostPushed(ActionEvent event) {

        String s1 = textProblem.getText();
        String s2 = comboModule.getValue();
        Post p = new Post(s1, s2);
        ServicePost s = new ServicePost();
        s.Create(p);
    }

      private void copy(File from) {
        String dir = System.getProperty("user.dir");//get project source path
        File dest = new File(dir + "\\ressources\\"+from.getName());//add the full path /ressources + file name
        
//check if folder ressources is created, sinon create it
        File file = new File(dir+"\\ressources");
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
            }catch (IOException ex) {
                Logger.getLogger(AddNewPostController.class.getName()).log(Level.SEVERE, null, ex);
            }
            /////////////////
        }


    }

}
