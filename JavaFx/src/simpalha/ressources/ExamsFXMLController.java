/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.ressources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.io.*;
import entities.Ressources;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.sql.JDBCType.NULL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import services.ServiceRessources;
import utils.UserSession;

/**
 *
 * @author α Ω
 */
public class ExamsFXMLController implements Initializable {

    private Label label;
    private Label labelaffiche;
    private TextField tfsupp;
    private ServiceRessources sr;
    @FXML
    private TextField tfsearch;
    private Ressources R1;
    @FXML
    private TableView<Ressources> examslist;
    @FXML
    private ComboBox<String> module;

    List<Ressources> list2;
    @FXML
    private Text title;

//    private void handleButtonAction(ActionEvent event) {
//        System.out.println("You clicked me!");
//        label.setText("Hello World!");
//    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("EXAMS TIME!");
            alert.setContentText("Exams Countdown.. already stressing?");
            alert.setHeaderText("HERE TO HELP YOU ");
            alert.showAndWait();

            //afficher les elements initialement:
            new animatefx.animation.Shake(title).play();

            sr = new ServiceRessources();

            TableColumn<Ressources, String> titleCol = new TableColumn<>("Title");
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

            TableColumn<Ressources, String> descriptionCol = new TableColumn<>("Description");
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

            TableColumn<Ressources, String> pathCol = new TableColumn<>("Path");
            pathCol.setCellValueFactory(new PropertyValueFactory<>("path"));

            TableColumn<Ressources, String> pathModule = new TableColumn<>("Module");
            pathModule.setCellValueFactory(new PropertyValueFactory<>("module"));

//            TableColumn<Ressources, String> modifyCol = new TableColumn<>("Modify");
//            pathCol.setCellValueFactory(new PropertyValueFactory<>("buttonModify"));
            //preparation de la structure
            examslist.getColumns().add(titleCol);
            examslist.getColumns().add(descriptionCol);
            examslist.getColumns().add(pathCol);
            examslist.getColumns().add(pathModule);
            //affichage
            list2 = sr.ReadExam();
            System.out.println(list2);
            //R1 = new Ressources(1, "aaa", "aaaa", "aaaa", "aaaaaaaaa");
            //examslist.getItems().add(R1);
            examslist.getItems().clear();
            examslist.getItems().addAll(list2);

            //combobox module
            module.getItems().add("All");
            List<String> list = sr.ReadModule();
            for (int i = 0; i < list.size(); i++) {
                module.getItems().add(list.get(i));
            }

