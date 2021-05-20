/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Post;
import com.mycompany.myapp.services.ServicePost;
import java.util.ArrayList;

/**
 *
 * @author anaso
 */
public class ListPostsForm extends Form {

    private Container postsContainer;
    private Button sort=new Button("Sort");
    private ArrayList<Post> posts;

    public ListPostsForm(Resources theme) {
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new HomeForm(theme).showBack());
        setUIID("ListPosts");
        postsContainer = new Container(BoxLayout.y());
        //postsContainer.setScrollableX(true);
        //setScrollableY(true);
        //setScrollableX(true);
        //setScrollVisible(false);
        posts = ServicePost.getInstance().getAllPosts();
        postsContainer.add(sort);
        sort.addActionListener(e->{ 
                
                new ListPostsSortedForm(theme).show();
            });
        

        setLayout(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_SCALE));
        setTitle("Posts List");
        

        for (Post p : posts) {
            Container a = new Container(new FlowLayout(CENTER));
            Container b = new Container(BoxLayout.y());
            Container dmContainer = new Container(new FlowLayout(CENTER));

            //SpanLabel lProblem = new SpanLabel(p.getProblem());
            MultiButton multibutton = new MultiButton(p.getOwnerUserName());
            multibutton.setTextPosition(CENTER);
            multibutton.setTextLine1(p.getOwnerUserName());
            multibutton.setTextLine2(p.getModule());
            multibutton.setTextLine3(p.getProblem());
            //Label lName = new Label();
            Button bDelete = new Button("Delete");
            Button bModify = new Button("Modify");

            dmContainer.add(bDelete).add(bModify);
            b.add(multibutton).add(dmContainer);
            a.add(b);

            postsContainer.add(a);
            
            
            
            bDelete.addActionListener(e->{
                // ONCE I GET SESSION WORKING, I NEED TO CHANGE 11 with getUserId()
                if( ServicePost.getInstance().deletePost(p))
                    Dialog.show("Success","Post Deleted",new Command("OK"));
                else
                    Dialog.show("ERROR", "Server Error", new Command("OK"));
                
                new ListPostsForm(theme).show();
            });
            
            bModify.addActionListener(e->{
                new UpdatePost(theme,p).show();
            });
        }

        add(BorderLayout.CENTER, postsContainer);

        FloatingActionButton confirm = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        confirm.addActionListener(e -> new AddPost(theme).show());
        confirm.bindFabToContainer(getContentPane());

        setScrollable(true);

        revalidate();

    }

}
