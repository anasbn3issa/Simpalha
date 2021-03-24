/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author win10
 */
public class Invitation {
    private int idv,idu;
    private String email;
    private boolean validation;

    public Invitation(int idv, int idu, String email, boolean validation) {
        this.idv = idv;
        this.idu = idu;
        this.email = email;
        this.validation = validation;
    }

    public int getIdv() {
        return idv;
    }

    public void setIdv(int idv) {
        this.idv = idv;
    }

    public int getIdu() {
        return idu;
    }

    public void setIdu(int idu) {
        this.idu = idu;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isValidation() {
        return validation;
    }

    public void setValidation(boolean validation) {
        this.validation = validation;
    }

    @Override
    public String toString() {
        return "Invitation{" + "idv=" + idv + ", idu=" + idu + ", email=" + email + ", validation=" + validation + '}';
    }
    
}
