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
import com.codename1.ui.*;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Feedback;
import com.mycompany.myapp.services.FeedbackService;
import com.mycompany.myapp.utils.Statics;

public class JoinMeetForm extends Form {

    BrowserComponent browser;

    public JoinMeetForm(Resources res, String id) {
        setTitle("Join meet");
        setLayout(new BorderLayout());
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> 
                                JoinMeetForm.leaveFeedback(res, id));


        browser = new BrowserComponent();
        browser.setURL(Statics.BROWSER_BASE_URL+id);

        add(BorderLayout.CENTER, browser);

    }

    public static void leaveFeedback(Resources res, String id) {
        Dialog dlg = new Dialog("Feedback");
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

            TextArea ta = new TextArea("Leave your feedback for this meet:");
            ta.setEditable(false);
            ta.setUIID("DialogBody");
            ta.getAllStyles().setFgColor(0);
            
            TextArea tafeedback = new TextArea();

            
            dlg.add(ta).add(tafeedback);

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
                    Feedback feedback = new Feedback();
                    feedback.setMeetId(id);
                    feedback.setFeedback(tafeedback.getText());
                    if (FeedbackService.getInstance().addFeedback(feedback)) {
                        dlg.dispose();
                        new ListMeetsForm(res).showBack();
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
                    new ListMeetsForm(res).showBack();
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
