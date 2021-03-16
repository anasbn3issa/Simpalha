/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.post;

import entities.Comment;
import entities.Post;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.ServiceComment;
import services.ServicePost;

public class AddCommentController implements Initializable {

    private int idPost;
    private HBox postContainer;
    private ServicePost servicePost;
    private ServiceComment serviceComment;

    private TextArea solutionText;

    ImageView upvoteImage, downvoteImage;

    Text commentOwnerName, commentText, commentLabel, ratingText, ratingLabel, TimestampText;

    Post p;
    Comment c;
    List<Comment> commentsForThisPost;

    HBox upVotedownVoteHbox;
    Button upvoteButton, downvoteButton;

    @FXML
    private VBox problemInfoContainer;
    private HBox hboxPost;
    @FXML
    private VBox vboxPost2;
    @FXML
    private HBox hboxPost2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        Platform.runLater(() -> {
            serviceComment = new ServiceComment();
            servicePost = new ServicePost();

            // we need to add a textfield for user to type a proposed solution to the post 
            Text proposeASolutionLabel = new Text("Propose a solution ? ");
            TextArea proposedSolution = new TextArea();
            Button submitButton = new Button("Submit");

            HBox hboxUserSolution = new HBox();
            hboxUserSolution.getChildren().addAll(proposeASolutionLabel, proposedSolution, submitButton);

            submitButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    // Partie 1 : Add this comment to Database , only need to set 
                    c.setSolution(proposedSolution.getText());
                    c.setOwner("Alolou"); // hethy nzidoha mba3d ki ywali 3ana owner berasmi
                    c.setId_Post(idPost);
                    serviceComment.Create(c);

                    // Partie 2 : Add the new Comment to get Displayed . 
                    HBox commentContainer = new HBox();

                    commentOwnerName = new Text("Steve Jobs"); // hethy mbaad nrodha berasmi esm li 3mal l comment w na3ml findById w kol
                    commentOwnerName.setStyle("-fx-fill: linear-gradient(from 0% 0% to 100% 200%, repeat, aqua 0%, red 50%);\n"
                            + "    -fx-stroke: black;\n"
                            + "    -fx-stroke-width: 1;");
                    commentLabel = new Text("Proposed Solution : ");
                    ratingLabel = new Text("rating");

                    commentText = new Text(c.getSolution());
                    ratingText = new Text(String.valueOf(c.getRating()));
                    TimestampText = new Text(String.valueOf(c.getTimestamp()));

                    VBox vboxCommentOwner = new VBox();

                    vboxCommentOwner.getChildren().addAll(commentOwnerName); // 3maltelha HBox wa7adha psk mbaad newi nzid des infos okhrin bjanb el name kima specialite main mte3ou
                    HBox hboxRating = new HBox();
                    hboxRating.getChildren().addAll(ratingLabel, ratingText);
                    HBox hboxComment = new HBox();
                    hboxComment.getChildren().addAll(commentText);
                    hboxComment.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                            + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                            + "-fx-border-radius: 5;" + "-fx-border-color: black;" + "-fx-background-color: white;");
                    hboxComment.setPrefWidth(500);
                    HBox hboxButtons = new HBox();
//                Button RienAFaire = new Button("maya3ml shay");
//                hboxButtons.getChildren().addAll(RienAFaire);

                    commentContainer.getChildren().addAll(vboxCommentOwner, hboxRating, hboxComment, hboxButtons);
                    commentContainer.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                            + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                            + "-fx-border-radius: 5;" + "-fx-border-color: black;");
                    commentContainer.setStyle("-fx-background-color: white;");
                    commentContainer.setStyle("-fx-border-color: black;");
                    problemInfoContainer.getChildren().addAll(commentContainer);
                    problemInfoContainer.setMargin(commentContainer, new Insets(6, 6, 6, 6));
                }
            });

            // in here we display the post selected from previous interface
            vboxPost2.getChildren().addAll(hboxPost, hboxUserSolution);
            vboxPost2.setPadding(new Insets(5, 10, 10, 5));
            vboxPost2.setStyle("    -fx-padding: 15; "
                    + "    -fx-spacing: 10; " + "-fx-border-style: solid inside;"
                    + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                    + "-fx-border-radius: 5;" + "-fx-border-color: black;");

            //p = servicePost.findById(idPost);
            // and now we search for all comments related to this post AND then Display them 
            commentsForThisPost = servicePost.findAllCommentsForThisPost(idPost);
            c = new Comment();

            for (Comment parcours : commentsForThisPost) {
                HBox commentContainer = new HBox();

                // Partie 1 : we get comment owner info 
                commentOwnerName = new Text("Steve Jobs"); // hethy mbaad nrodha berasmi esm li 3mal l comment w na3ml findById w kol
                commentOwnerName.setStyle("-fx-fill: linear-gradient(from 0% 0% to 100% 200%, repeat, aqua 0%, red 50%);\n"
                        + "    -fx-stroke: black;\n"
                        + "    -fx-stroke-width: 1;");
                // Partie 2 : we get comment info 
                commentLabel = new Text("Proposed Solution : ");
                ratingLabel = new Text("rating");
                commentText = new Text(parcours.getSolution());
                ratingText = new Text(String.valueOf(parcours.getRating()));
                TimestampText = new Text(String.valueOf(parcours.getTimestamp()));

                // Partie 3.1 : initialize upvote AND downvote buttons 
                FileInputStream inputUpvoteImage = null;
                FileInputStream inputDownvoteImage = null;
                try {
                    //upvote Button 

                    inputUpvoteImage = new FileInputStream("C:\\Users\\anaso\\OneDrive\\Desktop\\Simpalha\\src\\simpalha\\post\\img\\upvote_black.png");
                    Image imageUp = new Image(inputUpvoteImage);
                    upvoteImage = new ImageView(imageUp);
                    upvoteButton = new Button();
                    upvoteButton.setGraphic(upvoteImage);
                    upvoteButton.setPrefWidth(50);
                    upvoteButton.setPrefHeight(50);

                    //downvote Button 
                    inputDownvoteImage = new FileInputStream("C:\\Users\\anaso\\OneDrive\\Desktop\\Simpalha\\src\\simpalha\\post\\img\\downvote_black.png");
                    Image imageDown = new Image(inputDownvoteImage);
                    downvoteImage = new ImageView(imageDown);
                    downvoteButton = new Button();
                    downvoteButton.setGraphic(downvoteImage);
                    downvoteButton.setPrefWidth(50);
                    downvoteButton.setPrefHeight(50);

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(AddCommentController.class.getName()).log(Level.SEVERE, null, ex);
                }

                //Partie 3.2: put upvote AND downvote buttons in Containers 
                upVotedownVoteHbox = new HBox();
                VBox upVoteVbox = new VBox();
                VBox downVoteVbox = new VBox();
                Text upvoteLabel = new Text("+");
                Text downvoteLabel = new Text("-");
                //upVoteVbox.setPrefHeight(50);
                upVoteVbox.getChildren().addAll(upvoteLabel, upvoteButton);
                //downVoteVbox.setPrefWidth(50);
                downVoteVbox.getChildren().addAll(downvoteLabel, downvoteButton);
                upVotedownVoteHbox.getChildren().addAll(upVoteVbox, downVoteVbox);

                //Partie 3.3 : downvote button on action 
                downvoteButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        
                        
                            System.out.println("downvote button pushed");

                        FileInputStream inputDownvoteImage = null;
                        try {
                            inputDownvoteImage = new FileInputStream("C:\\Users\\anaso\\OneDrive\\Desktop\\Simpalha\\src\\simpalha\\post\\img\\downvote_red.png");
                            Image imageDown = new Image(inputDownvoteImage);
                            downvoteImage = new ImageView(imageDown);
                            downVoteVbox.getChildren().clear();
                            downvoteButton.setGraphic(downvoteImage);
                            downVoteVbox.getChildren().addAll(downvoteLabel,downvoteButton);
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(AddCommentController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                });
                //Partie 3.4 : upvote button on action 
                upvoteButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        FileInputStream inputUpvoteImage = null;
                        try {
                            
                            System.out.println("upvote button pushed");
                                    
                            inputUpvoteImage = new FileInputStream("C:\\Users\\anaso\\OneDrive\\Desktop\\Simpalha\\src\\simpalha\\post\\img\\upvote_green.png");
                            Image imageUp = new Image(inputUpvoteImage);
                            upvoteImage = new ImageView(imageUp);
                            upVoteVbox.getChildren().clear();
                            upVoteVbox.getChildren().addAll(upvoteLabel,upvoteButton);
                            upvoteButton.setGraphic(upvoteImage); // écraser l'anciene valeur par une nouvelle image 
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(AddCommentController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });

                VBox vboxCommentOwner = new VBox();

                vboxCommentOwner.getChildren().addAll(commentOwnerName); // 3maltelha HBox wa7adha psk mbaad newi nzid des infos okhrin bjanb el name kima specialite main mte3ou
                HBox hboxRating = new HBox();
                hboxRating.getChildren().addAll(ratingLabel, ratingText);
                HBox hboxComment = new HBox();
                hboxComment.getChildren().addAll(commentText);
                hboxComment.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                        + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                        + "-fx-border-radius: 5;" + "-fx-border-color: black;" + "-fx-background-color: white;");
                hboxComment.setPrefWidth(500);
                HBox hboxButtons = new HBox();
