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
import services.ServiceAnswer;
import services.ServiceQuestion;
import services.ServiceWrapper;

/**
 * FXML Controller class
 *
 * @author Parsath
 */
public class FXMLQuizzEvalController implements Initializable {

    private int addedQuizzId;
    @FXML
    private TableView<QuizzWrapper> LAffiche;
    @FXML
    private TableColumn<QuizzWrapper, String> questionColumn;
    @FXML
    private TableColumn<QuizzWrapper, Boolean> statusColumn;
    
    private int unresolved;
    private int totalResult;
    private int nbQuestions;
    @FXML
    private Button btAnswerQuestion;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
//    Reusable function to reload the table
    public void reloadQuestionsList(int id){
        ServiceWrapper sq = new ServiceWrapper();
        
        try {
            LAffiche.setItems(sq.ObservableListQuestionsWrapper(id));
        } catch (SQLException ex) {
            Logger.getLogger(FXMLQuestionTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setUnresolved() throws SQLException{
        ServiceQuestion sq = new ServiceQuestion();
        
        unresolved = sq.CountQuestions(addedQuizzId);
        nbQuestions = unresolved;
        totalResult = 0;
    }
    
    public void showInformation(int id) throws SQLException{
        
        addedQuizzId = id;
        
        ServiceAnswer sa = new ServiceAnswer();
        unresolved = sa.CountAnswers(addedQuizzId);
        setUnresolved();
        
        reloadQuestionsList(addedQuizzId);
    }
    
    public void updateQuestions(QuizzWrapper q, int id,boolean result, int totalResult, int nbQuestions, int unresolved)throws SQLException{
        
        this.unresolved = unresolved;
        this.totalResult = totalResult;
        this.nbQuestions = nbQuestions;
        if(result==true)
            this.totalResult++;
        
        this.unresolved--;
    }

    @FXML
    private void showP2P(MouseEvent event) {
    }

    @FXML
    private void showQuizz(MouseEvent event) {
    }

    @FXML
    private void answerQuestion(ActionEvent event) throws Exception {
        QuizzWrapper editable = LAffiche.getSelectionModel().getSelectedItem();
        
        FXMLLoader modal = new FXMLLoader(getClass().getResource("FXMLQuizzEvalAnswer.fxml"));
        Parent root = modal.load();
        
        FXMLQuizzEvalAnswerController editModal = modal.getController();
        
        editModal.showInformation(editable, addedQuizzId,this.totalResult,this.nbQuestions,this.unresolved);
        
        Stage stage;
        stage = (Stage) btAnswerQuestion.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Answer Question");
        stage.show();
    }

    @FXML
    private void showResult(ActionEvent event) {
    }

    @FXML
    private void exit(ActionEvent event) {
    }
    
}
