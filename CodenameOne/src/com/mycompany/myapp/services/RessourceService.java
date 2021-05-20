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
import com.mycompany.myapp.entities.Ressources;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bhk
 */ 
public class RessourceService {
     public ArrayList<Ressources> Ress;
    
    public static RessourceService instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private RessourceService() {
         req = new ConnectionRequest();
    }

    public static RessourceService getInstance() {
        if (instance == null) {
            instance = new RessourceService();
        }
        return instance;
    }
    public ArrayList<Ressources> parseTasks(String jsonText){

     try {
            Ress=new ArrayList<>();
            JSONParser j = new JSONParser();
            
            Map<String,Object> ressourcesListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)ressourcesListJson.get("root");
            
            //Parcourir la liste des tâches Json
            for(Map<String,Object> obj : list){
                //Création des tâches et récupération de leurs données
                Ressources R = new Ressources();
                float id = Float.parseFloat(obj.get("idr").toString());
                R.setIdR((int)id);
                R.setTitle(obj.get("title").toString());
                R.setDescription(obj.get("description").toString());
                R.setModule(obj.get("module").toString());
                R.setPath(obj.get("path").toString());

                //Ajouter la tâche extraite de la réponse Json à la liste
                Ress.add(R);
            }
            
            
        } catch (IOException ex) {
            
        }
               return Ress;}
    
    
    public ArrayList<Ressources> getListRessource(){
        String url = Statics.BASE_URL+"RUser/";
        System.out.println(url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Ress = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Ress;
    }
    
     
    public ArrayList<Ressources> getListSorted(){
        String url = Statics.BASE_URL+"RUser/tri";
        System.out.println(url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Ress = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Ress;
    }
    
    
/*
    public ArrayList<Ressources> tasks;
   

    ArrayList<Ressources> listRess = new ArrayList<>();
    ArrayList<Ressources> listRessOne = new ArrayList<>();

    public ArrayList<Ressources> ResourceParseJson(String json) throws ParseException {

        ArrayList<Ressources> listRess1 = new ArrayList<>();

        try {
            JSONParser j = new JSONParser();

            Map<String, Object> parse = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) parse.get("root");

            for (Map<String, Object> obj : list) {
                
                Ressources R = new Ressources(
                   (int)Float.parseFloat(obj.get("idR").toString()),
                   obj.get("path").toString(),
                   obj.get("title").toString(), 
                   obj.get("description").toString(),
                   obj.get("module").toString()
                );
                listRess1.add(R);
                

            }

        } catch (IOException ex) {
        }
        //System.out.println(listEx1);
        return listRess1;
    }

    public ArrayList<Ressources> getListRessource() {
        /*
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://127.0.0.1:8000/mobile/RUser/");

        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                RessourceService RS = new RessourceService();
                try {
                    listRess = RS.ResourceParseJson(new String(con.getResponseData()));
                } catch (ParseException ex) {
                }

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        System.out.println(listRess);
        return listRess;

        
          String url = Statics.BASE_URL+"/tasks/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                tasks = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tasks;

    } */
}
