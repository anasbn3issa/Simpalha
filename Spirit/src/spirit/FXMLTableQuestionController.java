/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spirit;

import Entities.Question;
import Service.ServiceQuestion;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Parsath
 */
public class FXMLTableQuestionController implements Initializable {

    @FXML
    private TableView<Question> LAffiche;
    @FXML
    private TableColumn<Question, String> questionColumn;
    @FXML
    private TableColumn<Question, Integer> answerColumn;
    @FXML
    private TableColumn<Question, Integer> idColumn;

    /**
     * Initializes the controller class by loading the "Question" objects from the DB
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reloadQuestionsList();
    }    
    
//    Reusable function to reload the table
    public void reloadQuestionsList(){
    
        ServiceQuestion sq = new ServiceQuestion();
        
        try {
            LAffiche.setItems(sq.ObservableListQuestions());
        } catch (SQLException ex) {
            Logger.getLogger(FXMLTableQuestionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    Popup Modal for adding a question then reloads table
    @FXML
    private void addQuestion(ActionEvent event) throws Exception {
        
        Stage stage = new Stage();
        Parent modal;
        
        modal = FXMLLoader.load(getClass().getResource("FXMLQuestionAdd.fxml"));
        
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add Question");
            
        Scene scene = new Scene(modal);
        stage.setScene(scene);
        stage.showAndWait();
        
        reloadQuestionsList();
    }

//    Popup Modal for modifying a question (reloads the table right after)
    @FXML
    private void editQuestion(ActionEvent event) throws Exception {
        Question editable = LAffiche.getSelectionModel().getSelectedItem();
        
        FXMLLoader modal = new FXMLLoader(getClass().getResource("FXMLQuestionEdit.fxml"));
        Parent root = modal.load();
        
        FXMLEditQuestionController editModal = modal.getController();
        
        editModal.showInformation(editable);
        
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Edit Question");
        stage.showAndWait();
        
        reloadQuestionsList();
    }

//    Deleting selected items and reloading the table
    @FXML
    private void deleteQuestion(ActionEvent event) throws Exception {
    
        ServiceQuestion sq2 = new ServiceQuestion();
        
        ObservableList<Question> questionsSelected;
        questionsSelected = LAffiche.getSelectionModel().getSelectedItems();
        
        questionsSelected.forEach(e -> {
            sq2.RemoveQuestion(e.getId());
        });
        
        
        reloadQuestionsList();
        
    }

//    reloading the table through "reload" button
    @FXML
    private void reloadQuestions(ActionEvent event) {
        
        reloadQuestionsList();
        
    }

    @FXML
    private void addAnswers(ActionEvent event) {
    }

    @FXML
    private void showAnswers(ActionEvent event) {
    }
    
}
