/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.admin.post;

import com.darkprograms.speech.translator.GoogleTranslate;
import static com.darkprograms.speech.translator.GoogleTranslate.detectLanguage;
import entities.Post;
import entities.Users;
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
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
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
import services.ServicePost;
import services.ServiceUsers;
import simpalha.admin.post.AddCommentController;
import utils.Maconnexion;
import utils.UserSession;

/**
 * FXML Controller class
 *
 * @author anaso
 */
public class ViewPostsController implements Initializable {

    @FXML
    private Text currentUserNameLabel;
    @FXML
    private Hyperlink exportExcelHyperlink;
    @FXML
    private ComboBox<String> comboSearch;
    @FXML
    private HBox firstHboxInPage;
    @FXML
    private VBox PostsContainer1;
    @FXML
    private VBox PostsContainer;
    @FXML
    private VBox adVbox;
    List<String> modules;
    List<Post> listSearchedByModule ;
    Text postOwnerName;
    Text problemText, moduleText, timestampText;
    Text timestampLabel, problemLabel, moduleLabel;
    Hyperlink b,viewPhotoHyperlink;
    private int userId;
    ServiceUsers serviceUsers;
    ServicePost servicePost;
    UserSession userSession;
    Users currentUser;
    String dir;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(() -> {

            dir = System.getProperty("user.dir");//get project source path
            userSession = UserSession.getInstace(0);
            userId = userSession.getUserid();
            modules=servicePost.ReadModules();
            
            servicePost = new ServicePost();
            serviceUsers= new ServiceUsers();
            
            List<Post> lc = servicePost.Read();
            currentUser = serviceUsers.findById(userId);
            currentUserNameLabel.setText(currentUser.getUsername());
            // if user.role is simple user exportExcelHyperlink.setVisible(false); 
            //else if admin setVisible(true)
            comboSearch.getItems().removeAll(comboSearch.getItems());
            comboSearch.getItems().add("All");
            comboSearch.getItems().addAll(modules); 

            // search combobox on Action
            comboSearch.setOnAction(e -> {
                String s1 = comboSearch.getValue().trim();
                PostsContainer.getChildren().clear();
                System.out.println("-*-*-*-*-*-*" + comboSearch.getValue() + "*--*-*-*-*-*");

                if (s1.equals("All")) {
                    try {
                        displayThisList(lc, servicePost);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(ViewPostsController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (!s1.isEmpty()){
                    listSearchedByModule = servicePost.findPostsByModule(s1);

                    System.out.println("All posts searched by Module : " + listSearchedByModule.toString());

                    try {
                        displayThisList(listSearchedByModule, servicePost);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(ViewPostsController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            });

            try {
                // we add all info related to posts
                displayThisList(lc, servicePost);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ViewPostsController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
    }    

    @FXML
    private void goToViewPosts(MouseEvent event) {
    }

    @FXML
    private void ExportExcelButtonPushed(ActionEvent event) {
        try {

            Connection cnx = Maconnexion.getInstance().getConnection();
            PreparedStatement pst ;
            ResultSet rs;

            String query = "Select * from post";
            pst = cnx.prepareStatement(query);
            rs = pst.executeQuery();
            
            
            //Variable counter for keeping track of number of rows inserted.  
            int counter = 1;
            FileOutputStream fileOut = null;

            //Creation of New Work Book in Excel and sheet.  
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("new sheet");
            //Creating Headings in Excel sheet.  
            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell((short) 1).setCellValue("timestamp");//Row Name1  
            rowhead.createCell((short) 2).setCellValue("status");//Row Name2  
            rowhead.createCell((short) 3).setCellValue("module");//Row Name3  
            rowhead.createCell((short) 4).setCellValue("problem");//Row Name4

            while (rs.next()) {
                //Insertion in corresponding row  
               
            
                HSSFRow row = sheet.createRow((int) counter);

                row.createCell((short) 1).setCellValue(rs.getInt("timestamp"));
                row.createCell((short) 2).setCellValue(rs.getString("status"));
                row.createCell((short) 3).setCellValue(rs.getString("module"));
                row.createCell((short) 4).setCellValue(rs.getString("problem"));

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
                    fileOut = new FileOutputStream("posts.xls");
                    hwb.write(fileOut);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //Close all the parameters once writing to excel is compelte.  
            fileOut.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("file created");
            alert.setHeaderText(null);
            alert.setContentText("All posts have been exported in Excel Sheet.");
            alert.showAndWait();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    @FXML
    private void AddNewPost(ActionEvent event) {
    }
    
    
    private void displayThisList(List<Post> lp, ServicePost cs) throws FileNotFoundException {
        for (Post p : lp) {

            System.out.println(p);
            b = new Hyperlink();

            Users postOwnerEnPersonne = new Users();
            postOwnerEnPersonne = serviceUsers.findById(p.getOwnerId());
            HBox postContainer = new HBox();

            moduleLabel = new Text("module");
            timestampLabel = new Text("timestamp");

            problemText = new Text(p.getProblem());
            moduleText = new Text(p.getModule());

            timestampText = new Text(String.valueOf(p.getTimestamp()));
            postOwnerName = new Text("post owner name");
            postOwnerName.setText(postOwnerEnPersonne.getUsername());
            postOwnerName.setStyle("-fx-fill: linear-gradient(from 0% 0% to 100% 200%, repeat, aqua 0%, red 50%);\n"
                    + "    -fx-stroke: black;\n"
                    + "    -fx-stroke-width: 1;");

            //postOwnerPhoto= new ImageView(getClass().getClassLoader().getResourceAsStream(("..\img\einstein.jpg"),true));
            Alert alertDeletepushed = new Alert(Alert.AlertType.CONFIRMATION);

            VBox vboxProblem = new VBox();
            vboxProblem.getChildren().addAll(problemText);

            vboxProblem.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                    + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                    + "-fx-border-radius: 5;" + "-fx-border-color: black;" + "-fx-background-color: white;");
            vboxProblem.setPrefWidth(500);

            VBox vboxName = new VBox();
            vboxName.getChildren().add(postOwnerName);

            VBox vboxProblemAndName = new VBox();
            vboxProblemAndName.getChildren().addAll(vboxName, vboxProblem, b);
            VBox vboxModuleAndTimestamp = new VBox();
            vboxModuleAndTimestamp.getChildren().addAll(timestampText, moduleText);

            HBox hboxButtons = new HBox();
            Button btnDelete = null;
            
            if (p.getOwnerId() == userId) {
                btnDelete = createGraphicButton(dir + "\\src\\simpalha\\post\\img\\deleteImg.png");
                
                hboxButtons.getChildren().addAll(btnDelete);

                btnDelete.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        alertDeletepushed.setContentText("Are you sure you want to delete this Post?");
                        alertDeletepushed.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.OK) {
                                cs.Delete(p);
                                PostsContainer.getChildren().remove(postContainer);

                            } else {
                                System.out.println("delete aborted.");
                            }
                        });
                    }
                });
                
            }
            Button btnAddComment = createGraphicButton(dir + "\\src\\simpalha\\post\\img\\showImg.png");
            hboxButtons.getChildren().add(btnAddComment);

            // if problem is solved : 
            if (p.getSolution_id() != -1 && p.getSolution_id() != 0) {
                HBox problemIsSolvedAnimationHbox = new HBox();
                problemIsSolvedAnimationHbox.setPrefHeight(40);
                problemIsSolvedAnimationHbox.setPrefWidth(40);

                problemIsSolvedAnimationHbox.getChildren().add(problemIsSolvedAnimation());
                hboxButtons.getChildren().add(problemIsSolvedAnimationHbox);
            }
            postContainer.getChildren().addAll(vboxProblemAndName, hboxButtons, vboxModuleAndTimestamp);
            postContainer.setSpacing(35);
            postContainer.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                    + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                    + "-fx-border-radius: 5;" + "-fx-border-color: black;");
            postContainer.setStyle("-fx-background-color: white;");
            postContainer.setStyle("-fx-border-color: black;");
            PostsContainer.getChildren().add(postContainer);
            PostsContainer.setMargin(postContainer, new Insets(6, 6, 6, 6));

            /* now we are going to check whether
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
                                displayThisList(lp, cs);

                            } catch (IOException ex) {
                                Logger.getLogger(simpalha.post.ViewPostsController.class.getName()).log(Level.SEVERE, null, ex);
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
                                displayThisList(lp, cs);
                            } catch (IOException ex) {
                                Logger.getLogger(simpalha.post.ViewPostsController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            // if (p.getOwnerId() == userId && p.getStatus().equals("OPEN")) {
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
                        hboxButtons.getChildren().removeAll(btnAddComment);
                        vboxProblemAndName.getChildren().remove(b);
                        
                        AddCommentController controller = loader.getController();
                        controller.initData(p.getId(), postContainer);

                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(simpalha.post.ViewPostsController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });

        }

    }
    
    
    
     public Button createGraphicButton(String path) throws FileNotFoundException{
        FileInputStream fi= new FileInputStream(path);
        Button b= new Button();
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
        FileInputStream inputPhoto = new FileInputStream(dir+"\\src\\simpalha\\post\\img\\solved.png");
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
