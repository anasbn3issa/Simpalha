/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.admin.post;

import com.darkprograms.speech.translator.GoogleTranslate;
import static com.darkprograms.speech.translator.GoogleTranslate.detectLanguage;
import entities.Comment;
import entities.DownvoteComment;
import entities.Post;
import entities.UpvoteComment;
import entities.Users;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
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
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import services.ServiceComment;
import services.ServiceDownvoteComment;
import services.ServicePost;
import services.ServiceUpvoteComment;
import services.ServiceUsers;
import utils.UserSession;

/**
 * FXML Controller class
 *
 * @author anaso
 */
public class AddCommentController implements Initializable {

    @FXML
    private Text currentUserNameLabel;
    @FXML
    private Hyperlink exportToExcel;
    @FXML
    private VBox vboxPost2;
    @FXML
    private VBox problemInfoContainer;
    @FXML
    private ImageView imagePost;
    @FXML
    private HBox imageHbox;

    private int idPost;
    Text commentOwnerName, commentText, commentLabel, TimestampText;
    Post p;
    Comment c;
    List<Comment> commentsForThisPost;
    private HBox hboxPost;

    int userId;
    ServicePost servicePost;
    ServiceUsers serviceUsers;
    ServiceComment serviceComment;
    ServiceUpvoteComment serviceUpvoteComment;
    ServiceDownvoteComment serviceDownvoteComment;
    UserSession userSession;
    Users currentUser;
    Post thisPost;
    Comment solutionForThisPost;
    Hyperlink b;
    String dir;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        Platform.runLater(() -> {

            dir = System.getProperty("user.dir");//get project source path
            c = new Comment();
            serviceComment = new ServiceComment();
            servicePost = new ServicePost();
            serviceUsers = new ServiceUsers();
            serviceUpvoteComment = new ServiceUpvoteComment();
            serviceDownvoteComment = new ServiceDownvoteComment();
            userSession = UserSession.getInstace(0);
            userId = userSession.getUserid();
            currentUser = serviceUsers.findById(userId);
            thisPost = servicePost.findById(idPost);
            solutionForThisPost = serviceComment.findById(thisPost.getSolution_id());

            commentsForThisPost = servicePost.findAllCommentsForThisPost(idPost);
            String postImagePath = dir + "\\ressources\\" + thisPost.getImageName();
            System.out.println(postImagePath);
            try {
                createImageView(postImagePath);

            } catch (FileNotFoundException ex) {
                Logger.getLogger(simpalha.post.AddCommentController.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (thisPost.getSolution_id() != -1 && thisPost.getSolution_id() != 0) {
                exportToExcel.setVisible(true);
                exportToExcel.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        int counter = 1;
                        FileOutputStream fileOut = null;

                        //Creation of New Work Book in Excel and sheet.  
                        HSSFWorkbook hwb = new HSSFWorkbook();
                        HSSFSheet sheet = hwb.createSheet("new sheet");
                        //Creating Headings in Excel sheet.  
                        HSSFRow rowhead = sheet.createRow((short) 0);
                        rowhead.createCell((short) 1).setCellValue("problem");//Row Name1  
                        rowhead.createCell((short) 2).setCellValue("module");//Row Name3  
                        rowhead.createCell((short) 3).setCellValue("solution");//Row Name4
                        rowhead.createCell((short) 4).setCellValue("upvotes");//Row Name5

                        HSSFRow row = sheet.createRow((int) counter);
                        System.out.println(thisPost.getSolution_id() + "<--sol id");
                        
                        System.out.println("solution :" + solutionForThisPost.toString());

                        row.createCell((short) 1).setCellValue(thisPost.getProblem());
                        row.createCell((short) 2).setCellValue(thisPost.getModule());
                        row.createCell((short) 3).setCellValue(solutionForThisPost.getSolution());
                        row.createCell((short) 4).setCellValue(solutionForThisPost.getUpvotes());

                        sheet.autoSizeColumn(1);
                        sheet.autoSizeColumn(2);
                        sheet.setColumnWidth(3, 256 * 25);

                        sheet.setZoom(150);
                        sheet.autoSizeColumn(1);
                        sheet.autoSizeColumn(2);
                        sheet.setColumnWidth(3, 256 * 25);

                        sheet.setZoom(150);

                        counter++;
                        try {
                            //For performing write to Excel file  
                            fileOut = new FileOutputStream("SolvedProblem.xls");
                            hwb.write(fileOut);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        try {
                            fileOut.close();
                        } catch (IOException ex) {
                            Logger.getLogger(simpalha.post.AddCommentController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("file created");
                        alert.setHeaderText(null);
                        alert.setContentText("problem solved have been exported in Excel Sheet.");
                        alert.showAndWait();

                    }
                });

            } else {
                exportToExcel.setVisible(false);
            }

            try {
                displayThisList(commentsForThisPost, serviceComment);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(simpalha.post.AddCommentController.class.getName()).log(Level.SEVERE, null, ex);
            }

            //animatePost(hboxPost);
            vboxPost2.getChildren().addAll(hboxPost);

            HBox hboxUserSolution = new HBox();

            Text proposeASolutionLabel = new Text("Propose a solution ? ");
            TextArea proposedSolution = new TextArea();
            Button submitButton = new Button("Submit");

            hboxUserSolution.getChildren().addAll(proposeASolutionLabel, proposedSolution, submitButton);
            vboxPost2.getChildren().addAll(hboxUserSolution);
            submitButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    c.setSolution(proposedSolution.getText());
                    c.setOwnerId(userId);
                    c.setId_Post(idPost);
                    serviceComment.Create(c);
                    proposedSolution.setText("");
                    problemInfoContainer.getChildren().clear();
                    commentsForThisPost = servicePost.findAllCommentsForThisPost(idPost);
                    try {
                        displayThisList(commentsForThisPost, serviceComment);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(simpalha.post.AddCommentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            //simpleTransition(vboxPost2);
            vboxPost2.setPadding(new Insets(5, 10, 10, 5));
            vboxPost2.setStyle("-fx-padding: 15; "
                    + "    -fx-spacing: 10; " + "-fx-border-style: solid inside;"
                    + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                    + "-fx-border-radius: 5;" + "-fx-border-color: black;");

        });
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
    void initData(int id) {
        idPost = id;
    }
    void initData(int id, HBox postContainer) {
        idPost = id;

        hboxPost = postContainer;
        vboxPost2.setLayoutX(postContainer.getLayoutX());
        vboxPost2.setLayoutY(postContainer.getLayoutY());

    }
    public void createImageView(String path) throws FileNotFoundException {
        FileInputStream post = new FileInputStream(path);
        Image u = new Image(post);
        imagePost.setImage(u);
    }
     void displayThisList(List<Comment> commentsForThisPost, ServiceComment sc) throws FileNotFoundException {

        for (Comment parcours : commentsForThisPost) {
            b = new Hyperlink();
            Users commentOwnerEnPersonne = new Users();
            commentOwnerEnPersonne = serviceUsers.findById(parcours.getOwnerId());
            HBox commentContainer = new HBox();
            commentOwnerName = new Text(commentOwnerEnPersonne.getUsername());
            commentOwnerName.setStyle("-fx-fill: linear-gradient(from 0% 0% to 100% 200%, repeat, aqua 0%, red 50%);\n" + " -fx-stroke: black;\n" + " -fx-stroke-width: 1;");

            commentLabel = new Text("Proposed Solution : ");
            commentText = new Text(parcours.getSolution());
            TimestampText = new Text(String.valueOf(parcours.getTimestamp()));
 

             

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
                                Logger.getLogger(simpalha.admin.post.ViewPostsController.class.getName()).log(Level.SEVERE, null, ex);
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
                            Logger.getLogger(simpalha.admin.post.ViewPostsController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            VBox vboxCommentOwner = new VBox();

            vboxCommentOwner.getChildren().addAll(commentOwnerName); // 3maltelha HBox wa7adha psk mbaad newi nzid des infos okhrin bjanb el name kima specialite main mte3ou
            VBox vboxComment = new VBox();
            vboxComment.getChildren().addAll(commentText);
            vboxComment.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                    + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                    + "-fx-border-radius: 5;" + "-fx-border-color: black;" + "-fx-background-color: white;");
            vboxComment.setPrefWidth(500);

            VBox vboxCommentAndTranslate = new VBox();
            vboxCommentAndTranslate.getChildren().addAll(vboxComment, b);

            HBox hboxButtons = new HBox();
            Button markAsSolutionButton = null;
            Button deleteComment = null;
            // if (userId == p.getOwnerId())   mbaad nwali n'affichi l bouton hetha ken lel PostOwner.
            if (thisPost.getSolution_id() == -1) {
                // markAsSolutionButton = new Button("Mark as solution");
                markAsSolutionButton = createGraphicButton(dir + "\\src\\simpalha\\post\\img\\greenTick.png");
                hboxButtons.getChildren().add(markAsSolutionButton);

                markAsSolutionButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Alert confirmationSetAsSolution = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmationSetAsSolution.setContentText("Are you sure this comment has resolved your problem?");
                        confirmationSetAsSolution.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.OK) {
                                serviceComment.MarkAsSolution(idPost, parcours.getId());
                                Parent loader;
                                try {
                                    loader = FXMLLoader.load(getClass().getResource("ViewPosts.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

                                    Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

                                    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

                                    app_stage.setScene(scene); //This sets the scene as scene

                                    app_stage.show(); // this shows the scene
                                } catch (IOException ex) {
                                }
                            } else {
                                System.out.println("command aborted.");
                            }
                        });

                    }
                });
            }

            Alert alertDeletepushed = new Alert(Alert.AlertType.CONFIRMATION);
            if (parcours.getOwnerId() == userId) {
                deleteComment = createGraphicButton(dir + "\\src\\simpalha\\post\\img\\deleteImg.png");
                hboxButtons.getChildren().add(deleteComment);
                deleteComment.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        alertDeletepushed.setContentText("Are you sure you want to delete this Post?");
                        alertDeletepushed.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.OK) {
                                serviceComment.Delete(parcours);
                                problemInfoContainer.getChildren().remove(commentContainer);

                            } else {
                                System.out.println("delete aborted.");
                            }
                        });
                    }
                });

            }

            if (thisPost.getSolution_id() == parcours.getId()) {
                HBox animationSolved = new HBox();
                animationSolved.setPrefHeight(40);
                animationSolved.setPrefWidth(40);
                animationSolved.getChildren().add(problemIsSolvedAnimation());
                hboxButtons.getChildren().add(animationSolved);

            }
            commentContainer.getChildren().addAll(vboxCommentOwner, vboxCommentAndTranslate, hboxButtons);
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
     
     
     public Button createGraphicButton(String path) throws FileNotFoundException {
        FileInputStream fi = new FileInputStream(path);
        Button b = new Button();
        Image u = new Image(fi);
        ImageView i = new ImageView(u);
        b.setGraphic(i);
        return b;
    }
     public HBox problemIsSolvedAnimation() throws FileNotFoundException {
        Node card = createCard();
        HBox h = new HBox();
        h.getChildren().add(card);
        RotateTransition rotator = createRotator(h);
        rotator.play();
        return h;
    }

     
     public Node createCard() throws FileNotFoundException {
        String dir = System.getProperty("user.dir");//get project source path
        FileInputStream inputPhoto = new FileInputStream(dir + "\\src\\simpalha\\post\\img\\solved.png");
        Image u = new Image(inputPhoto);
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
