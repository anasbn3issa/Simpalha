/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author win10
 */
public class Candidature {

   

    private String specialty, userrname, description;
    private int idc;
    private int idu;
    private String datec;
    private String fichier;
    private int status;

    public static Object getSelectionModel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Candidature(int idc, String description, String specialty, String fichier) {
        this.specialty = specialty;
        this.description = description;
        this.idc = idc;
        this.fichier=fichier;
    }
 public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getUserrname() {
        return userrname;
    }

    public void setUserrname(String userrname) {
        this.userrname = userrname;
    }

    public Candidature(String specialty, int status, int idc, String fichier, String datec, int idu) {
        this.specialty = specialty;
        this.status = status;
        this.idc = idc;
        this.fichier = fichier;
        this.datec = datec;
        this.idu = idu;
    }

    public int getId() {
        return idu;
    }

    public void setId(int idu) {
        this.idu = idu;
    }

    public String getDatec() {
        return datec;
    }

    public void setDatec(String datec) {
        this.datec = datec;
    }

    public Candidature(int status, int idu, String datec) {
        this.status = status;
        this.idu = idu;
        this.datec = datec;
    }

    public Candidature() {

    }

    public Candidature(String specialty, String fichier, int idu) {
        this.specialty = specialty;
        this.fichier = fichier;
        this.idu = idu;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIdc() {
        return idc;
    }

    public void setIdc(int idc) {
        this.idc = idc;
    }

    public int getIdu() {
        return idu;
    }

    public void setIdu(int idu) {
        this.idu = idu;
    }

    public String getDate() {
        return datec;
    }

    public void setDate(String date) {
        this.datec = date;
    }

    public String getFichier() {
        return fichier;
    }

    public void setFichier(String fichier) {
        this.fichier = fichier;
    }

    public String getDateToString() {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = format.parse(datec.toString());
            return date.toString();
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public String getStatusToString() {
        if (status == 1) {
            return "Refusée";
        }
        if (status == 2) {
            return "Confirmée";
        }
        return "En attente";
    }

    @Override
    public String toString() {
        return "Candidature{" + "specialty=" + specialty + ", userrname=" + userrname + ", description=" + description + ", idc=" + idc + ", idu=" + idu + ", datec=" + datec + ", fichier=" + fichier + ", status=" + status + '}';
    }

    

}
