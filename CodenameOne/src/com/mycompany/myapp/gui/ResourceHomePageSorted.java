/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;


import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.SwipeableContainer;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Ressources;
import com.mycompany.myapp.services.RessourceService;
import com.mycompany.myapp.services.ServiceTask;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author cyrin
 */
public class ResourceHomePageSorted extends SideMenu  {

    public ResourceHomePageSorted(Resources res)  {
        
                setupSideMenu(res);
                ArrayList<Ressources> sortedlist;
                sortedlist = new ArrayList<Ressources>();
                sortedlist = RessourceService.getInstance().getListSorted();
                setTitle("Simpalha's RESOURCES ");
                
               
                Button exam= new Button("Check exams' calendar here\n\n");                
                exam.addActionListener(e->{

                    new Exams(res).show();
                        });                
                
                add(exam);
                
                for (Ressources R:sortedlist)
                {System.out.println(R);
                
                 MultiButton multibutton = new MultiButton(R.getTitle());
                 
                 multibutton.setTextPosition(CENTER);
                 multibutton.setTextLine1(R.getTitle());
                 multibutton.setTextLine2(R.getModule());
                 multibutton.setTextLine3(R.getDescription());
                 multibutton.addActionListener(e->{
                         new DetailResource(R,res).show();                 
                        });
          
                 add(multibutton);}
    
    
    }
  }
 
   

    
    
    

   
