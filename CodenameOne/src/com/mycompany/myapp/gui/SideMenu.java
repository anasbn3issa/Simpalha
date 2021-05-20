/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

/**
 *
 * @author win10
 */
import com.codename1.admob.AdMobManager;
import com.mycompany.myapp.gui.AddTaskForm;
import com.mycompany.myapp.gui.ListTasksForm;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.util.Resources;

public class SideMenu extends Form {

     private AdMobManager admob;
     int n = 0;
    Form current;

    public SideMenu(String title, Layout contentPaneLayout) {
        super(title, contentPaneLayout);
    }

    public SideMenu(String title) {
        super(title);
    }

    public SideMenu() {
    }

    public SideMenu(Layout contentPaneLayout) {
        super(contentPaneLayout);
    }

    public void setupSideMenu(/*User u,*/ Resources res) {
        
           //String admobId = "ca-app-pub-3940256099942544/1033173712";
           
           //DECLARATION API ADMOB
          String admobId = "ca-app-pub-3940256099942544/5224354917\n";
          admob = new AdMobManager(admobId);
          //FIN DECLARATION API ADMOB
        
        Image profilePic = res.getImage("20187112601561032514-512.png");
        Image mask = res.getImage("round-mask.png");
        mask = mask.scaledHeight(mask.getHeight() / 4 * 3);
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label("hama", profilePic, "SideMenuTitle");
        profilePicLabel.setMask(mask.createMask());

        Container sidemenuTop = BorderLayout.center(profilePicLabel);
        sidemenuTop.setUIID("SidemenuTop");

        getToolbar().addComponentToSideMenu(sidemenuTop);

        getToolbar().addMaterialCommandToSideMenu("  Home", FontImage.MATERIAL_HOME, e -> new HomeForm(res).show());
        getToolbar().addMaterialCommandToSideMenu("  Job Offers", FontImage.MATERIAL_LOCAL_OFFER, null);
        getToolbar().addMaterialCommandToSideMenu("  Events", FontImage.MATERIAL_LOCAL_OFFER,null);
        getToolbar().addMaterialCommandToSideMenu("  Statistics", FontImage.MATERIAL_ANALYTICS, null);
        getToolbar().addMaterialCommandToSideMenu("  Meetings", FontImage.MATERIAL_TRENDING_UP, null);
        getToolbar().addMaterialCommandToSideMenu("  Projects", FontImage.MATERIAL_ACCESS_TIME, null);
        getToolbar().addMaterialCommandToSideMenu("  Releases", FontImage.MATERIAL_ARTICLE, null);

        getToolbar().addMaterialCommandToSideMenu("  Resources", FontImage.MATERIAL_ARTICLE, e -> {
          if (n % 2 == 0) {
               admob.loadAndShow();
               n++;
              
           } else {
                n++;
              new ResourceHomePage(res).show();
           }
        });
        getToolbar().addMaterialCommandToSideMenu("  Issues", FontImage.MATERIAL_ACCESS_TIME, null);

        getToolbar().addMaterialCommandToSideMenu("  Meeting Claims", FontImage.MATERIAL_ACCESS_TIME, null);
        getToolbar().addMaterialCommandToSideMenu("  Account Settings", FontImage.MATERIAL_SETTINGS, null);

        getToolbar().addMaterialCommandToSideMenu("  Logout", FontImage.MATERIAL_EXIT_TO_APP, null/*e -> new Login(current, res).show()*/);

    }
}
