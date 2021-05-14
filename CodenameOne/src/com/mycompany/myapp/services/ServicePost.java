/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Meet;
import java.util.ArrayList;

import com.mycompany.myapp.entities.Post;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author anaso
 */
public class ServicePost {
    public ArrayList<Post> posts;
    public static ServicePost instance=null;
    private ConnectionRequest req;
    public boolean resultOK;
     
     private ServicePost(){
        req = new ConnectionRequest();
    }
    
    public static ServicePost getInstance() {
        if (instance == null) {
            instance = new ServicePost();
        }
        return instance;
    }
    
    public boolean addPost(Post m) {
        String url = Statics.BASE_URL+"/mobile/post/new"; //création de l'URL
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); 
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    public ArrayList<Post> parsePosts(String jsonText){
        try {
            posts=new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            System.out.println(tasksListJson.get("status"));
            
            
            Map<String,Object> listdata = j.parseJSON(new CharArrayReader(tasksListJson.get("data").toString().toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)listdata.get("root");
            
            for(Map<String,Object> obj : list){
                //Création des tâches et récupération de leurs données

                Post m = new Post();
                float id = Float.parseFloat(obj.get("id").toString());
                m.setId((int)id);
                m.setStatus("OPEN");
                m.setProblem(obj.get("problem").toString());
                m.setModule(obj.get("module").toString());
                posts.add(m);
            }
            
            
        } catch (IOException ex) {
            
        }
        return posts;
    }
    
    
    public ArrayList<Post> getAllPosts(){
        String url = Statics.BASE_URL+"/mobile/post/getposts";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                posts = parsePosts(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return posts;
    }
    
    
}
