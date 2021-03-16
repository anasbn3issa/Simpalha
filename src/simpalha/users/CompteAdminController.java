/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.users;

import entities.Users;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import services.ServiceUsers;

/**
 * FXML Controller class
 *
 * @author win10
 */
public class CompteAdminController implements Initializable {

    @FXML
    private TableView<Users> table;
    
    private ServiceUsers service;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        service = new ServiceUsers();
        TableColumn<Users, String> idCol = new TableColumn<>("Id");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Users, String> idemailCol = new TableColumn<>("Email");
        idemailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Users, String> idNomCol = new TableColumn<>("Nom");
        idNomCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        

        TableColumn<Users, String> idSpecCol = new TableColumn<>("Spécialité");
        idSpecCol.setCellValueFactory(new PropertyValueFactory<>("Specialité"));

        //@FXML
        //private void read(ActionEvent event) {
        TableColumn modCol = new TableColumn("Action");
        modCol.setCellValueFactory(new PropertyValueFactory<>("modify"));
        Callback<TableColumn<Users, String>, TableCell<Users, String>> cellFactoryModify
                =                 //
        (final TableColumn<Users, String> param) -> {
            final TableCell<Users, String> cell = new TableCell<Users, String>() {
                
                final Button modify = new Button("Modify");
                
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        modify.setOnAction(event -> {
                            Users user = getTableView().getItems().get(getIndex());
                            
                            try {
                                FXMLLoader loader = new FXMLLoader(
                                        getClass().getResource(
                                                "modifierProfile.fxml"
                                        )
                                );
                                
                                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
                                stage.setScene(
                                        new Scene(loader.load())
                                );
                                
                                ModifierProfileController controller = loader.getController();
                                controller.initData(user.getId());
                                
                                stage.show();
                            } catch (IOException ex) {
                                Logger.getLogger(CompteAdminController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                        
                        setGraphic(modify);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        modCol.setCellFactory(cellFactoryModify);

        TableColumn delCol = new TableColumn();
        delCol.setCellValueFactory(new PropertyValueFactory<>("delete"));
        Callback<TableColumn<Users, String>, TableCell<Users, String>> cellFactoryDelete
                = //
                (final TableColumn<Users, String> param) -> {
                    final TableCell<Users, String> cell = new TableCell<Users, String>() {

                final Button delete = new Button("Delete");

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        delete.setOnAction(event -> {
                            Users user = getTableView().getItems().get(getIndex());
                            service.Delete(user);
                            table.getItems().clear();
                            table.getItems().addAll(service.Read());

                        });

                        setGraphic(delete);
                        setText(null);
                    }

                }

            ;

                    };
            return cell;

                };

        delCol.setCellFactory(cellFactoryDelete);

        table.getColumns().add(idCol);
        table.getColumns().add(idemailCol);
        table.getColumns().add(idNomCol);

        table.getColumns().add(idSpecCol);
        table.getColumns().add(modCol);
        table.getColumns().add(delCol);
        
        List<Users> list = service.Read();
        list.forEach((user) -> {
            table.getItems().add(user);
        });
    }    

    @FXML
    private void goToViewPosts(MouseEvent event) {
    }

    @FXML
    private void showP2P(MouseEvent event) {
    }

    @FXML
    private void AddNewPost(ActionEvent event) {
    }
    
}
