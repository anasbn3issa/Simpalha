/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.List;

/**
 *
 * @author α Ω
 */
public interface IService<T> {
    
     public void Create(T variable);
    public void Update(T variable);
    public List<T> Read();
    public void Delete(T variable);
    public List<T> findAllById(int id);
    public T findById(int id);
    public int count();

    
}
