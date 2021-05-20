/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.ImageViewer;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Util;
import com.codename1.ui.Button;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Ressources;
import java.io.IOException;

/**
 *
 * @author cyrin
 */
public class getPDF extends SideMenu {

    Form current;
    Image img;
    EncodedImage enc;
    ImageViewer imgv;

    public getPDF(Resources res, Ressources R) {
        setupSideMenu(res);
        setTitle("GET PDF..");

        Button Back = new Button("Go Back\n\n\n");
        Back.addActionListener(e -> {

            FileSystemStorage fs = FileSystemStorage.getInstance();
            System.out.println(fs.getAppHomePath());
           String fileName = fs.getAppHomePath() + R.getTitle() + ".pdf";
//            String fileName = "C:\\Users\\anaso\\OneDrive\\Desktop\\" + R.getTitle() + ".pdf";
           // String fileName = "http://127.0.0.1:8000/" + R.getTitle() + ".pdf";

            if (!fs.exists(fileName)) {
                Util.downloadUrlToFile("http://127.0.0.1:8000/RUser/pdf/" + R.getIdR(), fileName, true);
            }
//                    new ResourceHomePage(res).show();

        });

        //Website simpalha WORKING
        //    BrowserComponent browser = new BrowserComponent();
        // browser.setURL("http://127.0.0.1:8000/RUser/"+id);
        //  browser.setURL("https://esprit.tn/formation/esprit-ingenieur/calendrier-examens");
        //END WORKING
        //TRY
        try {
            enc = EncodedImage.create("/sure.png");
        } catch (IOException ex) {
        };

        String url = "/sure.png";

        img = URLImage.createToStorage(enc, url, url, URLImage.RESIZE_SCALE);
        imgv = new ImageViewer(img);

        //  Form hi = new Form("PDF Viewer", BoxLayout.y());
        setLayout(BoxLayout.y());
        Button dwnld = new Button("Download here");

        dwnld.addActionListener(e -> {
            FileSystemStorage fs = FileSystemStorage.getInstance();
            String fileName = fs.getAppHomePath() + R.getTitle() + ".pdf";

            if (!fs.exists(fileName)) {
                Util.downloadUrlToFile("http://127.0.0.1:8000/RUser/pdf/" + R.getIdR(), fileName, true);
            }
            //     Display.getInstance().execute(fileName);
        });

        add(Back);
        add("\n\n\n\n\n");
        add(imgv);
        add(dwnld);
        //END TRY

        //WEBSITE WORKING //  setLayout(new BorderLayout());
        // END // add(BorderLayout.CENTER, browser);

        ;

        //add(C1);
    }

}
