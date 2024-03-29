/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.quizz;

import simpalha.quizz.FXMLQuestionEditController;
import entities.Answer;
import entities.Notification;
import entities.QuizzStats;
import entities.QuizzWrapper;
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
    private int addedQuizzId;
    private int indexOfQuizz;
    private Notification notif;
    private QuizzStats qs1;
    private ObservableList<QuizzWrapper> tableItems;
    @FXML
    private Button btAjouterReponse;
    private int userId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

//    vérifie si la suggestion choisie est correct et transmets les nouvelles statistiques (q1 après mise à jour) au QuizzEvalController
    @FXML
    private void ajouterReponse(ActionEvent event) throws Exception {
        QuizzWrapper editable = q1;
        boolean checkResult = checkResult();
        
        FXMLLoader modal = new FXMLLoader(getClass().getResource("FXMLQuizzEval.fxml"));
        Parent root = modal.load();
        
        FXMLQuizzEvalController editModal = modal.getController();
        
        System.out.println("Quizz Eval Answer Controller : \n QuizzStats: "+qs1);
        editModal.updateQuestions(this.indexOfQuizz,tableItems,editable, addedQuizzId,notif);
        editModal.updateValues(qs1,checkResult);
        
        Stage stage;
        stage = (Stage) btAjouterReponse.getScene().getWindow();
        stage.close();
    }

    
//    Affiche les informations de l'objet transmit par "FXMLQuizzEval" et enregistre les statisques dans l'Objet QuizzStats qs1 ainsi que l'état de la question dans l'Objet QuizzWrapper q1
    public void showInformation(int indexQuizz,ObservableList<QuizzWrapper> allItems,QuizzWrapper q, int quizzId, QuizzStats qss, Notification n){
        notif = n;
        q1 = new QuizzWrapper();
        
        q1.setQuestion(q.getQuestion());
        q1.setStatus(false);
        q1.setTranslation();
        
        laQuestion.setText(q.getQuestion().getQuestion());
        
        this.indexOfQuizz = indexQuizz;
        this.addedQuizzId = quizzId;
        
        qs1 = qss;
        
        reloadAnswersList();
        
        this.tableItems = allItems;
        cbAnswer.getSelectionModel().select(0);
        
        System.out.println("Quizz Eval Answer Show information : \n"+qs1+"\n");
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
    
//    vérifie si la réponse choisie est la bonne réponse
    public boolean checkResult() throws SQLException{
        ServiceQuestion sa = new ServiceQuestion();
        boolean chResult;
        
        int selectedAnswer = cbAnswer.getSelectionModel().getSelectedItem();
        chResult = sa.IsCorrectAnswer(selectedAnswer, q1.getQuestion().getId());
        
        return chResult;
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

//    Will change the choiceBox "cbAnswer"s' selection depending on which row the user is selecting in the Answers Table
    @FXML
    private void detectSuggestion(MouseEvent event) {
        int i = tableAnswers.getSelectionModel().getSelectedIndex();
        cbAnswer.getSelectionModel().select(i);
    }
}

