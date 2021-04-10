/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.admin.reclamation;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import entities.Reclamation;
import entities.Users;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import services.IServiceReclamation;
import services.ServiceNotification;
import services.ServiceUsers;
import simpalha.admin.FXMLDocumentController;
import simpalha.admin.quizz.FXMLAdminQuizzTableController;
import simpalha.notification.FXMLNotificationController;
import simpalha.reclamation.ListerReclamationController;
import simpalha.reclamation.ModifierReclamationController;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import utils.UserSession;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class ListeReclamationController implements Initializable {

    @FXML
    private FontAwesomeIcon btNotificationShow;
    @FXML
    private Button LineChart;
    @FXML
    private TableView<Reclamation> listeRec;
    private IServiceReclamation service;
    private UserSession usr;

    //observalble list to store data
    private final ObservableList<Reclamation> dataList = FXCollections.observableArrayList();
    @FXML
    private TextField rechercher;
    @FXML
    private Button back;
    @FXML
    private Text currentUserNameLabel;
    ServiceUsers serviceUsers;
    UserSession userSession;
    Users currentUser;
    int userId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        service = new IServiceReclamation();
        
        serviceUsers = new ServiceUsers();
        userSession = UserSession.getInstace(0);
        userId = userSession.getUserid();
        currentUser = serviceUsers.findById(userId);

        //currentUserNameLabel.setText(currentUser.getUsername());

        usr = UserSession.getInstace(0);
        System.out.println(usr);
        launchServiceNotification();

        //interface liste reclamations
        TableColumn<Reclamation, String> Id = new TableColumn<>("Id");
        Id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Reclamation, String> Idreportee = new TableColumn<>("Idreportee");
        Idreportee.setCellValueFactory(new PropertyValueFactory<>("Idreportee"));

        TableColumn<Reclamation, String> Idreported = new TableColumn<>("Idreported");
        Idreported.setCellValueFactory(new PropertyValueFactory<>("Idreported"));

        TableColumn<Reclamation, String> description = new TableColumn<>("description");
        description.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Reclamation, Timestamp> image = new TableColumn<>("image");
        image.setCellValueFactory(new PropertyValueFactory<>("image"));

        TableColumn<Reclamation, String> dateRec = new TableColumn<>("dateRec");
        dateRec.setCellValueFactory(new PropertyValueFactory<>("dateRec"));

        TableColumn<Reclamation, String> dateResolution = new TableColumn<>("dateResolution");
        dateResolution.setCellValueFactory(new PropertyValueFactory<>("dateResolution"));
        
        TableColumn<Reclamation, String> Status = new TableColumn<>("Status");
        Status.setCellValueFactory(new PropertyValueFactory<>("Status"));
        
        
        //colone image
        
        TableColumn imCol = new TableColumn("image");  
        Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>> cellFactoryImage
                = //
                new Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>>() {
            @Override
            public TableCell call(final TableColumn<Reclamation, String> param) {
                final TableCell<Reclamation, String> cell = new TableCell<Reclamation, String>() {

                    ImageView image = new ImageView();

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {

                            try {
                                Reclamation Reclamation = getTableView().getItems().get(getIndex());
                                String dir = System.getProperty("user.dir");//get project source path
                                File dest = new File(dir + "\\ressources\\" + Reclamation.getFileSelected());//add the full path /ressources + file name
                                Image img = new Image(dest.toURI().toURL().toString());
                                image.setImage(img);
                                image.setFitWidth(40);
                                image.setFitHeight(40);
                                setGraphic(image);
                                setText(null);
                            } catch (MalformedURLException ex) {
                                Logger.getLogger(ListeReclamationController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    }
                };
                return cell;
            }
        };
        imCol.setCellFactory(cellFactoryImage); //ajout boutton au sein d colone

        //colone opération
        TableColumn modCol = new TableColumn("Opération");  
      
        //colone supprimer
        TableColumn delCol = new TableColumn();
        delCol.setCellValueFactory(new PropertyValueFactory<>("supprimer"));
        Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>> cellFactoryDelete
                = //
                new Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>>() {
            @Override
            public TableCell call(final TableColumn<Reclamation, String> param) {
                final TableCell<Reclamation, String> cell = new TableCell<Reclamation, String>() {

                    final Button supprimer = new Button("supprimer");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            supprimer.setOnAction(event -> {

                                Reclamation Reclamation = getTableView().getItems().get(getIndex());
                                service.Delete(Reclamation);
                                System.out.println(Reclamation);
                                String title = "claim successfully deleted ";
                                TrayNotification tray = new TrayNotification();
                                AnimationType type = AnimationType.POPUP;
                                tray.setAnimationType(type);
                                tray.setTitle(title);
                                tray.setNotificationType(NotificationType.SUCCESS);
                                tray.showAndDismiss(javafx.util.Duration.millis(3000));
                                
                                //refreche
                                listeRec.getItems().clear();
                                listeRec.getItems().addAll(service.Read());
                            });

                            setGraphic(supprimer);
                            setText(null);
                        }

                    }
                };
                return cell;
            }
        };
        delCol.setCellFactory(cellFactoryDelete);  //ajout boutton au sein d colone

        //colone Details
        TableColumn detcol = new TableColumn();
        detcol.setCellValueFactory(new PropertyValueFactory<>("Details"));
        Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>> cellFactoryDetails
                = //
                new Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>>() {
            @Override
            public TableCell call(final TableColumn<Reclamation, String> param) {
                final TableCell<Reclamation, String> cell = new TableCell<Reclamation, String>() {

                    final Button Details = new Button("Details");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            //Details.setDisable(true);
                            Reclamation Reclamation = getTableView().getItems().get(getIndex());
                            LocalDate d1 = LocalDate.parse(java.time.LocalDate.now().toString(), DateTimeFormatter.ISO_LOCAL_DATE);
                            LocalDate d2 = LocalDate.parse(Reclamation.getDateRec().toString(), DateTimeFormatter.ISO_LOCAL_DATE);
                            Duration diff = Duration.between(d2.atStartOfDay(), d1.atStartOfDay());

                            long diffDays = diff.toDays();
                            if (diffDays > 3 && (Reclamation.getValidHelper() != 1 | Reclamation.getValidStudent() != 1)) {
                                Details.setDisable(false);
                            }
                            Details.setOnAction(event -> {

                                try {
                                    FXMLLoader loader = new FXMLLoader(
                                            getClass().getResource(
                                                    "/simpalha/admin/reclamation/DetailsReclamation.fxml"
                                            )
                                    );

                                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
                                    stage.setScene(
                                            new Scene(loader.load())
                                    );

                                    DetailsReclamationController controller = loader.getController();
                                    controller.initData(String.valueOf(Reclamation.getId()));

                                    stage.show();
                                } catch (IOException ex) {
                                    Logger.getLogger(ListerReclamationController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            });

                            setGraphic(Details);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };
        detcol.setCellFactory(cellFactoryDetails);

        listeRec.getColumns().add(Id);
        listeRec.getColumns().add(Idreportee);
        listeRec.getColumns().add(Idreported);
        listeRec.getColumns().add(description);
        listeRec.getColumns().add(imCol);
        listeRec.getColumns().add(dateRec);
        listeRec.getColumns().add(dateResolution);
        listeRec.getColumns().add(modCol);
        listeRec.getColumns().add(delCol);
        listeRec.getColumns().add(detcol);
        listeRec.getColumns().add(Status);

        dataList.addAll(service.Read());
        listeRec.getItems().addAll(dataList);
        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Reclamation> filteredData = new FilteredList<>(dataList, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        rechercher.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(rec -> {
                // If filter text is empty, display all persons.
                System.out.println(rec);
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (rec.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches id.
                } else {
                    return false; // Does not match.
                }
            });
        });
        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<Reclamation> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(listeRec.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        listeRec.setItems(sortedData);
    }

    @FXML
    private void LineChart(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "LineChart.fxml"
                    )
            );

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
            stage.setScene(
                    new Scene(loader.load())
            );

            LineChartController controller = loader.getController();

            stage.show();
        } catch (IOException ex) {
        }
    }

    public void launchServiceNotification() {
        ServiceNotification sn = new ServiceNotification(usr.getUserid());

        Thread t = new Thread(sn);
        t.setDaemon(true);
        t.start();
    }

    @FXML
    private void notificationsShow(MouseEvent event) {
        FXMLLoader modal = new FXMLLoader(getClass().getResource("/simpalha/notification/FXMLNotification.fxml"));
        Parent root = null;
        try {
            root = modal.load();
        } catch (IOException io) {
        };

        FXMLNotificationController editModal = modal.getController();

        editModal.reloadAllNotificationsList(usr.getUserid());

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Notifications");
        stage.showAndWait();
    }

    // f hethy lezm n3awd nredefini l bouton eli bsh yhezni lel page ViewPosts (contact wajdi)
    @FXML
    private void goToViewPosts(MouseEvent event) {
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

    private void AddNewPost(ActionEvent event) {

        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("/simpalha/post/AddNewPost.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }

    @FXML
    private void showP2P(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/simpalha/admin/P2P/P2PFXML.fxml"
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

    private void candidatures(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/simpalha/users/CandidatureUser.fxml"
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
    private void profile(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/simpalha/users/Profile.fxml"
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
    private void showQuizz(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/simpalha/admin/quizz/FXMLQuizzTable.fxml"
                    )
            );

            Parent root = loader.load();

            FXMLAdminQuizzTableController tableController = loader.getController();

            tableController.initializeUserId(usr.getUserid());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
            stage.setScene(
                    new Scene(root)
            );
            stage.setTitle("Quiz Section");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void back(ActionEvent event) {
              Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("/simpalha/admin/reclamation/ReclamationSuggestionAdmin.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }
}
