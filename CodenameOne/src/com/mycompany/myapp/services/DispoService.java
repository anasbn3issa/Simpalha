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
import com.mycompany.myapp.entities.Disponibilite;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bhk
 */
public class DispoService {

    public ArrayList<Disponibilite> disps;
    public Map<Integer, String> dispMap = new HashMap<>();
    ;

    public static DispoService instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private DispoService() {
        req = new ConnectionRequest();
    }

    public static DispoService getInstance() {
        if (instance == null) {
            instance = new DispoService();
        }
        return instance;
    }

    public ArrayList<Disponibilite> parseDispo(String jsonText) {
        try {
            disps = new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            Map<String, Object> listdata = j.parseJSON(new CharArrayReader(tasksListJson.get("data").toString().toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) listdata.get("root");

            for (Map<String, Object> obj : list) {
                //Création des tâches et récupération de leurs données
                Disponibilite m = new Disponibilite(
                        (int) Float.parseFloat(obj.get("id").toString()),
                        (int) Float.parseFloat(obj.get("etat").toString()),
                        obj.get("datedeb").toString(),
                        obj.get("datefin").toString()
                );
                disps.add(m);
            }

        } catch (IOException ex) {

        }
        return disps;
    }

    public Map<Integer, String> getAllDispsByHlpr(int id) {

        String url = Statics.BASE_URL + "disponibilite/helper/" + id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                disps = parseDispo(new String(req.getResponseData()));
                for (Disponibilite dis : disps) {
                    try {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

                        Date date = format.parse(dis.getDatedeb());

                        String pattern = "dd MMM yy HH:mm a";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                        String dd = simpleDateFormat.format(date);

                        //dispMap.put(dis.getId(), date.toString());
                        dispMap.put(dis.getId(), dd);

                    } catch (ParseException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return dispMap;
    }

    public ArrayList<Disponibilite> getListAllDispsByHlpr(int id) {
        disps = new ArrayList<>();
        String url = Statics.BASE_URL + "disponibilite/helper/" + id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                disps = parseDispo(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return disps;
    }

    public boolean deleteDisp(int id) {
        String url = Statics.BASE_URL + "disponibilite/" + id; //création de l'URL

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

    public boolean addDispo(Disponibilite dis) {
        String url = Statics.BASE_URL + "disponibilite/new/" + dis.getHelperid(); //création de l'URL
        req.setUrl(url);
        req.addArgument("start", dis.getDatedeb());
        req.addArgument("finish", dis.getDateFin());
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

    public boolean editDispo(Disponibilite dis) {
        String url = Statics.BASE_URL + "disponibilite/" + dis.getId() + "/edit"; //création de l'URL
        req.setUrl(url);
        req.addArgument("start", dis.getDatedeb());
        req.addArgument("finish", dis.getDateFin());
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
}
