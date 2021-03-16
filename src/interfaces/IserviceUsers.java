/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.Users;


/**
 *
 * @author win10
 */
public interface IserviceUsers extends IService<Users> {
   public Users finfById(int id);
   public Boolean usernameExist(String username);
}
