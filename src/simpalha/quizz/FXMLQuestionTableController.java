/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.quizz;

import entities.Question;
import services.ServiceQuestion;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Parsath
 */
public class FXMLQuestionTableController implements Initializable {

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
//        reloadQuestionsList(3);
        reloadQuestionsList(addedQuizzId);
    }    
    
//    Reusable function to reload the table
    public void reloadQuestionsList(int id){
        
        ServiceQuestion sq = new ServiceQuestion();
        
        try {
            LAffiche.setItems(sq.ObservableListQuestions(id));
        } catch (SQLException ex) {
            Logger.getLogger(FXMLQuestionTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    Popup Modal for adding a question then reloads table
    @FXML
    private void addQuestion(ActionEvent event) throws Exception {
        
        Stage stage = new Stage();
        Parent root;
        
        FXMLLoader addQuestionModal = new FXMLLoader(getClass().getResource("FXMLQuestionAdd.fxml"));
        root = addQuestionModal.load();
        
        stage = (Stage) addQuizzButton.getScene().getWindow();
        
        FXMLQuestionAddController addQuestionControllerModal = addQuestionModal.getController();
        
        addQuestionControllerModal.addInformation(addedQuizzId);

        stage.setTitle("Add Question");
            
        
//        stage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.showAndWait();
        
//        reloadQuestionsList(3);
        reloadQuestionsList(addedQuizzId);
    }

//    Popup Modal for modifying a question (reloads the table right after)
    @FXML
    private void editQuestion(ActionEvent event) throws Exception {
        Question editable = LAffiche.getSelectionModel().getSelectedItem();
        
        FXMLLoader modal = new FXMLLoader(getClass().getResource("FXMLQuestionEdit.fxml"));
        Parent root = modal.load();
        
        FXMLQuestionEditController editModal = modal.getController();
        
        editModal.showInformation(editable, addedQuizzId);
        
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Edit Question");
        stage.showAndWait();
        
//        reloadQuestionsList(3);
        reloadQuestionsList(addedQuizzId);
    }

//    Deleting selected items and reloading the table
    @FXML
    private void deleteQuestion(ActionEvent event) throws Exception {
    
        ServiceQuestion sq2 = new ServiceQuestion();
        
        ObservableList<Question> questionsSelected;
        questionsSelected = LAffiche.getSelectionModel().getSelectedItems();
        
        questionsSelected.forEach(e -> {
            sq2.Delete(e);
        });
        
        
//        reloadQuestionsList(3);
        reloadQuestionsList(addedQuizzId);
        
    }

//    reloading the table through "reload" button
    private void reloadQuestions(ActionEvent event) {
        
//        reloadQuestionsList(3);
        reloadQuestionsList(addedQuizzId);
        
    }

    @FXML
    private void addQuizz(ActionEvent event) {
        
        Stage stage;
        Parent root;
        
        stage = (Stage) addQuizzButton.getScene().getWindow();
        stage.close();
        
    }
    
    public void addInformation(int id){
        addedQuizzId = id;
//        reloadQuestionsList(3);
        reloadQuestionsList(addedQuizzId);
    }

    @FXML
    private void showP2P(MouseEvent event) {
    }

    @FXML
    private void showQuizz(MouseEvent event) {
    }

    
}
