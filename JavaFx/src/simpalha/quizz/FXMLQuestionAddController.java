/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.quizz;

import simpalha.quizz.FXMLQuestionTableController;
import entities.Question;
import java.io.IOException;
import services.ServiceQuestion;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import simpalha.FXMLDocumentController;

/**
 *
 * @author Parsath
 */
public class FXMLQuestionAddController implements Initializable {
    
    @FXML
    private TextField tfQuestion;
    private TableView<Question> LAffiche;
    @FXML
    private Button ajouterEtQuitter;
    
    private int addedQuizzId;
    @FXML
    private ChoiceBox<Integer> cbRightAnswer;
    
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
        q.setAnswer(cbRightAnswer.getValue());
        q.setQuizz(addedQuizzId);
        
        sq.Create(q);
        
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
    
//    remplit le comboBox "cbRighAnswer" et initialise "addedQuizzId"
    public void addInformation(int id){
        addedQuizzId = id;
        cbRightAnswer.getItems().add(0);
        cbRightAnswer.setValue(0);
    }
    
}
