/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Question;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Parsath
 */
public interface IServiceQuestion {
    public void AddQuestion(Question q);
    public List<Question> ReadQuestions() throws SQLException;
    
}
