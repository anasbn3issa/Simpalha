/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spirit;

import Entities.Question;
import Entities.Quizz;
import Service.ServiceQuestion;
import Service.ServiceQuizz;
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
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Parsath
 */
public class FXMLQuizzEditController implements Initializable {

    @FXML
    private TableView<Question> LAffiche;
    @FXML
    private TableColumn<Question, String> questionColumn;
    @FXML
    private TableColumn<Question, Integer> answerColumn;
    @FXML
    private TableColumn<Question, Integer> idColumn;
    @FXML
    private TextField tfTitle;
    @FXML
    private TextField tfSubject;
    
    private int addedQuizzId;
    private Quizz q1;
    @FXML
    private Button addQuizzButton;

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
            sq2.RemoveQuestion(e.getId());
        });
        
        
        reloadQuestionsList(addedQuizzId);
        
    }

//    reloading the table through "reload" button
    private void reloadQuestions(ActionEvent event) {
        
        reloadQuestionsList(addedQuizzId);
        
    }

    @FXML
    private void addQuizz(ActionEvent event) {
    
        ServiceQuizz sq = new ServiceQuizz();
        Quizz q = new Quizz();
        
        q.setSubject(tfSubject.getText());
        q.setTitle(tfTitle.getText());
        
        sq.EditQuizz(q1.getId(), q);
        
        Stage stage;
        Parent root;
        
        stage = (Stage) addQuizzButton.getScene().getWindow();
        stage.close();
    }
    
    public void addInformation(int id){
        addedQuizzId = id;
    }
    
//    Affiche les informations de l'objet transmit par "FXMLTableQuizzController" et enregistre l'Objet dans la variable q1
    public void showInformation(Quizz q){
        q1 = new Quizz();
        q1.setTitle(q.getTitle());
        q1.setId(q.getId());
        q1.setSubject(q.getSubject());
        q1.setHelper(q.getHelper());
        
        addInformation(q1.getId());
        
        tfTitle.setText(q.getTitle());
        tfSubject.setText(q.getSubject());
        
        reloadQuestionsList(addedQuizzId);
    }

}
