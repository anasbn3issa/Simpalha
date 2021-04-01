/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.reclamation;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import entities.Reclamation;
import entities.Users;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import services.IServiceReclamation;
import services.ServiceNotification;
import services.ServiceUsers;
import simpalha.admin.FXMLDocumentController;
import simpalha.admin.quizz.FXMLAdminQuizzTableController;
import simpalha.notification.FXMLNotificationController;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import utils.UserSession;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class ListerReclamationController implements Initializable {

    @FXML
    private FontAwesomeIcon btNotificationShow;
    @FXML
    private TableView<Reclamation> listeRec;
    private IServiceReclamation service;
    private UserSession usr;
    @FXML
    private Button annuler;
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
        // TODO
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

        TableColumn<Reclamation, String> dateRec = new TableColumn<>("dateRec");
        dateRec.setCellValueFactory(new PropertyValueFactory<>("dateRec"));

        TableColumn<Reclamation, String> dateResolution = new TableColumn<>("dateResolution");
        dateResolution.setCellValueFactory(new PropertyValueFactory<>("dateResolution"));

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
                                System.out.println(dest.getAbsolutePath());
                                image.setImage(new javafx.scene.image.Image(dest.toURI().toURL().toString()));
                                image.setFitWidth(40);
                                image.setFitHeight(40);
                                setGraphic(image);
                                setText(null);
                            } catch (MalformedURLException ex) {
                                Logger.getLogger(ListerReclamationController.class.getName()).log(Level.SEVERE, null, ex);
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
        modCol.setCellValueFactory(new PropertyValueFactory<>("modifier"));
        Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>> cellFactoryModify
                = //
                new Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>>() {
            @Override
            public TableCell call(final TableColumn<Reclamation, String> param) {
                final TableCell<Reclamation, String> cell = new TableCell<Reclamation, String>() {

                    final Button modifier = new Button("Modifier");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            modifier.setOnAction(event -> {
                                Reclamation Reclamation = getTableView().getItems().get(getIndex());

                                try {
                                    FXMLLoader loader = new FXMLLoader(
                                            getClass().getResource(
                                                    "/simpalha/reclamation/ModifierReclamation.fxml"
                                            )
                                    );

                                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
                                    stage.setScene(
                                            new Scene(loader.load())
                                    );

                                    ModifierReclamationController controller = loader.getController();
                                    controller.initData(String.valueOf(Reclamation.getId()));

                                    stage.show();
                                } catch (IOException ex) {
                                    Logger.getLogger(ListerReclamationController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                
                                
                            });

                            setGraphic(modifier);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };
        modCol.setCellFactory(cellFactoryModify); //ajout boutton au sein d colone

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

                                String title = "réclamation supprimée avec succès ";
                                TrayNotification tray = new TrayNotification();
                                AnimationType type = AnimationType.POPUP;
                                tray.setAnimationType(type);
                                tray.setTitle(title);
                                tray.setNotificationType(NotificationType.SUCCESS);
                                tray.showAndDismiss(Duration.millis(3000));
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

        listeRec.getColumns().add(Id);
        listeRec.getColumns().add(Idreportee);
        listeRec.getColumns().add(Idreported);
        listeRec.getColumns().add(description);
        listeRec.getColumns().add(imCol);
        listeRec.getColumns().add(dateRec);
        listeRec.getColumns().add(dateResolution);
        listeRec.getColumns().add(modCol);
        listeRec.getColumns().add(delCol);

        List<Reclamation> list = service.Read();
        System.out.println(list);
        for (Reclamation Reclamation : list) {
            System.out.println(Reclamation);
            listeRec.getItems().add(Reclamation);
        }
    }

    @FXML
    private void ajouter(ActionEvent event) {
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("/simpalha/reclamation/AjouterReclamation.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }

    @FXML
    private void annuler(ActionEvent event) {
       Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("/simpalha/reclamation/ReclamationSuggestion.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
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

    @FXML
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
    private void RecSugg(MouseEvent event) {
    }

}
