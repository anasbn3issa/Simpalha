/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.QuizzStats;
import java.sql.SQLException;

/**
 *
 * @author Parsath
 */
public interface IServiceQuizzStats {
    
//    reconvertis les stats du quizz
    public void UpdateValues(QuizzStats q,int totalResult, int totalQuestions, int unresolved, boolean result);
    
//    sets quizz Stats to default
    public void SetUnresolved(QuizzStats q,int quizzId) throws SQLException;
}
