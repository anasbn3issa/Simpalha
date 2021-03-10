/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.quizz;

import entities.Answer;
import entities.Question;
import entities.QuizzWrapper;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.ServiceAnswer;
import services.ServiceQuestion;

/**
 * FXML Controller class
 *
 * @author Parsath
 */
public class FXMLQuizzEvalAnswerController implements Initializable {

    @FXML
    private Label laQuestion;
    @FXML
    private TableView<Answer> tableAnswers;
    @FXML
    private TableColumn<Answer, String> suggestionColumn;
    @FXML
    private ComboBox<Integer> cbAnswer;
    
    private QuizzWrapper q1;
    private boolean result;
    private int addedQuizzId;
    private int totalResult;
    private int nbQuestions;
    private int unresolved;
    @FXML
    private Button btAjouterReponse;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void showP2P(MouseEvent event) {
    }

    @FXML
    private void showQuizz(MouseEvent event) {
    }

    @FXML
    private void ajouterReponse(ActionEvent event) throws Exception {
        QuizzWrapper editable = q1;
        boolean checkResult = checkResult();
        
        FXMLLoader modal = new FXMLLoader(getClass().getResource("FXMLQuizzEval.fxml"));
        Parent root = modal.load();
        
        FXMLQuizzEvalController editModal = modal.getController();
        
        editModal.updateQuestions(editable, addedQuizzId,checkResult,this.totalResult,this.nbQuestions,this.unresolved);
                
        Stage stage;
        stage = (Stage) btAjouterReponse.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Quizz");
        stage.show();
    }
    
//    Reusable function to reload the answers to question q1
    public void reloadAnswersList(){
    
        ServiceAnswer sa = new ServiceAnswer();
        
        try {
            tableAnswers.setItems(sa.ObservableListAnswers(this.q1.getQuestion()));
            setChoiceBox(q1.getQuestion().getId());
        } catch (SQLException ex) {
            Logger.getLogger(FXMLQuestionEditController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public boolean checkResult() throws SQLException{
        ServiceQuestion sa = new ServiceQuestion();
        boolean chResult;
        
        int selectedAnswer = cbAnswer.getSelectionModel().getSelectedItem();
        chResult = sa.IsCorrectAnswer(selectedAnswer, q1.getQuestion().getId());
        
        return chResult;
    }

    
//    Affiche les informations de l'objet transmit par "FXMLTableQuestionController" et enregistre l'Objet dans la variable q1
    public void showInformation(QuizzWrapper q, int quizzId, int totalResult, int nbQuestions, int unresolved){
        q1 = new QuizzWrapper();
        
        q1.setQuestion(q.getQuestion());
        q1.setStatus(true);
        
        laQuestion.setText(q.getQuestion().getQuestion());
        cbAnswer.getSelectionModel().select(0);
        
        this.addedQuizzId = quizzId;
        this.totalResult = totalResult;
        this.nbQuestions = nbQuestions;
        this.unresolved = unresolved;
        
        reloadAnswersList();
    }
    
    
//    Will set the choicebox indexes for the answers
    public void setChoiceBox(int questionId) throws SQLException{
        ServiceAnswer sa = new ServiceAnswer();
        
        int answersCount;
        
        answersCount = sa.CountAnswers(questionId);
        int i;
        
        cbAnswer.getItems().clear();
        
        if(answersCount==0){
            cbAnswer.getItems().add(0);
        }
        else{
            for(i=0; i<answersCount; i++){
                cbAnswer.getItems().add(i+1);
            }
        }
    }
    
}

