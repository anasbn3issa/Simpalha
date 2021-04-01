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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
import utils.JavaSoundRecorder;
import utils.UserSession;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class ModifierReclamationController implements Initializable {

    private IServiceReclamation service;

    private String idRec;

    @FXML
    private FontAwesomeIcon btNotificationShow;
    @FXML
    private Button add;
    @FXML
    private TextField id;
    @FXML
    private TextArea des;
    @FXML
    private ToggleButton Record;
    @FXML
    private Button upload;
    @FXML
    private ImageView image;
    @FXML
    private Button valider;
    @FXML
    private Button annuler;
    private TextField helper;
    private File FileSelected;
    private String filename;
    private String wavfile;
    private JavaSoundRecorder javaSoundRecorder;
    private UserSession usr;
    private Reclamation rec;
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
        //run les paramétre avant l'execution d l'interface
        Platform.runLater(() -> {
            service = new IServiceReclamation();

            serviceUsers = new ServiceUsers();
            userSession = UserSession.getInstace(0);
            userId = userSession.getUserid();
            currentUser = serviceUsers.findById(userId);

            //currentUserNameLabel.setText(currentUser.getUsername());

            usr = UserSession.getInstace(0);
            System.out.println(usr);
            launchServiceNotification();

            rec = service.findById(Integer.valueOf(idRec));

            des.setText(rec.getDescription());
            helper.setText(rec.getIdreported());
        }
        );
    }

    @FXML
    private void modifierRec(ActionEvent event) {
        Reclamation rec = service.findById(Integer.valueOf(idRec));
        rec.setDescription(des.getText());
        service.Update(rec);

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "ListerReclamation.fxml"
                    )
            );

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
            stage.setScene(
                    new Scene(loader.load())
            );
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ModifierReclamationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String title = "claim successfully edited ";
        TrayNotification tray = new TrayNotification();
        AnimationType type = AnimationType.POPUP;
        tray.setAnimationType(type);
        tray.setTitle(title);
        tray.setNotificationType(NotificationType.SUCCESS);
        tray.showAndDismiss(Duration.millis(3000));
    }

    //recuperer les données
    public void initData(String id) {
        idRec = id;
    }

    @FXML
    private void Record(ActionEvent event) {
        if (Record.isSelected()) {
            System.out.println("on");
            javaSoundRecorder = new JavaSoundRecorder();
            Thread thread = new Thread(javaSoundRecorder);
            thread.start();
        } else {
            System.out.println("off");
            wavfile = javaSoundRecorder.finish();
            javaSoundRecorder.cancel();
        }
    }

    @FXML
    private void upload(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(System.getProperty("user.home") + "\\Desktop"));
        fc.setTitle("Veuillez choisir l'image");

        FileSelected = fc.showOpenDialog(null);
        if (FileSelected != null) {
            try {
                filename = copy(FileSelected);
                image.setImage(new javafx.scene.image.Image(FileSelected.toURI().toURL().toString()));
            } catch (MalformedURLException ex) {
                Logger.getLogger(AjouterReclamationController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private String copy(File from) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

        String dir = System.getProperty("user.dir");//get project source path
        File dest = new File(dir + "\\ressources\\" + sdf1.format(timestamp) + from.getName());//add the full path /ressources + file name

//check if folder ressources is created, sinon create it
        File file = new File(dir + "\\ressources");
        file.mkdirs();
        /////
        //COPY FILE OPERATION
        InputStream is = null;
        OutputStream os = null;
        try {
            try {
                is = new FileInputStream(from);
                os = new FileOutputStream(dest);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(AjouterReclamationController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(AjouterReclamationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            try {
                is.close();
                os.close();
            } catch (IOException ex) {
                Logger.getLogger(AjouterReclamationController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return dest.getName();
    }

    @FXML
    private void valider(ActionEvent event) {

        int id = usr.getUserid();
        Users user = serviceUsers.findById(id);
        String usertype = "Student";
        if (user.getSpecialité() != null) {
            usertype = "Helper";
        }
        service.ValidateStatus(rec, usertype);

    }

    @FXML
    private void annulerRec(ActionEvent event) {
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("/simpalha/reclamation/ListerReclamation.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

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
        FXMLLoader modal = new FXMLLoader(getClass().getResource("simpalha/notification/FXMLNotification.fxml"));
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
