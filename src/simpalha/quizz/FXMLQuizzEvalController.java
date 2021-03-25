/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.quizz;

import entities.Notification;
import entities.QuizzResult;
import entities.QuizzStats;
import entities.QuizzWrapper;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.ServiceNotification;
import services.ServiceQuizzResult;
import services.ServiceQuizzStats;
import services.ServiceWrapper;
import simpalha.FXMLDocumentController;

/**
 * FXML Controller class
 *
 * @author Parsath
 */
public class FXMLQuizzEvalController implements Initializable {

    @FXML
    private TableView<QuizzWrapper> LAffiche;
    @FXML
    private TableColumn<QuizzWrapper, String> questionColumn;
    @FXML
    private TableColumn<QuizzWrapper, String> statusTranslationColumn;
    @FXML
    private Button btAnswerQuestion;
    @FXML
    private Label lTotalQuestions;
    @FXML
    private Label laResult;
    @FXML
    private Button btExit;
    
    private ObservableList<QuizzWrapper> quizzTableItems;
    public static QuizzStats quizzStats;
    private QuizzResult quizzResultsObject;
    private Notification notif;
    private boolean isResultShown;
    private int addedQuizzId;
    private int userId;
    
    /**
     * Initializes the controller class and initializes quizzStats
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        isResultShown = false;
        quizzStats = new QuizzStats();
        quizzResultsObject = new QuizzResult();
        notif = new Notification();
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
    
//    Will initialize addedQuizzId through the Information sent by the FXMLQuizzTakingController
    public void showInformationEval(int id, int userIdSent, int helperQuizzCreator) throws SQLException{
        notif.setUser(helperQuizzCreator);
        userId = userIdSent;
        addedQuizzId = id;
        
        setUnresolved(id);
        
        reloadQuestionsList(addedQuizzId);        
    }
    
//    Will reset the Questions Table
    public void resetQuestionsTable(){
        LAffiche.getItems().clear();
        LAffiche.setItems(quizzTableItems);
    }

//    Will transmit the QuizzStats, the QuizzWrapper, and the index of the selected Question to the FXMLQuizzEvalAnswerController
    @FXML
    private void answerQuestion(ActionEvent event) throws Exception {
        
        QuizzWrapper editable = LAffiche.getSelectionModel().getSelectedItem();
        
        if(editable.getTranslation().equals("Done")){
            laResult.setText("You already answered this question!");
        }
        else{
            int indexEditable = quizzTableItems.indexOf(editable);

            FXMLLoader modal = new FXMLLoader(getClass().getResource("FXMLQuizzEvalAnswer.fxml"));
            Parent root = modal.load();

            FXMLQuizzEvalAnswerController editModal = modal.getController();


            editModal.showInformation(indexEditable,quizzTableItems,editable, addedQuizzId,quizzStats,notif);

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("Answer Question");
            stage.showAndWait();
        }
    }
    
//    Initializes the quizzStats variables
    public void setUnresolved(int id) throws SQLException{
        
        ServiceQuizzStats sqs = new ServiceQuizzStats();
        
        if(quizzStats.getNbQuestionsEval() == 0)
            sqs.SetUnresolved(quizzStats, id);
        
        lTotalQuestions.setText(String.valueOf(quizzStats.getNbQuestionsEval()));
        
    }
    
//    Updates the values of the quizzStats upon answering a question
    public void updateValues(QuizzStats qss, boolean result){
        ServiceQuizzStats sqs = new ServiceQuizzStats();
        
        sqs.UpdateValues(quizzStats, qss.getTotalResultEval(), qss.getNbQuestionsEval(), qss.getUnresolvedEval(), result);   
        
        lTotalQuestions.setText(String.valueOf(quizzStats.getNbQuestionsEval()));
    }
    
//    Updates the status of an answered QuizzWrapper by setting its status to true
    public void updateQuestions(int indexQuizz,ObservableList<QuizzWrapper> tableItems,QuizzWrapper q, int id, Notification n)throws SQLException{
        notif = n;
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

//    Will show the results of a Quizz or deny it if all the questions are still not answered.
    @FXML
    private void showResult(ActionEvent event) {
        if(quizzStats.getUnresolvedEval()!=0)
        {
            laResult.setText("You have not finished yet!");
        }
        else{
            if(!isResultShown)
            {
                ServiceNotification serviceNotif = new ServiceNotification();
                ServiceQuizzResult qr = new ServiceQuizzResult();

                int maxScore = quizzStats.getNbQuestionsEval();
                int result = quizzStats.getTotalResultEval();
                int average = (20*result)/maxScore;
                laResult.setText(String.valueOf(average)+" / 20");

                quizzResultsObject.setDate(LocalDateTime.now());
                quizzResultsObject.setQuizz(addedQuizzId);
                quizzResultsObject.setResult(average);
    //            TODO : Replace setStudent value once we integrate our work
                quizzResultsObject.setStudent(notif.getUser());

                qr.Create(quizzResultsObject);
                
                isResultShown = true;
                
                notif.setTitle("Quizz Result");
                notif.setContent("The Student "+userId+" has received a mark of "+average+"/20 in the Quizz \""+addedQuizzId+".");
                notif.setRead(false);
                notif.setSent(false);
//                System.out.println(notif);
//                System.out.println(userId);
                
                serviceNotif.createNotification(notif);
                
            }
       }
    }

//    Exits the Quizz and goes to Dashboard
    @FXML
    private void exit(ActionEvent event) throws IOException{
        
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
}
