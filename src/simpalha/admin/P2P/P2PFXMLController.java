/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.admin.P2P;

import entities.Disponibilite;
import entities.Meet;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import services.ServiceDisponibilite;
import services.ServiceP2P;
import services.ServiceUsers;
import simpalha.FXMLDocumentController;
import utils.UserSession;

/**
 * FXML Controller class
 *
 * @author α Ω
 */
public class P2PFXMLController implements Initializable {

    @FXML
    private TableView<Meet> meets;

    private ServiceP2P service;
    private ServiceDisponibilite serviceDisp;
    private ServiceUsers serviceUser;
    @FXML
    private TextField tfsearch;

    private HashMap<String, ArrayList<Meet>> specs = new HashMap<>();
    private int userId;

    //observalble list to store data
    private ObservableList<Meet> dataList = FXCollections.observableArrayList();
    @FXML
    private ComboBox<String> Specialite;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        userId = UserSession.getInstace(0).getUserid();

        service = new ServiceP2P();
        serviceDisp = new ServiceDisponibilite();
        serviceUser = new ServiceUsers();

        TableColumn<Meet, String> idHlpCol = new TableColumn<>("Helper");
        idHlpCol.setCellValueFactory(new PropertyValueFactory<>("helperDisplay"));

        TableColumn<Meet, String> idStdCol = new TableColumn<>("Student");
        idStdCol.setCellValueFactory(new PropertyValueFactory<>("studentDisplay"));

        TableColumn<Meet, String> idFdbCol = new TableColumn<>("Feedback");
        idFdbCol.setCellValueFactory(new PropertyValueFactory<>("feedbackDisplay"));

        TableColumn<Meet, String> spcCol = new TableColumn<>("Specialite");
        spcCol.setCellValueFactory(new PropertyValueFactory<>("specialite"));

        TableColumn<Meet, String> timeCol = new TableColumn<>("Time");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn modCol = new TableColumn("Action");
        modCol.setCellValueFactory(new PropertyValueFactory<>(""));

        Callback<TableColumn<Meet, String>, TableCell<Meet, String>> cellFactoryModify
                = //
                new Callback<TableColumn<Meet, String>, TableCell<Meet, String>>() {
            @Override
            public TableCell call(final TableColumn<Meet, String> param) {
                final TableCell<Meet, String> cell = new TableCell<Meet, String>() {

                    final Button modify = new Button("Details");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            Meet meet = getTableView().getItems().get(getIndex());

                            modify.setOnAction(event -> {
                                try {
                                    FXMLLoader loader = new FXMLLoader(
                                            getClass().getResource(
                                                    "UpdateP2PFXML.fxml"
                                            )
                                    );

                                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
                                    stage.setScene(
                                            new Scene(loader.load())
                                    );

                                    UpdateP2PFXMLController controller = loader.getController();
                                    controller.initData(meet.getId(), meet.getId_helper());
                                    stage.show();
                                } catch (IOException ex) {
                                    Logger.getLogger(P2PFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            });

                            setGraphic(modify);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };

        modCol.setCellFactory(cellFactoryModify);

        TableColumn delCol = new TableColumn();
        delCol.setCellValueFactory(new PropertyValueFactory<>("delete"));
        Callback<TableColumn<Meet, String>, TableCell<Meet, String>> cellFactoryDelete
                = //
                new Callback<TableColumn<Meet, String>, TableCell<Meet, String>>() {
            @Override
            public TableCell call(final TableColumn<Meet, String> param) {
                final TableCell<Meet, String> cell = new TableCell<Meet, String>() {
                    final Button delete = new Button("Delete");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            delete.setOnAction(event -> {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?",
                                        ButtonType.YES, ButtonType.NO);
                                alert.showAndWait();

                                if (alert.getResult() == ButtonType.YES) {
                                    //do stuff
                                    Meet meet = getTableView().getItems().get(getIndex());
                                    Disponibilite dispo = serviceDisp.findByTime(meet.getTime());
                                    dispo.setEtat(0);
                                    serviceDisp.Update(dispo);
                                    service.Delete(meet);
                                    dataList.clear();
                                    dataList.addAll(service.Read());
                                }
                            });

                            setGraphic(delete);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };

        delCol.setCellFactory(cellFactoryDelete);

        meets.getColumns().add(idHlpCol);
        meets.getColumns().add(idStdCol);
        meets.getColumns().add(idFdbCol);
        meets.getColumns().add(spcCol);
        meets.getColumns().add(timeCol);
        meets.getColumns().add(modCol);
        meets.getColumns().add(delCol);
        dataList.addAll(service.Read());
        meets.getItems().addAll(dataList);

        dataList.forEach((meet) -> {
            specs.putIfAbsent(meet.getSpecialite(), new ArrayList<Meet>(Arrays.asList(meet)));
        });
        Specialite.getItems().add("All");
        for (String sp : specs.keySet()) {
            Specialite.getItems().add(sp);
        }
        

        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Meet> filteredData = new FilteredList<>(dataList, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        tfsearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(meet -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (meet.getHelperDisplay().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (meet.getStudentDisplay().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (meet.getSpecialite().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else {
                    return false; // Does not match.
                }
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<Meet> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(meets.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        meets.setItems(sortedData);

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
    private void filterItems(ActionEvent event) {
        String filter = Specialite.getValue().trim().toLowerCase();
        ObservableList<Meet> filteredList = FXCollections.observableArrayList();

        if(!filter.toLowerCase().equals("all"))
            filteredList = dataList.stream().filter(spc -> spc.getSpecialite().toLowerCase().equals(filter)).collect(Collectors.toCollection(FXCollections::observableArrayList));
        else
            filteredList.addAll(service.Read());
        
        dataList.clear();
        dataList.addAll(filteredList);
    }

    @FXML
    private void signout(MouseEvent event) {
        UserSession.getInstace(0).cleanUserSession();
       //note that on this line you can substitue "Screen2.fxml" for a string chosen prior to this line.
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("/simpalha/users/Login.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }

}
