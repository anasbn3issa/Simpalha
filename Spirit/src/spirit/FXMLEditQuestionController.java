/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spirit;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Parsath
 */
public class FXMLEditQuestionController implements Initializable {

    @FXML
    private Button scene1;

//    Changing Scenes test
    @FXML
    private void changeScene(ActionEvent event) throws Exception {
        Stage stage;
        Parent root;
        
        stage = (Stage) scene1.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("FXMLQuestionAdd.fxml"));
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
