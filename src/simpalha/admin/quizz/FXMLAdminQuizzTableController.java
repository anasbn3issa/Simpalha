/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.admin.quizz;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import entities.Quizz;
import java.io.IOException;
import services.ServiceQuizz;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import simpalha.FXMLDocumentController;
import simpalha.notification.FXMLNotificationController;
import utils.UserSession;

/**
 * FXML Controller class
 *
 * @author Parsath
 */
public class FXMLAdminQuizzTableController implements Initializable {

    @FXML
    private TableView<Quizz> LAffiche;
    @FXML
    private TableColumn<Quizz, Integer> idColumn;
    @FXML
    private TableColumn<Quizz, String> quizzColumn;
    @FXML
    private TableColumn<Quizz, String> subjectColumn;
    @FXML
    private TableColumn<Quizz, Integer> helperIdColumn;
    @FXML
    private TableColumn<Quizz, String> helperNameColumn;
    
    private int userId;
    @FXML
    private FontAwesomeIcon btNotificationShow;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        reloadQuizzesList();
    }    
    
    public void initializeUserId(int uId){
        userId = uId;
        reloadQuizzesList();
    }
    
//    Reusable function to reload the table
    public void reloadQuizzesList(){
    
        ServiceQuizz sq = new ServiceQuizz();
        
        try {
            LAffiche.setItems(sq.ObservableListAllQuizzesWithUserName());
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdminQuizzTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    Opens the Quizz addition Modal (FXMLAdminQuizzAddController)
    @FXML
    private void addQuizz(ActionEvent event) throws Exception {
        
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/simpalha/admin/quizz/FXMLQuizzAdd.fxml"
                )
        );
        Parent modal = loader.load();
        
        FXMLAdminQuizzAddController addController = loader.getController();
        
        addController.initializeUser(userId);
        
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Quizz Creation Modal");
            
        Scene scene = new Scene(modal);
        stage.setScene(scene);
        stage.showAndWait();
        
        reloadQuizzesList();
    }

//    Opens the Quizz editing Modal and transmits the id of the Quizz to FXMLAdminQuizzEditController
    @FXML
    private void editQuizz(ActionEvent event) throws Exception {
        Quizz editable = LAffiche.getSelectionModel().getSelectedItem();
        
        editable.setHelper(userId);
        
        FXMLLoader modal = new FXMLLoader(getClass().getResource("/simpalha/admin/quizz/FXMLQuizzEdit.fxml"));
        Parent root = modal.load();
        
        FXMLAdminQuizzEditController editModal = modal.getController();
        
        editModal.showInformation(editable);
        
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Edit Question");
        stage.showAndWait();
        
        reloadQuizzesList();
    }

//    deletes a quizz by its ID
    @FXML
    private void deleteQuizz(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Look, a Confirmation Dialog");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            ServiceQuizz sq2 = new ServiceQuizz();
            ObservableList<Quizz> quizzesSelected;
            quizzesSelected = LAffiche.getSelectionModel().getSelectedItems();

            quizzesSelected.forEach(e -> {
                sq2.Delete(e);
            });


            reloadQuizzesList();
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    
        
    }

    @FXML
    private void showP2P(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/simpalha/admin/P2P/P2PFXML.fxml"
                    )
            );

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
            stage.setScene(
                    new Scene(loader.load())
            );
            stage.show();
        } 
        catch (IOException ex) {
            Logger.getLogger(FXMLAdminQuizzTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void showQuizz(MouseEvent event) {
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
    private void showPosts(MouseEvent event) {
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("/simpalha/admin/post/ViewPosts.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
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
