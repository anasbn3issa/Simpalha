/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.post;

import entities.Comment;
import entities.Post;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.ServiceComment;
import services.ServicePost;

/**
 * FXML Controller class
 *
 * @author anaso
 */
public class AddCommentSortedByController implements Initializable {

    
    
    private int idPost;
    private ServicePost servicePost;
    private ServiceComment serviceComment;
    
    Text commentOwnerName, commentText, commentLabel, ratingText, ratingLabel, TimestampText;
    Post p;
    Comment c;
    List<Comment> commentsForThisPost;
    
    @FXML
    private HBox CopyOfPageHeader;
    @FXML
    private VBox problemInfoContainer;
    @FXML
    private TextArea problemText;
    @FXML
    private TextArea solutionText;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(() -> {
            serviceComment = new ServiceComment();
            //servicePost = new ServicePost();

            p = servicePost.findById(idPost);
            commentsForThisPost = servicePost.findAllCommentsForThisPostSortedBy("rating",idPost);
            commentsForThisPost.toString();
            c = new Comment();
            problemText.setText(p.getProblem());
            for (Comment parcours : commentsForThisPost) {
                HBox commentContainer = new HBox();

                commentOwnerName = new Text("LM3allem"); // hethy mbaad nrodha berasmi esm li 3mal l comment w na3ml findById w kol
                commentLabel = new Text("Proposed Solution : ");
                ratingLabel = new Text("rating");

                commentText = new Text(parcours.getSolution());
                ratingText = new Text(String.valueOf(parcours.getRating()));
                TimestampText = new Text(String.valueOf(parcours.getTimestamp()));

                HBox hboxCommentOwner = new HBox();
                hboxCommentOwner.getChildren().addAll(commentOwnerName); // 3maltelha HBox wa7adha psk mbaad newi nzid des infos okhrin bjanb el name kima specialite main mte3ou
                HBox hboxRating = new HBox();
                hboxRating.getChildren().addAll(ratingLabel, ratingText);
                HBox hboxComment = new HBox();
                hboxComment.getChildren().addAll(commentLabel, commentText);

                HBox hboxButtons = new HBox();
                Button RienAFaire = new Button("maya3ml shay");
                hboxButtons.getChildren().addAll(RienAFaire);

                commentContainer.getChildren().addAll(hboxCommentOwner, hboxRating, hboxComment, hboxButtons);
                problemInfoContainer.getChildren().addAll(commentContainer);
                problemInfoContainer.setMargin(commentContainer, new Insets(6, 6, 6, 6));
                commentContainer.setSpacing(15);
                commentContainer.setStyle("-fx-background-color: white;"); // mela bsh nkhaliwha maghir style ?? 
                commentContainer.setStyle("-fx-border-color: black;");
            }

        });
    }    

    void initData(int id)  // hethy mbaad bsh nzidha postownerid
    {
        System.out.println(id);
        idPost=id;
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

    @FXML
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

    @FXML
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
        hboxCommentOwner.getChildren().addAll(commentOwnerName); // 3maltelha HBox wa7adha psk mbaad newi nzid des infos okhrin bjanb el name kima specialite main mte3ou
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

    @FXML
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
    
}
