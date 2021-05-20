/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui.meet;

import com.mycompany.myapp.gui.*;
import com.codename1.ui.FontImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.util.Resources;
import com.codename1.ui.Tabs;
import com.mycompany.myapp.utils.Session;

/**
 *
 * @author bhk
 */
public class ListMeetsForm extends SideMenu {

    public ListMeetsForm(Resources res) {
        setTitle("List meets");
        setupSideMenu(res);
        setLayout(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER));
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new HomeForm(res).showBack());

        Tabs tb = new Tabs();
        tb.setTabUIID(null);
        tb.addTab("Student", FontImage.MATERIAL_3D_ROTATION, 4, new ListStudentMeetsForm(res));
        System.out.println(Session.ConnectedUser.getRoles());
        if(Session.ConnectedUser.getRoles().contains("ROLE_HELPER"))
            tb.addTab("Helper", FontImage.MATERIAL_ACCESSIBILITY, 4, new ListHelperMeetsForm(res));
        setScrollableY(true);
        setScrollableX(true);
        setScrollVisible(false);
        add(BorderLayout.OVERLAY, tb);
    }
}
