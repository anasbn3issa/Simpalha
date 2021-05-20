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
import com.mycompany.myapp.gui.AddTaskForm;
import com.mycompany.myapp.gui.ListTasksForm;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.gui.meet.ListHelperDisponibiliteForm;
import com.mycompany.myapp.gui.meet.ListMeetsForm;
import com.mycompany.myapp.utils.Session;

public class SideMenu extends Form {

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

    public void setupSideMenu(/*User u,*/Resources res) {
        if (Session.ConnectedUser.getRoles().contains("ROLE_HELPER")) {
            getToolbar().addCommandToOverflowMenu("Disponibilite", null, ev -> {
                new ListHelperDisponibiliteForm(res).show();
            });
        }
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
        getToolbar().addMaterialCommandToSideMenu("  Meets", FontImage.MATERIAL_LOCAL_ACTIVITY, e -> new ListMeetsForm(res).show());
        getToolbar().addMaterialCommandToSideMenu("  Events", FontImage.MATERIAL_LOCAL_OFFER, null);
        getToolbar().addMaterialCommandToSideMenu("  Statistics", FontImage.MATERIAL_ANALYTICS, null);
        getToolbar().addMaterialCommandToSideMenu("  Meetings", FontImage.MATERIAL_TRENDING_UP, null);
        getToolbar().addMaterialCommandToSideMenu("  Projects", FontImage.MATERIAL_ACCESS_TIME, null);
        getToolbar().addMaterialCommandToSideMenu("  Releases", FontImage.MATERIAL_ACCESS_TIME, null);

        getToolbar().addMaterialCommandToSideMenu("  Documents", FontImage.MATERIAL_TRENDING_UP, null);
        getToolbar().addMaterialCommandToSideMenu("  Issues", FontImage.MATERIAL_ACCESS_TIME, null);

        getToolbar().addMaterialCommandToSideMenu("  Meeting Claims", FontImage.MATERIAL_ACCESS_TIME, null);
        getToolbar().addMaterialCommandToSideMenu("  Account Settings", FontImage.MATERIAL_SETTINGS, null);

        getToolbar().addMaterialCommandToSideMenu("  Logout", FontImage.MATERIAL_EXIT_TO_APP, null/*e -> new Login(current, res).show()*/);

    }
}
