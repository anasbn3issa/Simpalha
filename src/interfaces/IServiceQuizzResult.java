/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.Quizz;
import entities.QuizzResult;
import javafx.collections.ObservableList;

/**
 *
 * @author Parsath
 */
public interface IServiceQuizzResult extends IService<QuizzResult> {
    
//    Returns an observablelist of all Quizz Results (for tableview display)
    public ObservableList<QuizzResult> ObservableListAllQuizzResults();
    
//    Returns an observablelist of all Quizz Results of a certain Quizz (for tableview display)
    public ObservableList<QuizzResult> ObservableListQuizzResults(int quizzId);
}
