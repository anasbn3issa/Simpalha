/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.admin.P2P;

import simpalha.P2P.*;
import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.view.swing.BrowserView;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;
import com.teamdev.jxbrowser.media.MediaDevice;
import com.teamdev.jxbrowser.media.MediaDeviceType;
import com.teamdev.jxbrowser.media.MediaDevices;
import com.teamdev.jxbrowser.permission.PermissionType;
import com.teamdev.jxbrowser.permission.callback.RequestPermissionCallback;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconName;
import entities.Disponibilite;
import entities.Meet;
import entities.Users;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javax.swing.JButton;
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

    private int userId;

    //observalble list to store data
    private ObservableList<Meet> dataList = FXCollections.observableArrayList();

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
                            modify.setDisable(true);
                            if (meet.getFeedback_id() == 0) {
                                modify.setDisable(false);
                            }
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

                                    //UpdateP2PFXMLController controller = loader.getController();
                                    //controller.initData(meet.getId(), meet.getId_helper());
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

}
