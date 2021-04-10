/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.QuizzWrapper;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 *
 * @author Parsath
 */
public interface IServiceWrapper {
    
    public ObservableList<QuizzWrapper> ObservableListQuestionsWrapper(int quizzId) throws SQLException;
    
}
