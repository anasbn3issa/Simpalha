/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Container;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Button;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.SwipeableContainer;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Ressources;
import com.mycompany.myapp.services.RessourceService;
import com.mycompany.myapp.services.ServiceTask;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author cyrin
 */
public class DetailResource extends SideMenu {

    EncodedImage enc;
    Image imageloaded;
    ImageViewer imgviewer;

    public DetailResource(Ressources R, Resources res) {
        setupSideMenu(res);
        setTitle(R.getTitle());
        Button Back = new Button("get pdf\n\n\n");

        Back.addActionListener(e -> {
            // DetailResource a = new DetailResource(R,res);
            new getPDF(res, R).show();
//            new ResourceHomePage(res).show();
        });
        add(Back);
        
        TextArea infos = new TextArea("Title:"
                + " \n" + R.getTitle() + "\n\n" + "Description:\n"
                + R.getDescription() + "\n\n" + "Module:\n"
                + R.getModule() + "\n\n", 8, 12);
        infos.setEditable(false);

        String url = "http://127.0.0.1:8000" + R.getPath();
        System.out.println(url);

        // Form f1 = new Form("",new BoxLayout(BoxLayout.Y_AXIS));
        try {
            enc = EncodedImage.create("/load.png");
        } catch (IOException ex) {
        };

        imageloaded = URLImage.createToStorage(enc, url, url, URLImage.RESIZE_SCALE);
        imgviewer = new ImageViewer(imageloaded);

        // add(imgviewer);
        //add(infos);
        Container C1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        C1.add(infos);

        Container C2 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        C2.add(imgviewer);

        add(C2);
        add(C1);

//           
        Button Back1 = new Button("Get PDF ! \n\n\n");
        Back1.addActionListener(e -> {
            new getPDF(res, R).show();
        });
        add(Back1);

        //add(C1);
    }

}
