/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui.meet;

import com.codename1.components.FloatingActionButton;
import com.mycompany.myapp.gui.*;
import com.codename1.components.ToastBar;
import com.codename1.l10n.DateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Meet;
import com.mycompany.myapp.services.MeetService;
import com.mycompany.myapp.utils.Statics;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.codename1.ui.Image;
import com.codename1.ui.Tabs;
import com.codename1.util.DateUtil;
import com.mycompany.myapp.utils.Session;
import java.io.IOException;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

/**
 *
 * @author bhk
 */
public class ListMeetsForm extends Form {

    public ListMeetsForm(Resources res) {
        setTitle("List meets");
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
