/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui.meet;

/**
 *
 * @author win10
 */
import com.codename1.components.ToastBar;
import com.codename1.components.WebBrowser;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.utils.Session;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Meet;
import com.mycompany.myapp.services.DispoService;
import com.mycompany.myapp.services.MeetService;
import com.mycompany.myapp.utils.Statics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JoinMeetForm extends Form {

    BrowserComponent browser;

    public JoinMeetForm(Resources res) {
        setTitle("Join meet");
        setLayout(new BorderLayout());
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new ListMeetsForm(res).showBack());

        browser = new BrowserComponent();
        browser.setURL(Statics.BROWSER_BASE_URL);

        add(BorderLayout.CENTER, browser);
        

    }
}
