/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.admin.ressources;
import simpalha.ressources.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import entities.Ressources;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

/**
 *
 * @author cyrin
 */
public class GeneratingPDF {

    public void GeneratePdf(Ressources R1) {

        try {
            //file generated's name
            String dir = System.getProperty("user.dir");//get project source path
            File name = new File(dir + "\\ressources\\PDFS\\" + R1.getTitle() + ".pdf");//add the full path /ressources + file name

            //Create a document object
            Document document = new Document();

            PdfWriter.getInstance(document, new FileOutputStream(name));

            //Opening the document
            document.open();

            //load content
            Paragraph title = new Paragraph(R1.getTitle());
            Paragraph description = new Paragraph(R1.getDescription());
            Paragraph header1 = new Paragraph("COPYRIGHT : SIMPALHA");
            Paragraph titleheader = new Paragraph("Resource's title:");
            Paragraph descriptionheader = new Paragraph("Resource's Description:");
            Paragraph imageheader = new Paragraph("Resource's image:");

            //add content
            document.addTitle("Simpalha's resource");

            document.add(header1);
            document.add(titleheader);
            document.add(title);
            document.add(descriptionheader);
            document.add(description);
            document.add(imageheader);

            String imageFile = "C:/itextExamples/javafxLogo.jpg";

            document.add(Image.getInstance(dir + "\\ressources\\" + R1.getPath()));
            //close content
            document.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Generating a pdf..");
            alert.setHeaderText("Success!! ");
            alert.setContentText("PDF has been generated. Would you like to send it via an email?");
            alert.setContentText("Type OK to confirm, CANCEL to cancel.");
            alert.showAndWait();

        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    public File GetGeneratePdf(Ressources R1) {
   //file generated's name
            String dir = System.getProperty("user.dir");//get project source path
            File name = new File(dir + "\\ressources\\PDFS\\" + R1.getTitle() + ".pdf");//add the full path /ressources + file name

            //Create a document object
            Document document = new Document();
        try {
         

            PdfWriter.getInstance(document, new FileOutputStream(name));

            //Opening the document
            document.open();

            //load content
            Paragraph title = new Paragraph(R1.getTitle());
            Paragraph description = new Paragraph(R1.getDescription());
            Paragraph header1 = new Paragraph("COPYRIGHT : SIMPALHA");
            Paragraph titleheader = new Paragraph("Resource's title:");
            Paragraph descriptionheader = new Paragraph("Resource's Description:");
            Paragraph imageheader = new Paragraph("Resource's image:");

            //add content
            document.addTitle("Simpalha's resource");

            document.add(header1);
            document.add(titleheader);
            document.add(title);
            document.add(descriptionheader);
            document.add(description);
            document.add(imageheader);

            String imageFile = "C:/itextExamples/javafxLogo.jpg";

            document.add(Image.getInstance(dir + "\\ressources\\" + R1.getPath()));
            //close content
            document.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Generating a pdf..");
            alert.setHeaderText("Success!! ");
            alert.setContentText("PDF has been generated. Would you like to send it via an email?");
            alert.setContentText("Type OK to confirm, CANCEL to cancel.");
            alert.showAndWait();

        } catch (Exception ex) {
            System.out.println(ex);
        }

        return name;
    }
}
