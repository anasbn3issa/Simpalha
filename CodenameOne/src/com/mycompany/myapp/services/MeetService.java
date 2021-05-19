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
import com.mycompany.myapp.utils.Session;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bhk
 */
public class MeetService {

    public ArrayList<Meet> meets;

    public static MeetService instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private MeetService() {
        req = new ConnectionRequest();
    }

    public static MeetService getInstance() {
        if (instance == null) {
            instance = new MeetService();
        }
        return instance;
    }

    public boolean addMeet(Meet m) {
        String url = Statics.BASE_URL + "meet/new/" + m.getId_helper(); //création de l'URL
        req.setUrl(url);
        req.addArgument("student_id", String.valueOf(m.getId_student()));
        req.addArgument("disponibilite_id", m.getTime());
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
    
    public boolean editMeet(Meet m) {
        String url = Statics.BASE_URL + "meet/" + m.getId()+"/edit"; //création de l'URL
        req.setUrl(url);
        req.addArgument("disponibilite_id", m.getTime());
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
    
    public boolean deleteMeet(String id) {
        String url = Statics.BASE_URL + "meet/" + id; //création de l'URL
        
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

    public ArrayList<Meet> parseMeets(String jsonText) {
        if(jsonText==null)
                return meets;
        try {
            meets = new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json
            
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            if(tasksListJson.get("data")==null)
                return meets;

            Map<String, Object> listdata = j.parseJSON(new CharArrayReader(tasksListJson.get("data").toString().toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) listdata.get("root");

            for (Map<String, Object> obj : list) {
                //Création des tâches et récupération de leurs données
                Map<String, Object> std = (Map<String, Object>) obj.get("idStudent");
                Map<String, Object> hpr = (Map<String, Object>) obj.get("idHelper");
                int feedbackid = 0;
                if (((Map<String, Object>) obj.get("feedback")) != null) {
                    feedbackid = (int) Float.parseFloat(((Map<String, Object>) obj.get("feedback")).get("id").toString());
                }
                Meet m = new Meet(
                        obj.get("id").toString(),
                        (int) Float.parseFloat(((Map<String, Object>) obj.get("idStudent")).get("id").toString()),
                        (int) Float.parseFloat(((Map<String, Object>) obj.get("idHelper")).get("id").toString()),
                        feedbackid,
                        obj.get("specialite").toString(),
                        ((Map<String, Object>) obj.get("disponibilite")).get("datedeb").toString(),
                        (int) Float.parseFloat(obj.get("etat").toString())
                );
                m.setUnameStd(((Map<String, Object>) obj.get("idStudent")).get("pseudo").toString());
                m.setUnameHlp(((Map<String, Object>) obj.get("idHelper")).get("pseudo").toString());
                if (((Map<String, Object>) obj.get("feedback")) != null) {
                    m.setFeedback(((Map<String, Object>) obj.get("feedback")).get("description").toString());
                }

                meets.add(m);
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return meets;
    }

    public ArrayList<Meet> getAllMeets() {
        req.setPost(false);
        String url = Statics.BASE_URL + "meet/student/" + Session.ConnectedUser.getId();
        req.setUrl(url);
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
    
    public ArrayList<Meet> getAllHelperMeets() {
        req.setPost(false);
        String url = Statics.BASE_URL + "meet/helper/" + Session.ConnectedUser.getId();
        req.setUrl(url);
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
