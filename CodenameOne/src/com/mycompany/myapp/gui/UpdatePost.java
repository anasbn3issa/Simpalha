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
public class UpdatePost extends Form {
    
    private Form current;
     private Button confirm;
    private Container formContainer;
    private TextField tfpostProblem;
    
    public UpdatePost(Resources theme,Post p) {
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new HomeForm(theme).showBack());
        
        current=this;
         setLayout(new BorderLayout());
        setTitle("Update Post");
        
        formContainer = new Container(BoxLayout.y());
        confirm = new Button("Confirm Post");
        tfpostProblem = new TextField(p.getProblem(),""); // loula kent chaine vide 
        
        confirm.addActionListener(e->{
                    p.setProblem(tfpostProblem.getText());
                    if( ServicePost.getInstance().updatePost(p))
                        Dialog.show("Success","Post Updated",new Command("OK"));
                    else
                        Dialog.show("ERROR", "Server Error", new Command("OK"));
                
            new ListPostsForm(theme).show();
        });
        
        formContainer.add("problem").add(tfpostProblem);
        add(BorderLayout.CENTER, formContainer).add(BorderLayout.SOUTH,confirm);
        
//        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK
//                        , e->previous.showBack()); // Revenir vers l'interface précédente
    }
    
}
