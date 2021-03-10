/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author Parsath
 */
public class QuizzStats {
    
    private int unresolvedEval;
    private int totalResultEval;
    private int nbQuestionsEval;

    public QuizzStats() {
    }

    public int getUnresolvedEval() {
        return unresolvedEval;
    }

    public void setUnresolvedEval(int unresolvedEval) {
        this.unresolvedEval = unresolvedEval;
    }

    public int getTotalResultEval() {
        return totalResultEval;
    }

    public void setTotalResultEval(int totalResultEval) {
        this.totalResultEval = totalResultEval;
    }

    public int getNbQuestionsEval() {
        return nbQuestionsEval;
    }

    public void setNbQuestionsEval(int nbQuestionsEval) {
        this.nbQuestionsEval = nbQuestionsEval;
    }

    @Override
    public String toString() {
        return "QuizzStats{" + "unresolvedEval=" + unresolvedEval + ", totalResultEval=" + totalResultEval + ", nbQuestionsEval=" + nbQuestionsEval + '}';
    }
    
    
}
