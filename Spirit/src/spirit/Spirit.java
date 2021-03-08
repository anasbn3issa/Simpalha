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
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.DataSource;

/**
 *
 * @author Parsath
 */
public class Spirit extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
    
//        ServiceQuestion sq = new ServiceQuestion();
//        Question q = new Question();
//        
//        q.setQuestion("yes");
//        q.setAnswer(33);
//        q.setQuizz(3);
//        
//        sq.AddQuestion(q);
    
//        ServiceQuizz sq = new ServiceQuizz();
//        Quizz q = new Quizz();
//        
//        q.setTitle("yes");
//        q.setSubject("non");
//        q.setHelper(3);
//        
//        sq.AddQuizz(q);
        
        
        stage.setTitle("Quizz ");
        
        Parent root = FXMLLoader.load(getClass().getResource("FXMLQuizzTable.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
