/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.Disponibilite;

/**
 *
 * @author α Ω
 */
public interface IServiceDisponibilite extends IService<Disponibilite>{
        public Disponibilite findByTime(String time);
        public Disponibilite findOneByEtat(int id, int etat);
}
