/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.admin.quizz;

import entities.Question;
import java.io.IOException;
import services.ServiceQuestion;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import simpalha.FXMLDocumentController;

/**
 * FXML Controller class
 *
 * @author Parsath
 */
public class FXMLAdminQuestionTableController implements Initializable {

    @FXML
    private TableView<Question> LAffiche;
    @FXML
    private TableColumn<Question, String> questionColumn;
    @FXML
    private TableColumn<Question, Integer> answerColumn;
    @FXML
    private TableColumn<Question, Integer> idColumn;
    @FXML
    private Button addQuizzButton;
    
    private int addedQuizzId;

    /**
     * Initializes the controller class by loading the "Question" objects from the DB
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reloadQuestionsList(addedQuizzId);
    }    
    
//    Reusable function to reload the table
    public void reloadQuestionsList(int id){
        
        ServiceQuestion sq = new ServiceQuestion();
        
        try {
            LAffiche.setItems(sq.ObservableListQuestions(id));
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdminQuestionTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    Popup Modal for adding a question then reloads table (transmits informations to FXMLQuestionAdd through addInformation())
    @FXML
    private void addQuestion(ActionEvent event) throws Exception {
        
        Stage stage = new Stage();
        Parent root;
        
        FXMLLoader addQuestionModal = new FXMLLoader(getClass().getResource("/simpalha/admin/quizz/FXMLQuestionAdd.fxml"));
        root = addQuestionModal.load();
        
        stage = (Stage) addQuizzButton.getScene().getWindow();
        
        FXMLAdminQuestionAddController addQuestionControllerModal = addQuestionModal.getController();
        
        addQuestionControllerModal.addInformation(addedQuizzId);

        stage.setTitle("Add Question");
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.showAndWait();
        
        reloadQuestionsList(addedQuizzId);
    }

//    Popup Modal for modifying a question (reloads the table right after) (transmits informations to FXMLQuestionAdd through showInformation())
    @FXML
    private void editQuestion(ActionEvent event) throws Exception {
        Question editable = LAffiche.getSelectionModel().getSelectedItem();
        
        FXMLLoader modal = new FXMLLoader(getClass().getResource("/simpalha/admin/quizz/FXMLQuestionEdit.fxml"));
        Parent root = modal.load();
        
        FXMLAdminQuestionEditController editModal = modal.getController();
        
        editModal.showInformation(editable, addedQuizzId);
        
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Edit Question");
        stage.showAndWait();
        
        reloadQuestionsList(addedQuizzId);
    }

//    Deleting selected items and reloading the table
    @FXML
    private void deleteQuestion(ActionEvent event) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Look, a Confirmation Dialog");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            ServiceQuestion sq2 = new ServiceQuestion();

            ObservableList<Question> questionsSelected;
            questionsSelected = LAffiche.getSelectionModel().getSelectedItems();

            questionsSelected.forEach(e -> {
                sq2.Delete(e);
            });

            reloadQuestionsList(addedQuizzId);
        } else {
            // ... user chose CANCEL or closed the dialog
        }
        
    }

//    Confirms and closes the windows
    @FXML
    private void addQuizz(ActionEvent event) {
        
        Stage stage;
        Parent root;
        
        stage = (Stage) addQuizzButton.getScene().getWindow();
        stage.close();
        
    }

//    Initializes "addedQuizzId" and reloads the Questions table
    public void addInformation(int id){
        addedQuizzId = id;
        reloadQuestionsList(addedQuizzId);
    }
    
}