            //list items via combobox
            module.setOnAction(e -> {
                try {
                    String moduleSelected = module.getValue();
                    System.out.println(moduleSelected);
                    if (moduleSelected.equals("All")) {
                    } else {
                        System.out.println(sr.ExamfromCombobox(moduleSelected));
                        examslist.getItems().clear();
                        examslist.getItems().addAll(sr.ExamfromCombobox(moduleSelected));
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ExamsFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            //Rech avancée +Title
//            ServiceRessources sr2 = ServiceRessources.getInstance();
//            ObservableList<Ressources> resslist2 = FXCollections.observableArrayList(sr2.Read());
//
//            FilteredList<Ressources> filteredData = new FilteredList<>(resslist2, b -> true);
//            tfsearch.textProperty().addListener(((observable, oldValue, newValue) -> {
//
//                filteredData.setPredicate(e -> {
//                    if (newValue == null || newValue.isEmpty()) {
//                        return true;
//                    }
//                    String lowerCaseFilter = newValue.toLowerCase();
//                    if (e.getTitle().toLowerCase().contains(lowerCaseFilter)) {
//                        return true;
//                    } else {
//                        return false;
//                    }
//                });
//            }));
//            SortedList<Ressources> sortedData = new SortedList<>(filteredData);
//            sortedData.comparatorProperty().bind(examslist.comparatorProperty());
//            examslist.setItems(sortedData);
//            
//            
//    API MUSIC
            final URL resource = getClass().getResource("motivation.mp3");
            AudioClip audioClip = new AudioClip(resource.toExternalForm());
            audioClip.play();

        } catch (SQLException ex) {
            Logger.getLogger(ExamsFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //acces page wajdi via menu a gche
    @FXML
    private void showP2P(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "P2P/P2PFXML.fxml"
                    )
            );

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
            stage.setScene(
                    new Scene(loader.load())
            );
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ExamsFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //acces page cyrine via menu a gche
    @FXML
    private void showRESOURCES(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "ressources/FXMLDocument.fxml"
                    )
            );

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
            stage.setScene(
                    new Scene(loader.load())
            );
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ExamsFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //retour à la page d'acceuil
    @FXML
    private void PagePrecedente(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "FXMLDocument.fxml"
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

    /*
    private File getPDF(Ressources R1,String path)
    {        //Generating pdf:
            //1)file generated's name
            String dir = System.getProperty("user.dir");//get project source path
            File name = new File(path+"\\"+R1.getTitle()+".pdf");//add the full path /ressources + file name
        try {
      
            //2)Create a document object
            Document document = new Document();
            //3) using pdf writer to write on this document
            PdfWriter.getInstance(document, new FileOutputStream(name));
            //Opening the document
            document.open();
            //load content ( you want to add)
            Paragraph title = new Paragraph(R1.getTitle());
            Paragraph description = new Paragraph(R1.getDescription());
            Paragraph header1 = new Paragraph("COPYRIGHT : SIMPALHA");
            Paragraph titleheader = new Paragraph("Exams's title:");
            Paragraph descriptionheader = new Paragraph("Exam's Description, tips maybe?");
            Paragraph imageheader = new Paragraph("Exam's screenshot");
            //add content
            document.addTitle("Simpalha's resource");
            document.add(header1);
            document.add(titleheader);
            document.add(title);
            document.add(descriptionheader);
            document.add(description);
            document.add(imageheader);

            //!!adding image VERIFYYYYYYYYYYYYY
            //  document.add(Image.getInstance(dir + "\\ressources\\" + R1.getTitle()))
//        Image image = new Image(dir + "\\ressources\\" + R1.getTitle());
//        document.add((Element) image);
            //close content
            document.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex);
        }
    return name;
}*/
    private File getPDF(Ressources R1) {        //Generating pdf:
        //1)file generated's name
        String dir = System.getProperty("user.dir");//get project source path
        File name = new File(dir + "\\ressources\\EXAMS\\" + R1.getTitle() + ".pdf");//add the full path /ressources + file name
        try {

            //2)Create a document object
            Document document = new Document();
            //3) using pdf writer to write on this document
            PdfWriter.getInstance(document, new FileOutputStream(name));
            //Opening the document
            document.open();
            //load content ( you want to add)
            Paragraph title = new Paragraph(R1.getTitle());
            Paragraph description = new Paragraph(R1.getDescription());
            Paragraph header1 = new Paragraph("COPYRIGHT : SIMPALHA");
            Paragraph titleheader = new Paragraph("Exams's title:");
            Paragraph descriptionheader = new Paragraph("Exam's Description, tips maybe?");
            Paragraph imageheader = new Paragraph("Exam's screenshot");
            //add content
            document.addTitle("Simpalha's resource");
            document.add(header1);
            document.add(titleheader);
            document.add(title);
            document.add(descriptionheader);
            document.add(description);
            document.add(imageheader);

            //!!adding image VERIFYYYYYYYYYYYYY
            //  document.add(Image.getInstance(dir + "\\ressources\\" + R1.getTitle()))
//        Image image = new Image(dir + "\\ressources\\" + R1.getTitle());
//        document.add((Element) image);
            //close content
            document.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex);
        }
        return name;
    }

    @FXML
    private void GetExams(ActionEvent event) throws DocumentException, FileNotFoundException {

        //Recuperate resource selected :
//            R1 = examslist.getSelectionModel().getSelectedItem();
        GeneratingPDF GPDF = new GeneratingPDF();
        GPDF.GeneratePdf(R1);
//
//        try {
//
//            //Recuperate resource selected :
//            R1 = examslist.getSelectionModel().getSelectedItem();
//            //Generating pdf:
//
//            //1)file generated's name
//            String dir = System.getProperty("user.dir");//get project source path
//            System.out.println(dir);
//            File name = new File(dir + "\\ressources\\EXAMS\\" + R1.getTitle() + ".pdf");//add the full path /ressources + file name
//            //File droppath = new File(dir + "\\ressources\\PDFS\\" + R1.getTitle()+".pdf");
//
//            //2)Create a document object
//            Document document = new Document();
//            //3) using pdf writer to write on this document
//            PdfWriter.getInstance(document, new FileOutputStream(name));
//            //Opening the document
//            document.open();
//            //load content ( you want to add)
//            Paragraph title = new Paragraph(R1.getTitle());
//            Paragraph description = new Paragraph(R1.getDescription());
//            Paragraph header1 = new Paragraph("COPYRIGHT : SIMPALHA");
//            Paragraph titleheader = new Paragraph("Exams's title:");
//            Paragraph descriptionheader = new Paragraph("Exam's Description, tips maybe?");
//            Paragraph imageheader = new Paragraph("Exam's screenshot");
//            //add content
//            document.addTitle("Simpalha's resource");
//            document.add(header1);
//            document.add(titleheader);
//            document.add(title);
//            document.add(descriptionheader);
//            document.add(description);
//            document.add(imageheader);
//
//            //!!adding image VERIFYYYYYYYYYYYYY
//            //  document.add(Image.getInstance(dir + "\\ressources\\" + R1.getTitle()))
////        Image image = new Image(dir + "\\ressources\\" + R1.getTitle());
////        document.add((Element) image);
//            //close content
//            document.close();
//            //Successs!
////            Alert alert = new Alert(AlertType.INFORMATION);
////            alert.setTitle("Generating a pdf of this resource..");
////            alert.setContentText("Pdf is being generated under 'ressources/PDFS' folder" );
////            alert.setHeaderText("Success!! ");
////            alert.showAndWait();

        //Notification
        Notifications.create()
                .title("HELP FOR YOUR EXAMS!")
                .text("The exam : " + R1.getTitle() + " has been downloaded under resources folder")
                .darkStyle().position(Pos.TOP_RIGHT).showConfirm();

    }



@FXML
        private void downloadAll(ActionEvent event) throws DocumentException, FileNotFoundException, IOException {
//        this.GetExams(event);
 String moduleSelected = module.getValue();
List <List<String>> arrList=new ArrayList<>();
 if (moduleSelected.equals("All"))
        { Alert alert = new Alert(AlertType.INFORMATION);
           alert.setTitle("Please select a module");
           alert.setContentText("Select the specified module you cant to revise with our resources!" );
           alert.setHeaderText("Verify.. ");
           alert.showAndWait();}
 
 else if (moduleSelected.isEmpty())
        { Alert alert = new Alert(AlertType.INFORMATION);
           alert.setTitle("Please select a module");
           alert.setContentText("Select the specified module you cant to revise with our resources!" );
           alert.setHeaderText("Verify.. ");
           alert.showAndWait();}
 else{
     
       FileOutputStream fos = new FileOutputStream(""+moduleSelected+".zip");
            ZipOutputStream zos = new ZipOutputStream(fos);
               byte[] buffer = new byte[1024];
               String dir = System.getProperty("user.dir");//get project source path
        System.out.println(dir);
   for (int i = 0; i<examslist.getItems().size(); i++)
        {
        try {
            System.out.println("aa");
            
//            File file = this.getPDF(examslist.getItems().get(i), ""+moduleSelected+".zip");
//            
//            ZipEntry ze = new ZipEntry(examslist.getItems().get(i).getTitle()+".pdf");
//          
//            zos.putNextEntry(ze);
//            FileInputStream in = new FileInputStream(file.getAbsolutePath());
GeneratingPDF GPDF=new GeneratingPDF();

            File file = GPDF.GetGeneratePdf(examslist.getItems().get(i));
            ZipEntry ze = new ZipEntry(examslist.getItems().get(i).getTitle()+".pdf");
            zos.putNextEntry(ze);
            FileInputStream in = new FileInputStream(file.getPath());

            int len;
            while ((len = in.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }
            in.close();
            zos.closeEntry();
           
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        }  //remember close it
            zos.close();
            System.out.println("Done");
     

    }}

    @FXML
    private void showPosts(MouseEvent event) {
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

