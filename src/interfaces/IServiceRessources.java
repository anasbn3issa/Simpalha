/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.sql.SQLException;
import java.util.List;
import entities.Ressources;

/**
 *
 * @author cyrin
 */
public interface IServiceRessources {
        public void Create(Ressources R);
    public void Update(Ressources R);
    public List<Ressources> Read()throws SQLException;
    public void Delete(Ressources R);
    //les methodes avancées, métiers a ajouter
    
}
