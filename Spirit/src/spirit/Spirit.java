/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spirit;

import Entities.Question;
import Entities.Quizz;
import Service.ServiceAnswer;
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
        
        ServiceAnswer sa = new ServiceAnswer();
        System.out.println(sa.CountAnswers(10));
        
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
