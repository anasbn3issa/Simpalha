/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Post;
import com.mycompany.myapp.services.ServicePost;

/**
 *
 * @author anaso
 */
public class AddPost extends Form {
    private Form current;
     private Button confirm;
    private Container formContainer;
    private TextField tfpostProblem;
    private ComboBox<String> module;
    private Label lProblem, lModule;
    
    public AddPost(Resources theme ) {
                getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new HomeForm(theme).showBack());

        current=this;
         setLayout(new BorderLayout());
        setTitle("Add Post");
        
        formContainer = new Container(BoxLayout.y());
        confirm = new Button("Confirm Post");
        tfpostProblem = new TextField("","Problem");
        module= new ComboBox<String>();
        module.addItem("math");
        module.addItem("Physique");
        module.addItem("Prog");
        confirm.addActionListener(e->{
            
            
                if ((tfpostProblem.getText().length()==0)||module.getSelectedItem().length()==0)
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    Post p = new Post();
                    p.setProblem(tfpostProblem.getText());
                    p.setModule(module.getSelectedItem());
                    if( ServicePost.getInstance().addPost(p))
                        Dialog.show("Success","Post Created",new Command("OK"));
                    else
                        Dialog.show("ERROR", "Server Error", new Command("OK"));
                }
            new ListPostsForm(theme).show();
        });
        
        
        formContainer.add("Problem").add(tfpostProblem).add("Module").add(module);
        add(BorderLayout.CENTER, formContainer).add(BorderLayout.SOUTH,confirm);
        
        
        revalidate();
    }
}
