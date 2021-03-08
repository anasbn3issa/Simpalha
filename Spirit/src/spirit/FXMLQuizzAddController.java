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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Parsath
 */
public class FXMLQuizzAddController implements Initializable {

    @FXML
    private TextField tfTitle;
    @FXML
    private TextField tfSubject;
    @FXML
    private Button addQuizzButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void addQuizz(ActionEvent event) throws Exception {
    
        ServiceQuizz sq = new ServiceQuizz();
        Quizz q = new Quizz();
        
        q.setTitle(tfTitle.getText());
        q.setSubject(tfSubject.getText());
        q.setHelper(1);
        
        sq.AddQuizz(q);
        
        Stage stage;
        stage = (Stage) addQuizzButton.getScene().getWindow();
        
        Parent root;
        
        FXMLLoader addModal = new FXMLLoader(getClass().getResource("FXMLQuestionTable.fxml"));
        root = addModal.load();
        FXMLQuestionTableController addQuizzModal = addModal.getController();
        addQuizzModal.addInformation(sq.LastAddedQuizzId());
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Quizz Creation Modal");
        stage.show();
    }
    
}
