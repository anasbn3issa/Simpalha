/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author cyrin
 */
public class Ressources {
     private int idR;
     private String path,title,description,module;

    public Ressources(int idR, String path, String title, String description, String module) {
        this.idR = idR;
        this.path = path;
        this.title = title;
        this.description = description;
        this.module = module;
    }

    public Ressources() {
    }
    

    public int getIdR() {
        return idR;
    }

    public void setIdR(int idR) {
        this.idR = idR;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    @Override
    public String toString() {
        return "Ressources{" + "idR=" + idR + ", path=" + path + ", title=" + title + ", description=" + description + ", module=" + module + '}';
    }

}
