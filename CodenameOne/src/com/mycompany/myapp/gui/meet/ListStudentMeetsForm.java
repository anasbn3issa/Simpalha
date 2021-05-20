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
import java.io.IOException;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

/**
 *
 * @author bhk
 */
public class ListStudentMeetsForm extends Form {

    public ListStudentMeetsForm(Resources res) {
        setLayout(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER));
        setScrollableY(true);
        setScrollableX(true);
        setScrollVisible(false);

        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        //fab.addActionListener(e -> ToastBar.showErrorMessage("Not implemented yet..."));
        fab.addActionListener(e -> new ListHelpersForm(res).show());

        fab.bindFabToContainer(getContentPane());

        ArrayList<Meet> meets = MeetService.getInstance().getAllMeets();

        if (!meets.isEmpty()) {

            Container cnty = new Container(BoxLayout.y());

            Container cnthelpers = new Container(BoxLayout.y());
            Label lbhelpers = new Label("Helper");
            cnthelpers.add(lbhelpers);

            Container speclts = new Container(BoxLayout.y());
            Label lbspec = new Label("Specialty");
            speclts.add(lbspec);

            Container times = new Container(BoxLayout.y());
            Label lbtimes = new Label("Time");
            times.add(lbtimes);

            Container actions = new Container(BoxLayout.y());
            Label lbsel = new Label("Action");
            actions.add(lbsel);

            for (Meet meet : meets) {

                try {

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

                    Date date = null;
                    try {
                        date = format.parse(meet.getTime());
                    } catch (ParseException ex) {
                        System.out.println(ex.getMessage());
                    }
                    String pattern = "dd MMM yy HH:mm a";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                    String dd = simpleDateFormat.format(date);
                    //Label lbimg = new Label(res.getImage("user.png"));
                    lbhelpers = new Label(meet.getUnameHlp());
                    lbspec = new Label(meet.getSpecialite());
                    lbtimes = new Label(dd);
                    Container cntbtns = new Container(BoxLayout.x());
                    ComboBox<String> combo = new ComboBox<>();
                    combo.addItem("Join");
                    combo.addItem("Navigator");
                    combo.addItem("QR");
                    combo.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            switch (combo.getSelectedItem()) {
                                case "Navigator": {
                                    new JoinMeetForm(res).show();
                                    break;
                                }
                                case "QR": {
                                    showdialog();
                                    break;
                                }
                                default: {
                                    ToastBar.showErrorMessage("An error occured");
                                    break;
                                }
                            }
                        }
                    });

