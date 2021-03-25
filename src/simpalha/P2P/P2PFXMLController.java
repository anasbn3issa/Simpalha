/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.P2P;

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
import javafx.scene.control.Button;
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
    @FXML
    private Button ajouter;

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

        TableColumn<Meet, String> idFdbCol = new TableColumn<>("Feedback");
        idFdbCol.setCellValueFactory(new PropertyValueFactory<>("feedbackDisplay"));

        TableColumn<Meet, String> spcCol = new TableColumn<>("Specialite");
        spcCol.setCellValueFactory(new PropertyValueFactory<>("specialite"));

        TableColumn<Meet, String> timeCol = new TableColumn<>("Time");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn modCol = new TableColumn("Action");
        modCol.setCellValueFactory(new PropertyValueFactory<>("modify"));

        Callback<TableColumn<Meet, String>, TableCell<Meet, String>> cellFactoryModify
                = //
                new Callback<TableColumn<Meet, String>, TableCell<Meet, String>>() {
            @Override
            public TableCell call(final TableColumn<Meet, String> param) {
                final TableCell<Meet, String> cell = new TableCell<Meet, String>() {

                    final Button modify = new Button("Modify");

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
                                Meet meet = getTableView().getItems().get(getIndex());
                                Disponibilite dispo = serviceDisp.findByTime(meet.getTime());
                                dispo.setEtat(0);
                                serviceDisp.Update(dispo);
                                service.Delete(meet);
                                dataList.clear();
                                dataList.addAll(service.Read());
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

        TableColumn joinCol = new TableColumn();
        joinCol.setCellValueFactory(new PropertyValueFactory<>("join"));
        Callback<TableColumn<Meet, String>, TableCell<Meet, String>> cellFactoryJoin
                = //
                new Callback<TableColumn<Meet, String>, TableCell<Meet, String>>() {
            @Override
            public TableCell call(final TableColumn<Meet, String> param) {
                final TableCell<Meet, String> cell = new TableCell<Meet, String>() {

                    final Button join = new Button("Join");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            join.setDisable(true);
                            Meet meet = getTableView().getItems().get(getIndex());
                            if (meet.getFeedback_id() == 0) {
                                join.setDisable(false);
                            }
                            join.setOnAction(event -> {
                                EngineOptions options
                                        = EngineOptions.newBuilder(HARDWARE_ACCELERATED).licenseKey("1BNDHFSC1FYI13WP1OWJBWGL2PRX6OPN2B11ZN74PR1U2CEHZHKZW0QF3PBNZ08JNDZ6J3")
                                                .build();
                                Engine engine = Engine.newInstance(options);

                                MediaDevices mediaDevices = engine.mediaDevices();

                                // Get all available video devices, e.g. web camera.
                                List<MediaDevice> videoDevices = mediaDevices.list(MediaDeviceType.VIDEO_DEVICE);
                                /*System.out.println("video");
                                for (MediaDevice v : videoDevices) {
                                    System.out.println(v);
                                }

                                System.out.println("audio");
                                // Get all available audio devices, e.g. microphone.
                                List<MediaDevice> audioDevices = mediaDevices.list(MediaDeviceType.AUDIO_DEVICE);
                                for (MediaDevice v : audioDevices) {
                                    System.out.println(v);
                                }*/

                                engine.permissions().set(RequestPermissionCallback.class, (params, tell) -> {
                                    PermissionType type = params.permissionType();
                                    if (type == PermissionType.VIDEO_CAPTURE || type == PermissionType.AUDIO_CAPTURE) {
                                        tell.grant();
                                    } else {
                                        tell.grant();
                                    }
                                });

                                Browser browser = engine.newBrowser();

                                SwingUtilities.invokeLater(() -> {
                                    // Creating Swing component for rendering web content
                                    // loaded in the given Browser instance.
                                    BrowserView view = BrowserView.newInstance(browser);
                                    Users h = serviceUser.findById(meet.getId_helper());
                                    meet.setHelperDisplay(h.getUsername());

                                    String title = "Meeting with " + meet.getHelperDisplay() + " - " + meet.getSpecialite();
                                    // Creating and displaying Swing app frame.
                                    JFrame frame = new JFrame(title);
                                    // Close Engine and onClose app window
                                    frame.addWindowListener(new WindowAdapter() {
                                        @Override
                                        public void windowClosing(WindowEvent e) {
                                            engine.close();
                                        }

                                        @Override
                                        public void windowClosed(WindowEvent we) {
                                            super.windowClosed(we); //To change body of generated methods, choose Tools | Templates.
                                            Platform.runLater(() -> {

                                                try {
                                                    FXMLLoader loader = new FXMLLoader(
                                                            getClass().getResource(
                                                                    "NewFeedbackFXML.fxml"
                                                            )
                                                    );
                                                    Scene scene = new Scene(loader.load()); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"
                                                    //Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
                                                    Stage app_stage = new Stage();//this accesses the window.
                                                    app_stage.initModality(Modality.WINDOW_MODAL);
                                                    app_stage.initOwner(((Node) event.getTarget()).getScene().getWindow());
                                                    app_stage.setScene(scene);

                                                    NewFeedbackFXMLController controller = loader.getController();
                                                    controller.initData(meet.getId());

                                                    app_stage.show();

                                                } catch (IOException ex) {
                                                }
                                            }
                                            );
                                        }

                                    });

                                    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                                    String path = "http://localhost:8000/" + meet.getId();
                                    System.out.println(path);
                                    JTextField addressBar = new JTextField(path);
                                    addressBar.addActionListener(e
                                            -> browser.navigation().loadUrl(addressBar.getText()));
                                    //frame.add(addressBar, BorderLayout.NORTH);
                                    JButton end = new JButton("test");
                                    end.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(java.awt.event.ActionEvent ae) {
                                            
                                            engine.close();
                                            frame.dispose();
                                        }
                                    });
                                    frame.add(view, BorderLayout.CENTER);
                                    frame.add(end, BorderLayout.NORTH);
                                    frame.setSize(800, 500);
                                    frame.setLocationRelativeTo(null);
                                    frame.setVisible(true);

                                    browser.navigation().loadUrl(addressBar.getText());
                                });
                            });

                            setGraphic(join);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };

        joinCol.setCellFactory(cellFactoryJoin);

        meets.getColumns().add(idHlpCol);
        meets.getColumns().add(idFdbCol);
        meets.getColumns().add(spcCol);
        meets.getColumns().add(timeCol);
        meets.getColumns().add(modCol);
        meets.getColumns().add(delCol);
        meets.getColumns().add(joinCol);
        dataList.addAll(service.ReadById(userId));
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

    @FXML
    private void Ajouter(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "ListHelpersFXML.fxml"
                    )
            );

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
            stage.setScene(
                    new Scene(loader.load())
            );
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(P2PFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
