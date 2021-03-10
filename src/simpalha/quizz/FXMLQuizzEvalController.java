/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.quizz;

import entities.Answer;
import entities.Question;
import entities.Quizz;
import entities.QuizzStats;
import entities.QuizzWrapper;
import java.io.IOException;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.ServiceAnswer;
import services.ServiceQuestion;
import services.ServiceQuizzStats;
import services.ServiceWrapper;
import simpalha.FXMLDocumentController;

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
    private TableColumn<QuizzWrapper, String> statusTranslationColumn;
    @FXML
    private TableColumn<QuizzWrapper, Boolean> statusColumn;
    @FXML
    private Button btAnswerQuestion;
    
    private ObservableList<QuizzWrapper> quizzTableItems;
    public static QuizzStats quizzStats;
    
    @FXML
    private Label lTotalQuestions;
    @FXML
    private Label laResult;
    @FXML
    private Button btExit;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        quizzStats = new QuizzStats();
    }    
    
//    Reusable function to reload the table
    public void reloadQuestionsList(int id){
        System.out.println("reloadQuestionsList");
        ServiceWrapper sq = new ServiceWrapper();
        
        try {
            LAffiche.setItems(sq.ObservableListQuestionsWrapper(id));
            quizzTableItems = LAffiche.getItems();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLQuestionTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void showInformationEval(int id) throws SQLException{
        System.out.println("showInformationEval");
        
        addedQuizzId = id;
        
        setUnresolved(id);
        
        reloadQuestionsList(addedQuizzId);        
        
//         System.out.println("show Information function: \nunresolved : "+this.unresolvedEval+"\ntotal Questions : "+this.nbQuestionsEval+"\nTotal Result : "+this.totalResultEval+"\n");
    }
    
    public void resetQuestionsTable(){
        System.out.println("resetQuestionsTable");
        LAffiche.getItems().clear();
        LAffiche.setItems(quizzTableItems);
    }

    @FXML
    private void showP2P(MouseEvent event) {
        System.out.println("showP2P");
    }

    @FXML
    private void showQuizz(MouseEvent event) {
        System.out.println("showQuizz");
    }

    @FXML
    private void answerQuestion(ActionEvent event) throws Exception {
        System.out.println("answerQuestion");
        
        QuizzWrapper editable = LAffiche.getSelectionModel().getSelectedItem();
        
        if(editable.getTranslation().equals("Done")){
            laResult.setText("You already answered this question!");
        }
        else{
        int indexEditable = quizzTableItems.indexOf(editable);
        
        FXMLLoader modal = new FXMLLoader(getClass().getResource("FXMLQuizzEvalAnswer.fxml"));
        Parent root = modal.load();
        
        FXMLQuizzEvalAnswerController editModal = modal.getController();
        

        editModal.showInformation(indexEditable,quizzTableItems,editable, addedQuizzId,quizzStats);
        
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Answer Question");
        stage.showAndWait();
        }
    }
    
    public void setUnresolved(int id) throws SQLException{
        System.out.println("setUnresolved");
        
        ServiceQuizzStats sqs = new ServiceQuizzStats();
        
        if(quizzStats.getNbQuestionsEval() == 0)
            sqs.SetUnresolved(quizzStats, id);
        
        lTotalQuestions.setText(String.valueOf(quizzStats.getNbQuestionsEval()));
        
    }
    
    public void updateValues(QuizzStats qss, boolean result){
        System.out.println("updateValues");
        
        ServiceQuizzStats sqs = new ServiceQuizzStats();
        
        sqs.UpdateValues(quizzStats, qss.getTotalResultEval(), qss.getNbQuestionsEval(), qss.getUnresolvedEval(), result);   
        
        lTotalQuestions.setText(String.valueOf(quizzStats.getNbQuestionsEval()));
    }
    
    public void updateQuestions(int indexQuizz,ObservableList<QuizzWrapper> tableItems,QuizzWrapper q, int id)throws SQLException{
        System.out.println("updateQuestions");
        
        quizzTableItems = tableItems;
        
        resetQuestionsTable();
        
        
        
        QuizzWrapper q3;
        QuizzWrapper q4 = new QuizzWrapper();
        
        LAffiche.getSelectionModel().clearSelection();
        LAffiche.getSelectionModel().select(indexQuizz);
        
        
        q3 = LAffiche.getSelectionModel().getSelectedItem();
        LAffiche.getItems().remove(q3);

        q4.setQuestion(q3.getQuestion());
        q4.setStatus(true);
        q4.setTranslation();
        LAffiche.getItems().remove(q3);
        LAffiche.getItems().add(q4);
        
        
    }

    @FXML
    private void showResult(ActionEvent event) {
        if(quizzStats.getUnresolvedEval()!=0)
        {
            laResult.setText("You have not finished yet!");
        }
        else{
            int maxScore = quizzStats.getNbQuestionsEval();
            int result = quizzStats.getTotalResultEval();
            int average = (20*result)/maxScore;
            laResult.setText(String.valueOf(average)+" / 20");
        }
    
        System.out.println("Check ints : \n"+quizzStats);
    }

    @FXML
    private void exit(ActionEvent event) throws IOException{
        
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/simpalha/FXMLDocument.fxml"
//                            "quizz/FXMLQuizzTable.fxml"
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

    
}
