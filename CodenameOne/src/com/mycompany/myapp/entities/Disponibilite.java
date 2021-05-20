/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author α Ω
 */
public class Disponibilite {
    
        private int id,etat, helperid;
    private String datedeb,dateFin;

    public Disponibilite() {
    }

    public Disponibilite(int id, int etat, int helperid, String datedeb, String dateFin) {
        this.id = id;
        this.etat = etat;
        this.helperid = helperid;
        this.datedeb = datedeb;
        this.dateFin = dateFin;
    }
    
    public Disponibilite(int id, int etat, String datedeb, String dateFin) {
        this.id = id;
        this.etat = etat;
        this.helperid = helperid;
        this.datedeb = datedeb;
        this.dateFin = dateFin;
    }

    public Disponibilite(int helperid, String datedeb, String dateFin) {
        this.helperid = helperid;
        this.datedeb = datedeb;
        this.dateFin = dateFin;
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public int getHelperid() {
        return helperid;
    }

    public void setHelperid(int helperid) {
        this.helperid = helperid;
    }

    public String getDatedeb() {
        return datedeb;
    }

    public void setDatedeb(String datedeb) {
        this.datedeb = datedeb;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    @Override
    public String toString() {
        return "disponiilite{" + "id=" + id + ", etat=" + etat + ", helperid=" + helperid + ", datedeb=" + datedeb + ", dateFin=" + dateFin + '}';
    }
    
    
    
    
    
}
