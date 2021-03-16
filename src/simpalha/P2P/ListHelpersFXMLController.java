/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.P2P;

import entities.Users;
import java.io.IOException;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.ServiceUsers;
import simpalha.FXMLDocumentController;

/**
 * FXML Controller class
 *
 * @author α Ω
 */
public class ListHelpersFXMLController implements Initializable {

    @FXML
    private Button back;
    
    //var
    private ServiceUsers service;
    @FXML
    private TableView<Users> helpersList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // TODO
        service = new ServiceUsers();

        TableColumn<Users, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("fname"));

        TableColumn<Users, String> specialiteCol = new TableColumn<>("Specialite");
        specialiteCol.setCellValueFactory(new PropertyValueFactory<>("specialites"));

        helpersList.getColumns().add(nameCol);
        helpersList.getColumns().add(specialiteCol);

        fillData();
    }    

    @FXML
    private void showP2P(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "P2PFXML.fxml"
                    )
            );

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
            stage.setScene(
                    new Scene(loader.load())
            );
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Back(ActionEvent event) {
        //note that on this line you can substitue "Screen2.fxml" for a string chosen prior to this line.
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("P2PFXML.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }
    
    private void fillData() {
        helpersList.getItems().addAll(service.Read());
    }

    @FXML
    private void changeView(MouseEvent event) {
        if (event.getClickCount() == 2) //Checking double click
        {
            Users s = helpersList.getSelectionModel().getSelectedItem();

            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource(
                                "NewMeetFXML.fxml"
                        )
                );

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
                stage.setScene(
                        new Scene(loader.load())
                );
                
                NewMeetFXMLController controller = loader.getController();
                controller.initData(s.getId());
                
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(ListHelpersFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
    
}
