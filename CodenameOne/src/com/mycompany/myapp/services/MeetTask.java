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
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bhk
 */
public class MeetTask {

    public ArrayList<Meet> meets;
    
    public static MeetTask instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private MeetTask() {
         req = new ConnectionRequest();
    }

    public static MeetTask getInstance() {
        if (instance == null) {
            instance = new MeetTask();
        }
        return instance;
    }

    public boolean addMeet(Meet m) {
        String url = Statics.BASE_URL; //création de l'URL
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

    public ArrayList<Meet> parseMeets(String jsonText){
        try {
            meets=new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            System.out.println(tasksListJson.get("status"));
            
            
            Map<String,Object> listdata = j.parseJSON(new CharArrayReader(tasksListJson.get("data").toString().toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)listdata.get("root");
            
            for(Map<String,Object> obj : list){
                //Création des tâches et récupération de leurs données
                Map<String,Object> std = (Map<String,Object>) obj.get("idStudent");
                int feedbackid = 0;
                if (((Map<String,Object>) obj.get("feedback")) != null)
                    feedbackid = (int)Float.parseFloat(((Map<String,Object>) obj.get("feedback")).get("id").toString());
                Meet m = new Meet(
                    obj.get("id").toString(),
                    (int)Float.parseFloat(((Map<String,Object>) obj.get("idStudent")).get("id").toString()),
                    (int)Float.parseFloat(((Map<String,Object>) obj.get("idHelper")).get("id").toString()),
                    feedbackid,
                    obj.get("specialite").toString(),
                    String.valueOf((int)Float.parseFloat(((Map<String,Object>) obj.get("disponibilite")).get("id").toString())),
                    (int)Float.parseFloat(obj.get("etat").toString())
                );
                meets.add(m);
            }
            
            
        } catch (IOException ex) {
            
        }
        return meets;
    }
    
    public ArrayList<Meet> getAllMeets(){
        String url = Statics.BASE_URL+"meet/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                meets = parseMeets(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return meets;
    }
}
