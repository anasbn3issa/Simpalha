/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.quizz;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import entities.Quizz;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.ServiceQuizz;
import simpalha.FXMLDocumentController;
import simpalha.notification.FXMLNotificationController;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.scene.input.ContextMenuEvent;
import utils.UserSession;

/**
 * FXML Controller class
 *
 * @author Parsath
 */
public class FXMLQuizzTakingController implements Initializable {

    @FXML
    private TableView<Quizz> LAffiche;
    @FXML
    private TableColumn<Quizz, String> quizzColumn;
    @FXML
    private TableColumn<Quizz, String> userNameColumn;
    @FXML
    private TableColumn<Quizz, String> subjectColumn;
    @FXML
    private Button btTakeQuizz;
    
    
    private int userId;
    @FXML
    private FontAwesomeIcon btNotificationShow;
    @FXML
    private FontAwesomeIcon btSearchQuizzBody;
    @FXML
    private Button btReloadQuizzes;
    @FXML
    private TextField tfSearchQuizz;
    
    /**
     * Initializes the controller class and reloads the Quiz table
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reloadQuizzesList();
    }    
    
    public void initializeUserId(int uid){
        userId = uid;
    }
    
//    Reusable function to reload the Quiz table
    public void reloadQuizzesList(){
    
        ServiceQuizz sq = new ServiceQuizz();
        
        try {
            LAffiche.setItems(sq.ObservableListAllQuizzesWithUserName());
        } catch (SQLException ex) {
            Logger.getLogger(FXMLQuizzTakingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    sends id of the selected Quizz to the FXMLQuizzEvalController and opens the modal for passing the exam
    @FXML
    private void takeQuizz(ActionEvent event) throws Exception {
        Quizz editable = LAffiche.getSelectionModel().getSelectedItem();
        
        FXMLLoader modal = new FXMLLoader(getClass().getResource("FXMLQuizzEval.fxml"));
        Parent root = modal.load();
        
        
        FXMLQuizzEvalController editModal = modal.getController();
        
        System.out.println(userId);
        editModal.showInformationEval(editable.getId(),userId,editable.getHelper());
        
        Stage stage;
        stage = (Stage) btTakeQuizz.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Quizz");
        stage.show();
    }

    @FXML
    private void notificationsShow(MouseEvent event) {
        FXMLLoader modal = new FXMLLoader(getClass().getResource("/simpalha/notification/FXMLNotification.fxml"));
        Parent root = null;
        try{
            root = modal.load();
        }
        catch(IOException io){};

        FXMLNotificationController editModal = modal.getController();


        editModal.reloadAllNotificationsList(userId);

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Notifications");
        stage.showAndWait();
    }

    @FXML
    private void showP2P(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/simpalha/P2P/P2PFXML.fxml"
                    )
            );

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
            stage.setScene(
                    new Scene(loader.load())
            );
            stage.show();
        } 
        catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void showQuizz(MouseEvent event) {
    }

    private void showDashboard(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/simpalha/FXMLDocument.fxml"
                    )
            );

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
            stage.setScene(
                    new Scene(loader.load())
            );
            stage.show();
        } 
        catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void showPost(MouseEvent event) {
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("/simpalha/post/ViewPosts.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }

    @FXML
    private void searchQuizzTitle(MouseEvent event) {
        String quizzTitle = tfSearchQuizz.getText();
        
        List targetList = LAffiche.getItems().stream()
                            .filter(item -> item.getTitle().equalsIgnoreCase(quizzTitle))
                            .collect(Collectors.toList()); 
        
        LAffiche.setItems(FXCollections.observableArrayList(targetList));
    }

    @FXML
    private void reloadQuizzes(ActionEvent event) {
        reloadQuizzesList();
    }

    @FXML
    private void showRESOURCES(MouseEvent event) {
        //note that on this line you can substitue "Screen2.fxml" for a string chosen prior to this line.
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("/simpalha/ressources/FXMLDocument.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }

    @FXML
    private void showRec(ContextMenuEvent event) {
        //note that on this line you can substitue "Screen2.fxml" for a string chosen prior to this line.
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("/simpalha/reclamation/ListerReclamation.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }

    @FXML
    private void showProfile(MouseEvent event) {
        //note that on this line you can substitue "Screen2.fxml" for a string chosen prior to this line.
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("/simpalha/users/Profile.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }

    @FXML
    private void logout(MouseEvent event) {

        UserSession.getInstace(0).cleanUserSession();
        //note that on this line you can substitue "Screen2.fxml" for a string chosen prior to this line.
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("/simpalha/users/Login.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }
    
}
