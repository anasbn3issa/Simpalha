/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.post;

import entities.Post;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.ServicePost;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author anaso
 */
public class ViewPostsController implements Initializable {

//    private TableView<Post> table;
//    private TableColumn<Post,String> id;
//    private TableColumn<Post, String> problem;
//    private TableColumn<Post, String> timestamp;
//    private TableColumn<Post, String> module;
//    private TableColumn<Post, String> status;
    //ObservableList<Post> data =FXCollections.observableArrayList();
    @FXML
    private VBox PostsContainer; // eli bsh n7ot fih l posts lkol 
    @FXML
    private HBox firstHboxInPage;

    Text idText, problemText, moduleText, statusText, timestampText;
    Text timestampLabel, problemLabel, statusLabel, moduleLabel, idLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {  // tawa el lista mte3i 9a3da tet3aba jawha behi . tawa lezmni nekhdm affichage post by post

        ServicePost cs = new ServicePost();
        List<Post> lc = cs.Read();

        for (Post p : lc) {
            System.out.println(p);

            HBox postContainer = new HBox();

            postContainer.setPrefWidth(firstHboxInPage.getPrefWidth());
            postContainer.setPrefHeight(firstHboxInPage.getPrefHeight());

            //idLabel = new Text("id");
            problemLabel = new Text("problem");
            moduleLabel = new Text("module");
            statusLabel = new Text("status");
            timestampLabel = new Text("timestamp");

            //idText = new Text(String.valueOf(p.getId()));
            problemText = new Text(p.getProblem());
            moduleText = new Text(p.getModule());
            timestampText = new Text(String.valueOf(p.getTimestamp()));

            Alert alertDeletepushed = new Alert(Alert.AlertType.WARNING);

            //HBox hboxId = new HBox();
            //hboxId.getChildren().addAll(idLabel, idText);
            HBox hboxProblem = new HBox();
            hboxProblem.getChildren().addAll(problemLabel, problemText);
            HBox hboxModule = new HBox();
            hboxModule.getChildren().addAll(moduleLabel, moduleText);
            HBox hboxTimestamp = new HBox();
            hboxTimestamp.getChildren().addAll(timestampLabel, timestampText);
            HBox hboxButtons = new HBox();
            Button btnDelete = new Button("Delete");
            Button btnModify = new Button("Modify");
            Button btnAddComment = new Button("Add Comment");
            hboxButtons.getChildren().addAll(btnDelete, btnModify, btnAddComment);
            btnDelete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    alertDeletepushed.setTitle("delete pushed ");
                    cs.Delete(p);
                    PostsContainer.getChildren().remove(postContainer);
                    postContainer.getChildren().remove(btnDelete);
                    alertDeletepushed.show();  //To change body of generated methods, choose Tools | Templates.
                }
            });

            btnModify.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    try {
                        FXMLLoader loader = new FXMLLoader(
                                getClass().getResource(
                                        "ModifyThisPost.fxml"
                                )
                        );

                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
                        stage.setScene(
                                new Scene(loader.load())
                        );

                        ModifyThisPostController controller = loader.getController();
                        controller.initData(p.getId());

                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(ViewPostsController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });

            btnAddComment.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                     try {
                        FXMLLoader loader = new FXMLLoader(
                                getClass().getResource(
                                        "AddComment.fxml"
                                )
                        );

                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
                        stage.setScene(
                                new Scene(loader.load())
                        );

                        AddCommentController controller = loader.getController();
                        controller.initData(p.getId());

                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(AddCommentController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });

            //h.getChildren().addAll(idLabel,idText,problemLabel,problemText,moduleLabel,moduleText,timestampLabel,timestampText);
            postContainer.getChildren().addAll(hboxTimestamp, hboxModule, hboxProblem, hboxButtons);
            postContainer.setSpacing(35);// hethy nzidha mba3d lbarsha Hboxouét bsh nba3dou razzebi men razzebi
            postContainer.setStyle("-fx-background-color: white;");
            postContainer.setStyle("-fx-border-color: black;");
            PostsContainer.getChildren().addAll(postContainer);
            PostsContainer.setMargin(postContainer, new Insets(6, 6, 6, 6));

        }

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
