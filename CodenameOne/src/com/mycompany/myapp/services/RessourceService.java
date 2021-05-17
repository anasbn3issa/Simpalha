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

    public ArrayList<Ressources> tasks;
   

    ArrayList<Ressources> listRess = new ArrayList<>();
    ArrayList<Ressources> listRessOne = new ArrayList<>();

    public ArrayList<Ressources> ExamParseJson(String json) throws ParseException {

        ArrayList<Ressources> listRess1 = new ArrayList<>();

        try {
            JSONParser j = new JSONParser();

            Map<String, Object> experiences = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) experiences.get("root");

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

    public ArrayList<Ressources> getListExamen() {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://127.0.0.1:8000/mobile/RUser/");

        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                RessourceService sl = new RessourceService();
                try {
                    listRess = sl.ExamParseJson(new String(con.getResponseData()));
                } catch (ParseException ex) {
                }

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        System.out.println(listRess);
        return listRess;

    }
}
