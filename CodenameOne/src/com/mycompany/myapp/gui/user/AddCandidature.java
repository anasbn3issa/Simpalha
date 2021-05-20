/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui.user;

import com.mycompany.myapp.entities.Candidature;
import com.mycompany.myapp.services.CandidatureService;
import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.codename1.ext.filechooser.FileChooser;
import com.codename1.io.File;
import com.codename1.io.Log;
import com.codename1.ui.CN;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.myapp.gui.HomeForm;
import com.mycompany.myapp.gui.SideMenu;
import com.mycompany.myapp.utils.Session;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


/**
 *
 * @author USER
 */
public class AddCandidature extends SideMenu {

    Button img1 = new Button("Parcourir");
    Form current;
    Session currentUser;
    CheckBox multiSelect = new CheckBox("Multi-select");
    File destFile;

    public AddCandidature(Form previous, Resources res) {
        super("Apply to be the next helper", BoxLayout.y());

        getContentPane().setScrollVisible(false);

        img1.setUIID("TextFieldBack");
        addStringValue("image", img1);
        TextField description = new TextField("", "entrer description");

        description.setUIID("TextFieldBack");
        addStringValue("description", description);

        TextField specialty = new TextField("", "entrer specialty");

        specialty.setUIID("TextFieldBack");
        addStringValue("specialty", specialty);

        Button AppButton = new Button("Ajouter");

        addStringValue("", AppButton);

//        TextField image = new TextField("", "entrer image");
//        
        img1.addActionListener((ActionEvent e) -> {
            if (FileChooser.isAvailable()) {
                FileChooser.setOpenFilesInPlace(true);
                FileChooser.showOpenDialog(multiSelect.isSelected(), ".pdf,application/pdf,.gif,image/gif,.mp4,video/mp4,.png,image/png,.jpg,image/jpg,.tif,image/tif,.jpeg", (ActionEvent e2) -> {
                    if (e2 == null || e2.getSource() == null) {
                        add("No file was selected");
                        revalidate();
                        return;
                    }
                    if (multiSelect.isSelected()) {
                        String[] paths = (String[]) e2.getSource();
                        for (String path : paths) {
                            System.out.println(path);
                            CN.execute(path);
                        }
                        return;
                    }

                    String file = (String) e2.getSource();
                    File filePath = new File(file);
                    if (file == null) {
                        add("No file was selected");

                        revalidate();
                    } else {
                        Image logo;

                        String extension = null;
                        if (file.lastIndexOf(".") > 0) {
                            extension = file.substring(file.lastIndexOf(".") + 1);
                            StringBuilder hi = new StringBuilder(file);

                            if (file.startsWith("file://")) {
                                hi.delete(0, 7);

                            }
                            int lastIndexPeriod = hi.toString().lastIndexOf(".");
                            Log.p(hi.toString());
                            String ext = hi.toString().substring(lastIndexPeriod);
                            String hmore = hi.toString().substring(0, lastIndexPeriod - 1);
                            try {
                                String namePic = saveFileToDevice(file, ext);
//                                SlideService.getInstance().ajouterSlide(filePath, namePic);
                                System.out.println(namePic);

                                AppButton.addActionListener((ea) -> {

                                    try {

                                        if (description.getText() == "") {
                                            Dialog.show("Veuillez vérifier les données ", "", "Annuler", "Ok");

                                        } else {
                                            InfiniteProgress ip = new InfiniteProgress();

                                            final Dialog iDialog = ip.showInfiniteBlocking();
                                            Candidature cdr = new Candidature(0, description.getText(), specialty.getText(), namePic);

                                            CandidatureService.getInstance().addcandidature(cdr, filePath, namePic);

                                        }
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }

                                });
                            } catch (IOException ex) {
                            }
                            revalidate();
                        }
                    }
                });
            }
        });

        Button btnannuler = new Button("Annuler");
        btnannuler.addActionListener(a -> {
            new HomeForm(res).show();

        });
        add(btnannuler);

    }

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel"))
                .add(BorderLayout.CENTER, v));
    }

    protected String saveFileToDevice(String hi, String ext) throws IOException {
        URI uri;
        try {
            uri = new URI(hi);
            String path = uri.getPath();
            int index = hi.lastIndexOf("/");
            hi = hi.substring(index + 1);
            return hi;
        } catch (URISyntaxException ex) {
        }
        return "hh";
    }

}
