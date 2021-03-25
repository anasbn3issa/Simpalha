/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.users;

import entities.Candidature;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import services.ServiceCandidature;
import utils.UserSession;

/**
 * FXML Controller class
 *
 * @author win10
 */
public class CandidatureUserController implements Initializable {

    @FXML
    private Button upload;
    @FXML
    private Button confirm;
    @FXML
    private TextField screen;
    @FXML
    private ImageView tfimg;
    @FXML
    private ComboBox<String> comb;
    private ServiceCandidature srv;
    private File selectedFile;
    ImageView imageView = new ImageView();
    private ListView liste;
    private int userid;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        userid = UserSession.getInstace(0).getUserid();
        System.out.println(userid);
        srv = new ServiceCandidature();

        ObservableList<String> List = FXCollections.observableArrayList("Java", "UML", "math");
        comb.setItems(List);

    }

    @FXML
    private void goToViewPosts(MouseEvent event) {
    }

    @FXML
    private void showP2P(MouseEvent event) {
    }

    @FXML
    private void UploadFichier(ActionEvent event) {

        FileChooser fc = new FileChooser();

        selectedFile = fc.showOpenDialog(null);

    }

    @FXML
    private void Confirmer(ActionEvent event) {
        String file="";
        if (selectedFile != null) {

            file = copy(selectedFile);
        }
        Candidature cdr = new Candidature(comb.getValue(), file, userid);
        srv.Create(cdr);
        Candidature can = srv.findby(userid);
        System.out.println(can);
        

    }

    @FXML
    private void selectspec(ActionEvent event) {
        String s = comb.getSelectionModel().getSelectedItem().toString();
        screen.setText(s);
    }

    private String copy(File from) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

        String dir = System.getProperty("user.dir");//get project source path
        File dest = new File(dir + "\\ressources\\" +sdf1.format(timestamp)+ from.getName());//add the full path /ressources + file name
        
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
                Logger.getLogger(CandidatureUserController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(CandidatureUserController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            try {
                is.close();
                os.close();
            } catch (IOException ex) {
                Logger.getLogger(CandidatureUserController.class.getName()).log(Level.SEVERE, null, ex);
            }
            /////////////////
        }
        return dest.getName();
    }
}