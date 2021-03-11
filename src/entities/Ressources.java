/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author cyrin
 */
public class Ressources {
    private int idR;
    private String path;
    private String title;
    private String description;

    
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

    public Ressources() {
    }
    
    @Override
    public String toString() {
        return "Ressources{" + "idR=" + idR + ", path=" + path + ", title=" + title + ", description=" + description + "}\n";
        
    }

   
}
