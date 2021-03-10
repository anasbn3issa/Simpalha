/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.Meet;

/**
 *
 * @author α Ω
 */
public interface IServiceP2P extends IService<Meet> {
        public Meet findById(String id);

    
}
