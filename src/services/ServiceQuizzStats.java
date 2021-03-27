/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.QuizzStats;
import interfaces.IServiceQuizzStats;
import java.sql.SQLException;

/**
 *
 * @author Parsath
 */
public class ServiceQuizzStats implements IServiceQuizzStats {
    
    @Override
    public void UpdateValues(QuizzStats q,int totalResult, int totalQuestions, int unresolved, boolean result){
      
        System.out.println("service quizzstats updateValues");
        
        q.setUnresolvedEval(unresolved -1);
        q.setTotalResultEval(totalResult);
        q.setNbQuestionsEval(totalQuestions);
        
        if(result==true)
            q.setTotalResultEval(totalResult+1);        
        

        System.out.println("service quizzstats Update values function : \nunresolved : "+q.getUnresolvedEval()+"\ntotal Questions : "+q.getNbQuestionsEval()+"\nTotal Result : "+q.getTotalResultEval()+"\n");
      
    }
    
    @Override
    public void SetUnresolved(QuizzStats q,int quizzId) throws SQLException{
        
        System.out.println("service quizzstats setUnresolved service Quizz Stats");
        
        ServiceQuestion sq = new ServiceQuestion();
        
        int total = sq.CountQuestions(quizzId);
//        int total = 0;
        
        q.setNbQuestionsEval(total);
        q.setUnresolvedEval(total);
        q.setTotalResultEval(0);
        
//        System.out.println("service quizzstats Set Unresolved function: \nunresolved : "+q.getUnresolvedEval()+"\ntotal Questions : "+q.getNbQuestionsEval()+"\nTotal Result : "+q.getTotalResultEval()+"\n");
    
    }
}
