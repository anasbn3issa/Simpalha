/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.Reclamation;

/**
 *
 * @author PC
 */
public interface IserviceReclamation extends IService<Reclamation> {
    public void Validate(Reclamation Rec);
    public void UpdateStatus(Reclamation Rec);
    
}