                    Button btnedt = new Button("Edit");
                    btnedt.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            new EditMeetForm(res, meet).show();
                        }
                    });
                    Button btndel = new Button("Delete");
                    btndel.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            showDeldialog(res, meet.getId());
                        }
                    });

                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                    String d1st = format1.format(date);
                    String d2st = format1.format(new Date());
                    Date d1 = format1.parse(d1st);
                    Date d2 = format1.parse(d2st);
                    int diff = DateUtil.compare(d1, d2);

                    if (meet.getEtat() != 1) {
                        if (diff == 0) {
                            cntbtns.add(combo);
                        } else if (diff == -1) {
                            add(btnedt);
                        }
                    }
                    cntbtns.add(btndel);
                    cnthelpers.add(lbhelpers);
                    speclts.add(lbspec);
                    times.add(lbtimes);
                    actions.add(cntbtns);

                } catch (ParseException ex) {
                    System.out.println(ex.getMessage());
                }

            }
            Container table = new Container(BoxLayout.x());
            table.addAll(cnthelpers, speclts, times, actions);
            table.setScrollableX(true);
            add(BorderLayout.OVERLAY, table);
        } else {
            add(BorderLayout.CENTER, new Label("No scheduled meetings"));
        }

    }

    void showdialog() {
        Dialog dlg = new Dialog("SCAN QR CODE");
        Style dlgStyle = dlg.getDialogStyle();
        dlgStyle.setBorder(Border.createEmpty());
        dlgStyle.setBgTransparency(255);
        dlgStyle.setBgColor(0xffffff);
        dlgStyle.setAlignment(CENTER);

        Label title = dlg.getTitleComponent();
        title.getUnselectedStyle().setFgColor(0xff);
        title.getUnselectedStyle().setAlignment(Component.CENTER);

        dlg.setLayout(BoxLayout.y());
        Label blueLabel = new Label();
        blueLabel.setShowEvenIfBlank(true);
        blueLabel.getUnselectedStyle().setBgColor(0xff);
        blueLabel.getUnselectedStyle().setPadding(1, 1, 1, 1);
        blueLabel.getUnselectedStyle().setPaddingUnit(Style.UNIT_TYPE_PIXELS);
        dlg.add(blueLabel);

        ByteArrayOutputStream out = QRCode.from(Statics.BROWSER_BASE_URL).to(ImageType.PNG).withSize(200, 200).stream();
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        Image qrcode = null;
        try {
            qrcode = Image.createImage(in);
        } catch (IOException ex) {
        }
        qrcode = qrcode.scaled(500, 500);
        Container cntqr = new Container(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER));
        cntqr.add(BorderLayout.CENTER, qrcode);

        TextArea ta = new TextArea("Scan QR code to get the link to your meet");
        ta.setEditable(false);
        ta.setUIID("DialogBody");
        ta.getAllStyles().setFgColor(0);
        dlg.add(ta);
        dlg.add(cntqr);

        Label grayLabel = new Label();
        grayLabel.setShowEvenIfBlank(true);
        grayLabel.getUnselectedStyle().setBgColor(0xcccccc);
        grayLabel.getUnselectedStyle().setPadding(1, 1, 1, 1);
        grayLabel.getUnselectedStyle().setPaddingUnit(Style.UNIT_TYPE_PIXELS);
        dlg.add(grayLabel);

        Button ok = new Button(new Command("OK"));
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                dlg.dispose();
            }
        });
        ok.getAllStyles().setBorder(Border.createEmpty());
        ok.getAllStyles().setFgColor(0);
        dlg.add(ok);
        dlg.showDialog();
    }

    void showDeldialog(Resources res, String id) {
        Dialog dlg = new Dialog("Are you sure?");
        Style dlgStyle = dlg.getDialogStyle();
        dlgStyle.setBorder(Border.createEmpty());
        dlgStyle.setBgTransparency(255);
        dlgStyle.setBgColor(0xffffff);
        dlgStyle.setAlignment(CENTER);

        Label title = dlg.getTitleComponent();
        title.getUnselectedStyle().setFgColor(0xff);
        title.getUnselectedStyle().setAlignment(Component.CENTER);

        dlg.setLayout(BoxLayout.y());
        Label blueLabel = new Label();
        blueLabel.setShowEvenIfBlank(true);
        blueLabel.getUnselectedStyle().setBgColor(0xff);
        blueLabel.getUnselectedStyle().setPadding(1, 1, 1, 1);
        blueLabel.getUnselectedStyle().setPaddingUnit(Style.UNIT_TYPE_PIXELS);
        dlg.add(blueLabel);

        TextArea ta = new TextArea("You're about to delete a meet");
        ta.setEditable(false);
        ta.setUIID("DialogBody");
        ta.getAllStyles().setFgColor(0);
        dlg.add(ta);

        Label grayLabel = new Label();
        grayLabel.setShowEvenIfBlank(true);
        grayLabel.getUnselectedStyle().setBgColor(0xcccccc);
        grayLabel.getUnselectedStyle().setPadding(1, 1, 1, 1);
        grayLabel.getUnselectedStyle().setPaddingUnit(Style.UNIT_TYPE_PIXELS);
        dlg.add(grayLabel);
        Container btncntr = new Container();
        Button ok = new Button(new Command("OK"));
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                dlg.dispose();
                animateLayout(2);
                if (MeetService.getInstance().deleteMeet(id)) {
                    new ListMeetsForm(res).show();
                    ToastBar.showInfoMessage("Meet deleted.");
                } else {
                    ToastBar.showErrorMessage("Delete error!");
                }
            }
        });

        Button cancel = new Button(new Command("Cancel"));
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                dlg.dispose();
            }
        });

        ok.getAllStyles().setBorder(Border.createEmpty());
        ok.getAllStyles().setFgColor(0);

        btncntr.addAll(ok, cancel);
        Container cnt = new Container(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER));
        cnt.add(BorderLayout.CENTER, btncntr);
        dlg.add(cnt);
        dlg.showDialog();
    }
}
