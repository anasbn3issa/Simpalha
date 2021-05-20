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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javax.swing.JButton;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import services.ServiceDisponibilite;
import services.ServiceP2P;
import services.ServiceUsers;
import simpalha.FXMLDocumentController;
import utils.Constants;
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
    private ObservableList<Meet> dataListH = FXCollections.observableArrayList();
    @FXML
    private TableView<Meet> meetsH;

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

        fetchStudentMeets();
        fetchHelperMeets();

    }

    void fetchStudentMeets() {
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
                            String[] split = meet.getTime().split("->");

                            LocalDateTime now = LocalDateTime.now();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            LocalDateTime dateTime = LocalDateTime.parse(split[1], formatter);
                            Boolean isbofore = dateTime.isBefore(now);

                            if (meet.getEtat() == 1 | isbofore) {
                                modify.setDisable(true);
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
                            Meet meet = getTableView().getItems().get(getIndex());

                            delete.setOnAction(event -> {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?",
                                        ButtonType.YES, ButtonType.NO);
                                alert.showAndWait();

                                if (alert.getResult() == ButtonType.YES) {
                                    Disponibilite dispo = serviceDisp.findByTime(meet.getTime());
                                    dispo.setEtat(0);
                                    serviceDisp.Update(dispo);
                                    service.Delete(meet);
                                    dataList.clear();
                                    dataList.addAll(service.ReadById(userId));
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

        TableColumn joinCol = new TableColumn("Join Via");
        joinCol.setCellValueFactory(new PropertyValueFactory<>("join"));
        Callback<TableColumn<Meet, String>, TableCell<Meet, String>> cellFactoryJoin
                = //
                new Callback<TableColumn<Meet, String>, TableCell<Meet, String>>() {
            @Override
            public TableCell call(final TableColumn<Meet, String> param) {
                final TableCell<Meet, String> cell = new TableCell<Meet, String>() {

                    //final Button join = new Button("Join");
                    ChoiceBox<String> choix = new ChoiceBox(FXCollections.observableArrayList(
                            "QRCode", new Separator(), "Native browser"));

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {

                            Meet meet = getTableView().getItems().get(getIndex());
                            String[] split = meet.getTime().split("->");

                            LocalDateTime now = LocalDateTime.now();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            LocalDateTime dateTime = LocalDateTime.parse(split[1], formatter);
                            Boolean isbofore = dateTime.isBefore(now);
                            if (meet.getEtat() == 1 | isbofore) {
                                choix.setDisable(true);
                            }

                            choix.setOnAction((event) -> {
                                switch (choix.getValue().toLowerCase()) {
                                    case "qrcode": {
                                        // GENERATE QR CODE
                                        ByteArrayOutputStream out = QRCode.from(Constants.BASE_URL + meet.getId()).to(ImageType.PNG).withSize(200, 200).stream();
                                        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());

                                        // SHOW QR CODE
                                        BorderPane root = new BorderPane();
                                        Image image = new Image(in);
                                        ImageView view = new ImageView(image);
                                        view.setStyle("-fx-stroke-width: 2; -fx-stroke: blue");
                                        root.setCenter(view);
                                        Scene scene = new Scene(root, 200, 200);
                                        Stage app_stage = new Stage();//this accesses the window.
                                        app_stage.initModality(Modality.APPLICATION_MODAL);
                                        app_stage.setScene(scene);
                                        app_stage.show();
                                        break;
                                    }
                                    default: {
                                        Engine engine = Engine.newInstance(
                                                EngineOptions.newBuilder(HARDWARE_ACCELERATED)
                                                        .licenseKey(Constants.JXBROWSER_LSC)
                                                        .build());

                                        MediaDevices mediaDevices = engine.mediaDevices();

                                        // Get all available video devices, e.g. web camera.
                                        List<MediaDevice> videoDevices = mediaDevices.list(MediaDeviceType.VIDEO_DEVICE);

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
                                                            app_stage.initModality(Modality.APPLICATION_MODAL);
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
                                            String path = Constants.BASE_URL + meet.getId();
                                            System.out.println(path);
                                            JTextField addressBar = new JTextField(path);
                                            addressBar.addActionListener(e
                                                    -> browser.navigation().loadUrl(addressBar.getText()));
                                            //frame.add(addressBar, BorderLayout.NORTH);

                                            frame.add(view, BorderLayout.CENTER);
                                            frame.setSize(800, 500);
                                            frame.setLocationRelativeTo(null);
                                            frame.setVisible(true);

                                            browser.navigation().loadUrl(addressBar.getText());
                                        });
                                    }
                                }
                            });

                            setGraphic(choix);
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
        System.out.println("Student");
        System.out.println(userId);
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

    void fetchHelperMeets() {

        TableColumn<Meet, String> idStdCol = new TableColumn<>("Student");
        idStdCol.setCellValueFactory(new PropertyValueFactory<>("StudentDisplay"));

        TableColumn<Meet, String> idFdbCol2 = new TableColumn<>("Feedback");
        idFdbCol2.setCellValueFactory(new PropertyValueFactory<>("feedbackDisplay"));

        TableColumn<Meet, String> spcCol2 = new TableColumn<>("Specialite");
        spcCol2.setCellValueFactory(new PropertyValueFactory<>("specialite"));

        TableColumn<Meet, String> timeCol2 = new TableColumn<>("Time");
        timeCol2.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn modCol2 = new TableColumn("Action");
        modCol2.setCellValueFactory(new PropertyValueFactory<>("modify"));

        Callback<TableColumn<Meet, String>, TableCell<Meet, String>> cellFactoryModify2
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
                            String[] split = meet.getTime().split("->");

                            LocalDateTime now = LocalDateTime.now();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            LocalDateTime dateTime = LocalDateTime.parse(split[1], formatter);
                            Boolean isbofore = dateTime.isBefore(now);

                            if (meet.getEtat() == 1 | isbofore) {
                                modify.setDisable(true);
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

        modCol2.setCellFactory(cellFactoryModify2);

        TableColumn delCol2 = new TableColumn();
        delCol2.setCellValueFactory(new PropertyValueFactory<>("delete"));
        Callback<TableColumn<Meet, String>, TableCell<Meet, String>> cellFactoryDelete2
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
                            Meet meet = getTableView().getItems().get(getIndex());

                            delete.setOnAction(event -> {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?",
                                        ButtonType.YES, ButtonType.NO);
                                alert.showAndWait();

                                if (alert.getResult() == ButtonType.YES) {
                                    Disponibilite dispo = serviceDisp.findByTime(meet.getTime());
                                    dispo.setEtat(0);
                                    serviceDisp.Update(dispo);
                                    service.Delete(meet);
                                    dataListH.clear();
                                    dataListH.addAll(service.ReadStudentsById(userId));
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

        delCol2.setCellFactory(cellFactoryDelete2);

        TableColumn joinCol2 = new TableColumn("Join Via");
        joinCol2.setCellValueFactory(new PropertyValueFactory<>("join"));
        Callback<TableColumn<Meet, String>, TableCell<Meet, String>> cellFactoryJoin2
                = //
                new Callback<TableColumn<Meet, String>, TableCell<Meet, String>>() {
            @Override
            public TableCell call(final TableColumn<Meet, String> param) {
                final TableCell<Meet, String> cell = new TableCell<Meet, String>() {

                    //final Button join = new Button("Join");
                    ChoiceBox<String> choix = new ChoiceBox(FXCollections.observableArrayList(
                            "QRCode", new Separator(), "Native browser"));

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {

                            Meet meet = getTableView().getItems().get(getIndex());
                            String[] split = meet.getTime().split("->");

                            LocalDateTime now = LocalDateTime.now();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            LocalDateTime dateTime = LocalDateTime.parse(split[1], formatter);
                            Boolean isbofore = dateTime.isBefore(now);
                            if (meet.getEtat() == 1 | isbofore) {
                                choix.setDisable(true);
                            }

                            choix.setOnAction((event) -> {
                                switch (choix.getValue().toLowerCase()) {
                                    case "qrcode": {
                                        // GENERATE QR CODE
                                        ByteArrayOutputStream out = QRCode.from(Constants.BASE_URL + meet.getId()).to(ImageType.PNG).withSize(200, 200).stream();
                                        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());

                                        // SHOW QR CODE
                                        BorderPane root = new BorderPane();
                                        Image image = new Image(in);
                                        ImageView view = new ImageView(image);
                                        view.setStyle("-fx-stroke-width: 2; -fx-stroke: blue");
                                        root.setCenter(view);
                                        Scene scene = new Scene(root, 200, 200);
                                        Stage app_stage = new Stage();//this accesses the window.
                                        app_stage.initModality(Modality.APPLICATION_MODAL);
                                        app_stage.setScene(scene);
                                        app_stage.show();
                                        break;
                                    }
                                    default: {
                                        Engine engine = Engine.newInstance(
                                                EngineOptions.newBuilder(HARDWARE_ACCELERATED)
                                                        .licenseKey(Constants.JXBROWSER_LSC)
                                                        .build());
                                        MediaDevices mediaDevices = engine.mediaDevices();

                                        // Get all available video devices, e.g. web camera.
                                        List<MediaDevice> videoDevices = mediaDevices.list(MediaDeviceType.VIDEO_DEVICE);

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
                                                            app_stage.initModality(Modality.APPLICATION_MODAL);
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
                                            String path = Constants.BASE_URL + meet.getId();
                                            System.out.println(path);
                                            JTextField addressBar = new JTextField(path);
                                            addressBar.addActionListener(e
                                                    -> browser.navigation().loadUrl(addressBar.getText()));
                                            //frame.add(addressBar, BorderLayout.NORTH);

                                            frame.add(view, BorderLayout.CENTER);
                                            frame.setSize(800, 500);
                                            frame.setLocationRelativeTo(null);
                                            frame.setVisible(true);

                                            browser.navigation().loadUrl(addressBar.getText());
                                        });
                                    }
                                }
                            });

                            setGraphic(choix);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };

        joinCol2.setCellFactory(cellFactoryJoin2);

        meetsH.getColumns().add(idStdCol);
        meetsH.getColumns().add(idFdbCol2);
        meetsH.getColumns().add(spcCol2);
        meetsH.getColumns().add(timeCol2);
        meetsH.getColumns().add(modCol2);
        meetsH.getColumns().add(delCol2);
        meetsH.getColumns().add(joinCol2);
        dataListH.addAll(service.ReadStudentsById(userId));
        System.out.println("HS");
        System.out.println(service.ReadStudentsById(userId));
        meetsH.getItems().addAll(dataListH);

        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Meet> filteredData2 = new FilteredList<>(dataListH, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        tfsearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData2.setPredicate(meet -> {
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
        SortedList<Meet> sortedData2 = new SortedList<>(filteredData2);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData2.comparatorProperty().bind(meets.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        meetsH.setItems(sortedData2);

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
                            "/simpalha/P2P/ListHelpersFXML.fxml"
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

    @FXML
    private void goToViewPosts(MouseEvent event) {
        //note that on this line you can substitue "Screen2.fxml" for a string chosen prior to this line.
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("/simpalha/post/ViewPosts.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }

    @FXML
    private void showQuizz(MouseEvent event) {
        //note that on this line you can substitue "Screen2.fxml" for a string chosen prior to this line.
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("/simpalha/quizz/FXMLQuizz.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }

    @FXML
    private void showRESOURCES(MouseEvent event) {
        //note that on this line you can substitue "Screen2.fxml" for a string chosen prior to this line.
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("/simpalha/ressources/FXMLDocument.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }

    @FXML
    private void showRec(ContextMenuEvent event) {
        //note that on this line you can substitue "Screen2.fxml" for a string chosen prior to this line.
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("/simpalha/ressources/FXMLDocument.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }

    @FXML
    private void showProfile(MouseEvent event) {
        //note that on this line you can substitue "Screen2.fxml" for a string chosen prior to this line.
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("/simpalha/users/Profile.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }

    @FXML
    private void logout(MouseEvent event) {

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
