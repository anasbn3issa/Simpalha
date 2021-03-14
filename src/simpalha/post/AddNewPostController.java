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
            Text shareYourProblemText = new Text("Share your problem");
            //textProblem= new TextArea();
            shareYourProblemText.setStyle("-fx-fill: linear-gradient(from 0% 0% to 100% 200%, repeat, aqua 0%, red 50%);\n"
                    + "    -fx-stroke: black;\n"
                    + "    -fx-stroke-width: 1;");
            comboModule = new ComboBox<String>();
            comboModule.getItems().removeAll(comboModule.getItems());
            comboModule.getItems().addAll("IP Essentials", "Mathématique de base 1", "Mathématique de base 2", "Génie Logiciel"); // mba3d nrodou marbout b classe specialité .
            comboModule.getSelectionModel().select("Math,java .."); // shnowa maktoub par défaut . 
            Text moduleLabel = new Text("Module");
            HBox hboxModule = new HBox();
            hboxModule.getChildren().addAll(moduleLabel, comboModule);

            Text problemLabel = new Text("Problem");
            TextArea textProblem = new TextArea();
            Button submit = new Button("Submit");

            HBox hboxProblem = new HBox();
            hboxProblem.getChildren().addAll(problemLabel, textProblem, submit);

            addProblemContainer.getChildren().addAll(shareYourProblemText, hboxModule, hboxProblem);

            submit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    // Partie 1 : Add this post to Database , only need to set 
                    String s1 = textProblem.getText();
                    String s2 = comboModule.getValue();
                    Post p = new Post(s1, s2);
                    System.out.println("s1"+s1+"s2"+s2);
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

}
