/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.post;

import com.darkprograms.speech.translator.GoogleTranslate;
import static com.darkprograms.speech.translator.GoogleTranslate.detectLanguage;
import entities.Comment;
import entities.Post;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import services.ServiceComment;
import services.ServicePost;
import utils.Maconnexion;

public class AddCommentController implements Initializable {

    private int idPost;
    //private HBox postContainer;
    private ServicePost servicePost;
    private ServiceComment serviceComment;

    private Text solutionText;

    ImageView upvoteImage, downvoteImage;

    Text commentOwnerName, commentText, commentLabel, ratingText, ratingLabel, TimestampText;

    Post p;
    Comment c;
    List<Comment> commentsForThisPost;

    HBox upVotedownVoteHbox;
    Button upvoteButton, downvoteButton;
    Hyperlink b;

    @FXML
    private VBox problemInfoContainer;
    private HBox hboxPost;
    @FXML
    private VBox vboxPost2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        Platform.runLater(() -> {

            c = new Comment();
            serviceComment = new ServiceComment();
            servicePost = new ServicePost();

            // we need to add a textfield for user to type a proposed solution to the post 
            commentsForThisPost = servicePost.findAllCommentsForThisPost(idPost);
            displayThisList(commentsForThisPost, serviceComment);

            animatePost(hboxPost);
            Text proposeASolutionLabel = new Text("Propose a solution ? ");
            TextArea proposedSolution = new TextArea();
            Button submitButton = new Button("Submit");

            HBox hboxUserSolution = new HBox();
            hboxUserSolution.getChildren().addAll(proposeASolutionLabel, proposedSolution, submitButton);

            submitButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    c.setSolution(proposedSolution.getText());
                    c.setOwner("Alolou");
                    c.setId_Post(idPost);
                    serviceComment.Create(c);
                    problemInfoContainer.getChildren().clear();
                    commentsForThisPost = servicePost.findAllCommentsForThisPost(idPost);
                    displayThisList(commentsForThisPost, serviceComment);
                }
            });

            simpleTransition(vboxPost2);
            vboxPost2.getChildren().addAll(hboxPost, hboxUserSolution);
            vboxPost2.setPadding(new Insets(5, 10, 10, 5));
            vboxPost2.setStyle("-fx-padding: 15; "
                    + "    -fx-spacing: 10; " + "-fx-border-style: solid inside;"
                    + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                    + "-fx-border-radius: 5;" + "-fx-border-color: black;");

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

        //HBox hboxButtons = new HBox();
        commentContainer.getChildren().addAll(hboxCommentOwner, hboxRating, hboxComment);
        problemInfoContainer.getChildren().addAll(commentContainer);

    }

    void initData(int id) {
        idPost = id;
    }

    void initData(int id, HBox postContainer) {
        idPost = id;

        hboxPost = postContainer;
        vboxPost2.setLayoutX(postContainer.getLayoutX());
        vboxPost2.setLayoutY(postContainer.getLayoutY());

    }

    void displayThisList(List<Comment> commentsForThisPost, ServiceComment sc) {

        for (Comment parcours : commentsForThisPost) {
            System.out.println(parcours);
            b = new Hyperlink();
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
            Text upvoteLabel = new Text("upvote");
            Text upvoteCounter = new Text("32");
            Text downvoteLabel = new Text("downvote");
            Text downvoteCounter = new Text("69");
            //upVoteVbox.setPrefHeight(50);
            upVoteVbox.getChildren().addAll(upvoteLabel, upvoteButton, upvoteCounter);
            //downVoteVbox.setPrefWidth(50);
            downVoteVbox.getChildren().addAll(downvoteLabel, downvoteButton, downvoteCounter);
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
                        downVoteVbox.getChildren().addAll(downvoteLabel, downvoteButton);
                        problemInfoContainer.getChildren().clear();
                        displayThisList(commentsForThisPost, serviceComment);
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
                        upVoteVbox.getChildren().addAll(upvoteLabel, upvoteButton);
                        upvoteButton.setGraphic(upvoteImage); // écraser l'anciene valeur par une nouvelle image 
                        problemInfoContainer.getChildren().clear();
                        displayThisList(commentsForThisPost, serviceComment);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(AddCommentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            // Partie 3.5 Hypertex Translation Solution text
            try {
                if (detectLanguage(parcours.getSolution()).equals("en")) {

                    b.setText("traduire en français.");
                    b.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {

                            try {
                                parcours.setSolution(GoogleTranslate.translate("fr", parcours.getSolution()));
                                problemInfoContainer.getChildren().clear();
                                displayThisList(commentsForThisPost, serviceComment);
                            } catch (IOException ex) {
                                Logger.getLogger(ViewPostsController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });

                }

                if (detectLanguage(parcours.getSolution()).equals("fr")) {
                    b.setText("translate to english.");
                    b.setOnAction((ActionEvent event) -> {
                        try {

                            parcours.setSolution(GoogleTranslate.translate("en", parcours.getSolution()));
                            problemInfoContainer.getChildren().clear();
                            displayThisList(commentsForThisPost, serviceComment);
                        } catch (IOException ex) {
                            Logger.getLogger(ViewPostsController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            VBox vboxCommentOwner = new VBox();

            vboxCommentOwner.getChildren().addAll(commentOwnerName); // 3maltelha HBox wa7adha psk mbaad newi nzid des infos okhrin bjanb el name kima specialite main mte3ou
            HBox hboxRating = new HBox();
            hboxRating.getChildren().addAll(ratingLabel, ratingText);
            VBox vboxComment = new VBox();
            vboxComment.getChildren().addAll(commentText);
            vboxComment.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                    + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                    + "-fx-border-radius: 5;" + "-fx-border-color: black;" + "-fx-background-color: white;");
            vboxComment.setPrefWidth(500);

            VBox vboxCommentAndTranslate = new VBox();
            vboxCommentAndTranslate.getChildren().addAll(vboxComment, b);

            HBox hboxButtons = new HBox();
            Button markAsSolutionButton = new Button("Mark as solution");
            hboxButtons.getChildren().add(markAsSolutionButton);

            markAsSolutionButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Alert confirmationSetAsSolution= new Alert(Alert.AlertType.CONFIRMATION);
                        confirmationSetAsSolution.setContentText("Are you sure you want to pass this command?");
                    confirmationSetAsSolution.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            System.out.println(c.getId()+"----"+c.getId_Post());
                            serviceComment.MarkAsSolution(idPost, parcours.getId());
                            System.out.println("cbn");
                           
                        } else {
                            System.out.println("command aborted.");
                        }
                    });
                    

                }
            });

            commentContainer.getChildren().addAll(vboxCommentOwner, hboxRating, vboxCommentAndTranslate, hboxButtons, upVotedownVoteHbox);
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
    }

    void animatePost(HBox v) {

        System.out.println(v.getLayoutY() + "------");
        TranslateTransition transition = new TranslateTransition();
        System.out.println("postContainer.getLayoutX()  " + v.getLayoutX() + "---postContainer.getLayoutY()  " + v.getLayoutX());
        //v.setLayoutX(hboxPost.getLayoutX());

        System.out.println(v.getLayoutY() + "------");

        transition.setDuration(Duration.seconds(3));
        //transition.setToX(hboxPost.getLayoutX());
        transition.setToY(10);

        //transition.setByY(-40);
        System.out.println(v.getLayoutX() + ".." + v.getLayoutY());
        transition.setNode(v);
        transition.play();

    }

    void simpleTransition(VBox v) {
        Circle cir = new Circle();
        cir.setFill(Color.AZURE);

        cir.setRadius(50);
        cir.relocate(50, 50);
        v.getChildren().add(cir);
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(3));
        transition.setCycleCount(TranslateTransition.INDEFINITE);
        transition.setToX(500);
        transition.setToY(500);
        transition.setNode(cir);
        transition.play();

    }

    public Node problemIsSolvedAnimation() {
        Node card = createCard();

        RotateTransition rotator = createRotator(card);
        rotator.play();
        return card;
    }

    public Node createCard() {
        Image u = new Image("https://www.nicepng.com/png/detail/75-759910_problem-solving-png-solve-problem-icon-png.png");

        ImageView i = new ImageView(u);

        return i;
    }

    public RotateTransition createRotator(Node card) {
        RotateTransition rotator = new RotateTransition(Duration.millis(10000), card);
        rotator.setAxis(Rotate.Y_AXIS);
        rotator.setFromAngle(0);
        rotator.setToAngle(360);
        rotator.setInterpolator(Interpolator.LINEAR);
        rotator.setCycleCount(10);

        return rotator;
    }

}
