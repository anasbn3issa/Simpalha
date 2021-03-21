/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author win10
 */
public class Candidature {

   
    private String email,spécialité,userrname;
    private int idc;
    private int idu;
    private LocalDateTime datec;
     private String fichier;
    private int status;
    
    
     public static Object getSelectionModel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getUserrname() {
        return userrname;
    }

    public void setUserrname(String userrname) {
        this.userrname = userrname;
    }
   
    

    public Candidature(String email, String spécialité, int status, int idc, String fichier, LocalDateTime datec) {
        this.email = email;
        this.spécialité = spécialité;
        this.status = status;
        this.idc = idc;
        this.fichier = fichier;
        this.datec = datec;
    }

    public int getId() {
        return idu;
    }

    public void setId(int idu) {
        this.idu = idu;
    }

    public LocalDateTime getDatec() {
        return datec;
    }

    public void setDatec(LocalDateTime datec) {
        this.datec = datec;
    }

    public Candidature(int status, int idu, LocalDateTime datec) {
        this.status = status;
        this.idu = idu;
        this.datec = datec;
    }
    

  
    public Candidature() {
        
    }

    public Candidature(String email, String spécialité, String fichier) {
        this.email = email;
        this.spécialité = spécialité;
      
        this.fichier = fichier;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpécialité() {
        return spécialité;
    }

    public void setSpécialité(String spécialité) {
        this.spécialité = spécialité;
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
   public LocalDateTime getDate() {
        return datec;
    }

    public void setDate(LocalDateTime date) {
        this.datec = date;
    }


    public String getFichier() {
        return fichier;
    }

    public void setFichier(String fichier) {
        this.fichier = fichier;
    }
    
 public String getDateToString(){
        return datec.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }
    
 public String getStatusToString(){
        if (status==1)
            return "En attente";
        if (status==2)
            return "Confirmée";
        return "Annulée";
    }
    @Override
    public String toString() {
        return "Candidature{" + "email=" + email + ", sp\u00e9cialit\u00e9=" + spécialité + ", status=" + status + ", userrname=" + userrname + ", idc=" + idc + ", idu=" + idu + ", fichier=" + fichier + ", datec=" + datec + '}';
    }

    

   
    

  
    
}
