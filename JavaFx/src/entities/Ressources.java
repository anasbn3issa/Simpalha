/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import javafx.scene.control.Button;

/**
 *
 * @author cyrin
 */
public class Ressources {

    private int idR;
    private String path;
    private String title;
    private String description;
private String module;

    public String getModule() {
        return module;
    }

    public void setModule(String Module) {
        this.module = Module;
    }
    public void setIdR(int idR) {
        this.idR = idR;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdR() {
        return idR;
    }

    public String getPath() {
        return path;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Ressources(int idR) {
        this.idR = idR;
    }

    public Ressources(int idR, String path, String description, String title) {
        this.idR = idR;
        this.description = description;
        this.path = path;
        this.title = title;
      //  this.buttonModify = new Button("buttonModify");

      
    }

     public Ressources(int idR, String path, String description, String title,String module) {
        this.idR = idR;
        this.description = description;
        this.path = path;
        this.title = title;
        this.module= module;
    }


    public Ressources() {
    }

    @Override
    public String toString() {
        return "Ressources{" + "idR=" + idR + ", path=" + path + ", title=" + title + ", description=" + description + "module=" + module +"}\n";

    }

}
