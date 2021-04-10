/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.Feedback;

/**
 *
 * @author α Ω
 */
public interface IServiceFeedback extends IService<Feedback>{
        public Feedback findByMeetId(String id);

    
}
