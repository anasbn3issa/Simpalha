/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

import com.codename1.capture.Capture;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.File;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.io.Storage;
import com.codename1.io.Util;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Candidature;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author win10
 */
public class CandidatureService {

    public static CandidatureService instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private CandidatureService() {
        req = new ConnectionRequest();
    }

    public static CandidatureService getInstance() {
        if (instance == null) {
            instance = new CandidatureService();
        }
        return instance;
    }

    public void addcandidature(Candidature c, File file, String nameFile) {
        MultipartRequest request = new MultipartRequest();

        String url = Statics.BASE_URL + "candidature/new" + "?description=" + c.getDescription() + "&speciality=" + c.getSpecialty() + "&fichier=" + c.getFichier();
        request.setUrl(url);
        request.setPost(true);

        try {
            String urll = file.toString();

            System.out.println(urll);
            request.addData("file", urll, "application/json");
            request.addArgument("name", nameFile);

        } catch (IOException ex) {

        }

        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                if (request.getResponseCode() == 200) {
                    Dialog.show("Success", "Added Successfully", "Ok", null);
                } else {

                    Dialog.show("Failed", "Existing Application", "Ok", null);
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);  
    }
}
