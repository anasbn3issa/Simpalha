/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;


import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.gui.meet.ListMeetsForm;
import com.mycompany.myapp.services.MeetTask;
import com.codename1.ui.util.Resources;

/**
 *
 * @author bhk
 */
public class HomeForm extends SideMenu {

    Form current;
    public HomeForm(Resources res) {
        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);
        setupSideMenu(res);
        current = this;
        setTitle("Home");
        setLayout(BoxLayout.y());

        add(new Label("Choose an option"));
        Button btnApplications = new Button("Applications list");
        

   
        addAll(btnApplications);

    }

}