//                Button RienAFaire = new Button("maya3ml shay");
//                hboxButtons.getChildren().addAll(RienAFaire);

                commentContainer.getChildren().addAll(vboxCommentOwner, hboxRating, hboxComment, hboxButtons, upVotedownVoteHbox);
                commentContainer.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                        + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                        + "-fx-border-radius: 5;" + "-fx-border-color: black;");
                commentContainer.setStyle("-fx-background-color: white;");
                commentContainer.setStyle("-fx-border-color: black;");
                problemInfoContainer.getChildren().addAll(commentContainer);
                problemInfoContainer.setMargin(commentContainer, new Insets(6, 6, 6, 6));
                commentContainer.setSpacing(15);
                commentContainer.setStyle("-fx-background-color: white;"); // mela bsh nkhaliwha maghir style ?? 
                commentContainer.setStyle("-fx-border-color: black;");
            }

        });
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
    private void AddNewPost(ActionEvent event) { // BUTTON PUSHED
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

    private void CancelButtonPushed(ActionEvent event) {
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("ViewPosts.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    private void SubmitButtonPushed(ActionEvent event) {

        // Partie 1 : Add this comment to Database , only need to set 
        c.setSolution(solutionText.getText());
        c.setOwner("Alolou"); // hethy nzidoha mba3d ki ywali 3ana owner berasmi
        c.setId_Post(idPost);
        serviceComment.Create(c);

        // Partie 2 : Add the new Comment to get Displayed . 
        HBox commentContainer = new HBox();

        commentOwnerName = new Text("LM3allem"); // hethy mbaad nrodha berasmi esm li 3mal l comment w na3ml findById w kol
        commentLabel = new Text("Proposed Solution : ");
        ratingLabel = new Text("rating");

        commentText = new Text(c.getSolution());
        ratingText = new Text(String.valueOf(c.getRating()));
        TimestampText = new Text(String.valueOf(c.getTimestamp()));

        HBox hboxCommentOwner = new HBox();
        hboxCommentOwner.getChildren().addAll(commentOwnerName);
        HBox hboxRating = new HBox();
        hboxRating.getChildren().addAll(ratingLabel, ratingText);
        HBox hboxComment = new HBox();
        hboxComment.getChildren().addAll(commentLabel, commentText);

        HBox hboxButtons = new HBox();
        Button RienAFaire = new Button("maya3ml shay");
        hboxButtons.getChildren().addAll(RienAFaire);

        commentContainer.getChildren().addAll(hboxCommentOwner, hboxRating, hboxComment, hboxButtons);
        problemInfoContainer.getChildren().addAll(commentContainer);

    }

    void initData(int id) {
        idPost = id;
    }

    private void sortByRatingButtonPushed(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddCommentSortedBy.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
            stage.setScene(new Scene(loader.load()));

            AddCommentSortedByController controller = loader.getController();
            controller.initData(p.getId());  // hethy mbaad bsh nzidha postownerid

            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ViewPostsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void initData(int id, HBox postContainer) {
        idPost = id;
        this.postContainer = new HBox();
        hboxPost = postContainer;

    }
}
