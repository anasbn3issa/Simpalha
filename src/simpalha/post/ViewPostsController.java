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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.ServicePost;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import utils.Maconnexion;

/**
 * FXML Controller class
 *
 * @author anaso
 */
public class ViewPostsController implements Initializable {
    
    @FXML
    private VBox PostsContainer; // eli bsh n7ot fih l posts lkol 

    ImageView postOwnerPhoto;
    Text postOwnerName;
    Text idText, problemText, moduleText, statusText, timestampText;
    Text timestampLabel, problemLabel, statusLabel, moduleLabel, idLabel;
    Hyperlink b;
    @FXML
    private ComboBox<String> comboSearch;
    @FXML
    private HBox firstHboxInPage;
    @FXML
    private VBox PostsContainer1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ServicePost cs = new ServicePost();
        List<Post> lc = cs.Read();

        // we add the search combobox 
        comboSearch.getItems().removeAll(comboSearch.getItems());
        
        comboSearch.getItems().addAll("IP Essentials", "Mathématique de base 1", "Mathématique de base 2", "Génie Logiciel", "All"); // mba3d nrodou marbout b classe specialité .
        comboSearch.getSelectionModel().select("Search by module"); // shnowa maktoub par défaut . 

        // search combobox on Action
        comboSearch.setOnAction(e -> {
            String s1 = comboSearch.getValue().trim();
            PostsContainer.getChildren().clear();
            System.out.println("-*-*-*-*-*-*" + comboSearch.getValue() + "*--*-*-*-*-*");
            
            if (s1.equals("All")) {
                try {
                    displayThisList(lc, cs);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ViewPostsController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                List<Post> listSearchedByModule = cs.findPostsByModule(s1);
                
                System.out.println("All posts searched by Module : " + listSearchedByModule.toString());
                
                try {
                    displayThisList(listSearchedByModule, cs);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ViewPostsController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        
        try {
            // we add all info related to posts
            displayThisList(lc, cs);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ViewPostsController.class.getName()).log(Level.SEVERE, null, ex);
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
    
    private String translateProblemToEnglish(Post p) throws IOException {
        System.out.println("p.get : " + p.getProblem() + " ---p.set: ");
        String s = GoogleTranslate.translate("en", p.getProblem());
        p.setProblem(s);
        
        System.out.println(p.getProblem());
        return p.getProblem();
    }
    
    private String translateProblemToFrench(Post p) throws IOException {
        System.out.println("p.get : " + p.getProblem() + " ---p.set: ");
        String s = GoogleTranslate.translate("fr", p.getProblem());
        p.setProblem(s);
        
        System.out.println(p.getProblem());
        
        return p.getProblem();
        
    }
    
    private void displayThisList(List<Post> lp, ServicePost cs) throws FileNotFoundException {
        for (Post p : lp) {
            
            System.out.println(p);
            b = new Hyperlink();
            
            HBox postContainer = new HBox();
            
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
            Alert alertDeletepushed = new Alert(Alert.AlertType.CONFIRMATION);
            
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
            vboxProblemAndName.getChildren().addAll(vboxName, vboxProblem, b);
            VBox vboxModuleAndTimestamp = new VBox();
            vboxModuleAndTimestamp.getChildren().addAll(timestampText, moduleText);
            
            HBox hboxButtons = new HBox();
            Button btnDelete = new Button("Delete");
            Button btnModify = new Button("Modify");
            Button btnAddComment = new Button("Add Comment");
            hboxButtons.getChildren().addAll(btnDelete, btnModify, btnAddComment);

            // if problem is solved : 
            HBox problemIsSolvedAnimationHbox = new HBox();            
            problemIsSolvedAnimationHbox.setPrefHeight(40);
            problemIsSolvedAnimationHbox.setPrefWidth(40);
            
            problemIsSolvedAnimationHbox.getChildren().add(problemIsSolvedAnimation());
            hboxButtons.getChildren().add(problemIsSolvedAnimationHbox);
            
            postContainer.getChildren().addAll(vboxOwnerPhoto, vboxProblemAndName, hboxButtons, vboxModuleAndTimestamp);
            postContainer.setSpacing(35);// hethy nzidha mba3d lbarsha Hboxouét bsh nba3dou razzebi men razzebi

            postContainer.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                    + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                    + "-fx-border-radius: 5;" + "-fx-border-color: black;");
            postContainer.setStyle("-fx-background-color: white;");
            postContainer.setStyle("-fx-border-color: black;");
            PostsContainer.getChildren().add(postContainer);
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
                                displayThisList(lp, cs);
                                
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
                                displayThisList(lp, cs);
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
                    //alertDeletepushed.setTitle("");
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
                        //stage.setCamera(new PerspectiveCamera());

                        hboxButtons.getChildren().remove(btnAddComment);
                        vboxProblemAndName.getChildren().remove(b);
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
    
    public HBox problemIsSolvedAnimation() throws FileNotFoundException {
        Node card = createCard();
        HBox h = new HBox();
        h.getChildren().add(card);
        h.setStyle("-fx-hover");
//        h.setStyle("-fx-min-width: 10 ;\n"
//                + "    -fx-max-width: 20 ;\n"
//                + "    -fx-min-height: 10 ;\n"
//                + "    -fx-max-height: 20 ;");
        RotateTransition rotator = createRotator(h);
        rotator.play();
        return h;
    }
    
    public Node createCard() throws FileNotFoundException {
        FileInputStream inputPhoto = new FileInputStream("C:\\Users\\anaso\\OneDrive\\Desktop\\Simpalha\\src\\simpalha\\post\\img\\solved.png");
        //Image u= new Image("https://icon-library.com/images/check-image-icon/check-image-icon-8.jpg"); 
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
    
    @FXML
    private void ExportExcelButtonPushed(ActionEvent event) {
        try {            
            
            Connection cnx = Maconnexion.getInstance().getConnection();
            PreparedStatement pst;
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
    
}
