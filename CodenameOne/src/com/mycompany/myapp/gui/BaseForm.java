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
import com.codename1.components.FloatingActionButton;
import com.codename1.components.RadioButtonList;
import com.codename1.io.Storage;
import com.codename1.ui.*;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.layouts.*;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;

import java.util.List;
import java.util.Map;

public class BaseForm extends Form {



    public void installSidemenu(Resources res, List<Button> list_button, List<Form> list_form) {
        Image selection = res.getImage("selection_in_sidemenu.png");

        Image inboxImage = null;

        if (isCurrentInbox()) {
            inboxImage = selection;
        }

        Image shop_image = null;
        Image image_adresse = null;
        if (is_current_shop()) {
            shop_image = selection;
        }

        Map<String, String> notifications = (Map<String, String>) Storage.getInstance().readObject("Notifications");;
        int notifications_number = notifications.size();

        Button inboxButton = new Button("Inbox", inboxImage);
        inboxButton.setUIID("SideCommand");
        inboxButton.getAllStyles().setPaddingBottom(0);
        Container inbox = FlowLayout.encloseMiddle(inboxButton,
                new Label(Integer.toString(notifications_number), "SideCommandNumber"));
        inbox.setLeadComponent(inboxButton);
        inbox.setUIID("SideCommand");
        //inboxButton.addActionListener(e -> new AddTaskForm(res).show());
        Style style_toolbar = getToolbar().getAllStyles();
        style_toolbar.setBgColor(0x1058D1);
        style_toolbar.setBgTransparency(255);

        getToolbar().addComponentToSideMenu(inbox);

       // getToolbar().addCommandToSideMenu("Commande", image_adresse, e -> new ShopForm(res).show());
        //getToolbar().addCommandToSideMenu("Shop", shop_image, e -> new AddTaskForm().show());
        //getToolbar().addCommandToSideMenu("Produits", shop_image, e -> new ProduitsListForm(this).show());

        // spacer
        getToolbar().addComponentToSideMenu(new Label(" ", "SideCommand"));
        getToolbar().addComponentToSideMenu(new Label(res.getImage("profile_image.png"), "Container"));
     //   User user = (User) Storage.getInstance().readObject("User");
     //   getToolbar().addComponentToSideMenu(new Label(user.getUsername(), "SideCommandNoPad"));
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        fab.setUIID("FloatingActionButton");

//        RoundBorder rb = (RoundBorder)fab.getUnselectedStyle().getBorder();
//        rb.uiid(false);
        fab.bindFabToContainer(getContentPane());
        fab.addActionListener(e -> {
            fab.setUIID("FloatingActionButtonClose");
            Image oldImage = fab.getIcon();
            FontImage image = FontImage.createMaterial(FontImage.MATERIAL_CLOSE, "FloatingActionButton", 3.8f);
            fab.setIcon(image);
            Dialog popup = new Dialog();
            popup.setDialogUIID("Container");
            popup.setLayout(new LayeredLayout());

            // Sheet
            RadioButtonList sheetPos = new RadioButtonList(new DefaultListModel(
                    BorderLayout.NORTH, BorderLayout.EAST, BorderLayout.SOUTH, BorderLayout.WEST, BorderLayout.CENTER
            ));
            MySheet sheet = new MySheet(null, list_button, list_form);
            int positionIndex = sheetPos.getModel().getSelectedIndex();
            if (positionIndex >= 0) {
                String pos = (String) sheetPos.getModel().getItemAt(positionIndex);
                sheet.setPosition("South");
            }
            sheet.show();

            //popup
            popup.setTransitionInAnimator(CommonTransitions.createEmpty());
            popup.setTransitionOutAnimator(CommonTransitions.createEmpty());
            popup.setDisposeWhenPointerOutOfBounds(false);
            int t = BaseForm.this.getTintColor();
            BaseForm.this.setTintColor(0);
            //popup.showPopupDialog(new Rectangle(SignInForm.this.getWidth() - 10, SignInForm.this.getHeight() - 10, 10, 10));
            BaseForm.this.setTintColor(t);
            fab.setUIID("FloatingActionButton");
            fab.setIcon(oldImage);
            // ligne pour fermer

        });

    }

    public BaseForm() {
    }
    public BaseForm(Layout contentPaneLayout) {
        super(contentPaneLayout);
    }

    public BaseForm(String title) {
        super(title);
    }

    public BaseForm(String title, Layout contentPaneLayout) {
        super(title, contentPaneLayout);
    }

    private class MySheet extends Sheet {

        MySheet(Sheet parent, List<Button> list_button, List<Form> list_form) {
            super(parent, "My Sheet");
            Container cnt = getContentPane();
            cnt.setLayout(BoxLayout.y());

            System.out.println(cnt);
            for (int i = 0; i < list_button.size(); i++) {
                Form t = list_form.get(i);
                if (!cnt.contains(list_button.get(i))) {
                    System.out.println(cnt.contains(list_button.get(i)) + "ici");
                    // System.out.println(cnt.getChildrenAsList(true));
                    list_button.get(i).addActionListener(e -> {
                        t.show();
                    });
                    cnt.add(list_button.get(i));
                }

            }
            addCloseListener((e) -> {
                getContentPane().removeAll();
            });
        }
    }

    private boolean is_current_shop() {
        return false;
    }
    private boolean isCurrentInbox() {
        return false;
    }

}
