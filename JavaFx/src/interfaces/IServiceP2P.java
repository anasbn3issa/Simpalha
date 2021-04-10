/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.Meet;
import java.util.List;

/**
 *
 * @author α Ω
 */
public interface IServiceP2P extends IService<Meet> {
        public Meet findById(String id);
        public List<Meet> ReadById(int id);
        public List<Meet> ReadStudentsById(int id);
        public int finishedCount();
        public int ScheduledCount();
        public void setFinished(String id);

    
}
