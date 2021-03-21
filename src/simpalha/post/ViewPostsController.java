/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.post;

import com.darkprograms.speech.translator.GoogleTranslate;
import static com.darkprograms.speech.translator.GoogleTranslate.detectLanguage;
import entities.Post;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    @FXML
    private VBox PostsContainer; // eli bsh n7ot fih l posts lkol 

    @FXML
    private HBox firstHboxInPage;

    ImageView postOwnerPhoto;
    Text postOwnerName;
    Text idText, problemText, moduleText, statusText, timestampText;
    Text timestampLabel, problemLabel, statusLabel, moduleLabel, idLabel;
    Hyperlink b;
    @FXML
    private ComboBox<String> comboSearch;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ServicePost cs = new ServicePost();
        List<Post> lc = cs.Read();

        // we add the search combobox 
        comboSearch.getItems().removeAll(comboSearch.getItems());
        comboSearch.getItems().addAll("IP Essentials", "Mathématique de base 1", "Mathématique de base 2", "Génie Logiciel"); // mba3d nrodou marbout b classe specialité .
        comboSearch.getSelectionModel().select("Search by module"); // shnowa maktoub par défaut . 
        System.out.println("-*-*-*-*-*-*" + comboSearch.getValue() + "*--*-*-*-*-*");

        // search combobox on Action
        comboSearch.setOnAction(e -> {
            String s1 = comboSearch.getValue().trim();
            PostsContainer.getChildren().clear();
            System.out.println("-*-*-*-*-*-*" + comboSearch.getValue() + "*--*-*-*-*-*");
            List<Post> listSearchedByModule = cs.findPostsByModule(s1);

            System.out.println("All posts searched by Module : " + listSearchedByModule.toString());

            displayThisList(listSearchedByModule, cs);

        });

        // we add all info related to posts 
        displayThisList(lc, cs);

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

    private String translateProblemToEnglish(Post p) throws IOException {
        System.out.println("p.get : "+p.getProblem()+" ---p.set: ");
        String s=GoogleTranslate.translate("en", p.getProblem());
        p.setProblem(s);
        
        System.out.println(p.getProblem());
        return p.getProblem();
    }

    private String translateProblemToFrench(Post p) throws IOException {
        System.out.println("p.get : "+p.getProblem()+" ---p.set: ");
        String s=GoogleTranslate.translate("fr", p.getProblem());
        p.setProblem(s);
        
        System.out.println(p.getProblem());
        
        return p.getProblem();

    }

    private void displayThisList(List<Post> lp, ServicePost cs) {
        for (Post p : lp) {

            System.out.println(p);
           b = new Hyperlink();

            HBox postContainer = new HBox();

            postContainer.setPrefWidth(firstHboxInPage.getPrefWidth());
            postContainer.setPrefHeight(firstHboxInPage.getPrefHeight());

            moduleLabel = new Text("module");
            statusLabel = new Text("status");
            timestampLabel = new Text("timestamp");

            problemText = new Text(p.getProblem());
            moduleText = new Text(p.getModule());

            timestampText = new Text(String.valueOf(p.getTimestamp()));
            postOwnerName = new Text("post owner name");
            postOwnerName.setStyle("-fx-fill: linear-gradient(from 0% 0% to 100% 200%, repeat, aqua 0%, red 50%);\n"
                    + "    -fx-stroke: black;\n"
                    + "    -fx-stroke-width: 1;");

            // postOwnerPhoto Load
            try {
                System.out.println(System.getProperty("user.dir"));
                FileInputStream inputOwnerPhoto = new FileInputStream("C:\\Users\\anaso\\OneDrive\\Desktop\\Simpalha\\src\\simpalha\\post\\img\\einstein.jpg");
                Image image = new Image(inputOwnerPhoto);
                postOwnerPhoto = new ImageView(image);
                //Setting the position of the image 
                postOwnerPhoto.setX(50);
                postOwnerPhoto.setY(25);

                //setting the fit height and width of the image view 
                postOwnerPhoto.setFitHeight(43);
                postOwnerPhoto.setFitWidth(49);

                //Setting the preserve ratio of the image view 
                postOwnerPhoto.setPreserveRatio(true);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ViewPostsController.class.getName()).log(Level.SEVERE, null, ex);
            }

            
            //postOwnerPhoto= new ImageView(getClass().getClassLoader().getResourceAsStream(("..\img\einstein.jpg"),true));
            Alert alertDeletepushed = new Alert(Alert.AlertType.WARNING);

            VBox vboxOwnerPhoto = new VBox();
            vboxOwnerPhoto.setPadding(new Insets(15, 5, 15, 5));
            vboxOwnerPhoto.getChildren().addAll(postOwnerPhoto);
            VBox vboxProblem = new VBox();
            vboxProblem.getChildren().addAll(problemText);

            vboxProblem.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                    + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                    + "-fx-border-radius: 5;" + "-fx-border-color: black;" + "-fx-background-color: white;");
            vboxProblem.setPrefWidth(500);

            VBox vboxName = new VBox();
            vboxName.getChildren().add(postOwnerName);

            VBox vboxProblemAndName = new VBox();
            vboxProblemAndName.getChildren().addAll(vboxName, vboxProblem,b); 
            VBox vboxModuleAndTimestamp = new VBox();
            vboxModuleAndTimestamp.getChildren().addAll(timestampText, moduleText);

            HBox hboxButtons = new HBox();
            Button btnDelete = new Button("Delete");
            Button btnModify = new Button("Modify");
            Button btnAddComment = new Button("Add Comment");
            hboxButtons.getChildren().addAll(btnDelete, btnModify, btnAddComment);

            postContainer.getChildren().addAll(vboxOwnerPhoto, vboxProblemAndName, hboxButtons, vboxModuleAndTimestamp);
            postContainer.setSpacing(35);// hethy nzidha mba3d lbarsha Hboxouét bsh nba3dou razzebi men razzebi

            postContainer.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                    + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                    + "-fx-border-radius: 5;" + "-fx-border-color: black;");
            postContainer.setStyle("-fx-background-color: white;");
            postContainer.setStyle("-fx-border-color: black;");
            PostsContainer.getChildren().addAll(postContainer);
            PostsContainer.setMargin(postContainer, new Insets(6, 6, 6, 6));

            
            /* now we are going to check wheather
            * our problemText is in english or french
            *
            *---- This is my algorithm ----------------------------------
            * if [ problemText in ENGLISH ]
            *   Button translateButton=button(displayed : FRENCH)
            * else if [ problemText in FRENCH]
            *   Button  translateButton=button(displayed : ENGLISH )
            *-------------------------------------------------------------
            *
             */
            
            try {
                if (detectLanguage(p.getProblem()).equals("en")) {

                    b.setText("traduire en français.");
                    b.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                
                                p.setProblem(GoogleTranslate.translate("fr", p.getProblem()));
                                PostsContainer.getChildren().clear();
                                displayThisList(lp,cs);
                            } catch (IOException ex) {
                                Logger.getLogger(ViewPostsController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });

                }
                
                if (detectLanguage(p.getProblem()).equals("fr")) {
                    b.setText("translate to english.");
                    b.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                
                                p.setProblem(GoogleTranslate.translate("en", p.getProblem()));
                                PostsContainer.getChildren().clear();
                                displayThisList(lp,cs);
                            } catch (IOException ex) {
                                Logger.getLogger(ViewPostsController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                } 
                
                
            } catch (IOException e) {
                e.printStackTrace();
            }

            
            
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
                        controller.initData(p.getId(), postContainer);

                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(AddCommentController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });

        }

    }

}
