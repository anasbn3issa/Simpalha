/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spirit;

import Entities.Question;
import Service.ServiceQuestion;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Parsath
 */
public class FXMLQuestionAddController implements Initializable {
    
    @FXML
    private TextField tfQuestion;
    @FXML
    private TextField tfRightAnswer;
    private TableView<Question> LAffiche;
    @FXML
    private Button ajouterEtQuitter;
    
    private int addedQuizzId;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

//    Ajoute une question après avoir remplit les champs et cliquer sur "Ajouter"
    @FXML
    private void ajouterQuestion(ActionEvent event) throws Exception{
    
        ServiceQuestion sq = new ServiceQuestion();
        Question q = new Question();
        
        q.setQuestion(tfQuestion.getText());
        q.setAnswer(Integer.parseInt(tfRightAnswer.getText()));
        q.setQuizz(addedQuizzId);
        
        sq.AddQuestion(q);
        
        Stage stage;
        stage = (Stage) ajouterEtQuitter.getScene().getWindow();
        
        Parent root;
        
        FXMLLoader addModal = new FXMLLoader(getClass().getResource("FXMLQuestionTable.fxml"));
        root = addModal.load();
        FXMLQuestionTableController addQuizzModal = addModal.getController();
        addQuizzModal.addInformation(addedQuizzId);
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Quizz Creation Modal");
        stage.show();
        
    }
    
    public void addInformation(int id){
        addedQuizzId = id;
    }
    
}
