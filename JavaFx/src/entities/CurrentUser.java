/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author win10
 */
public class CurrentUser {
    
    private static CurrentUser single_instance=null; 
     public int id;
   
     private String email;
     public int targetId;
     public String code;

     private CurrentUser(){
         
         //id=0;
         
         email="";
         targetId=0;
         code="";
     }
     
     
     public static CurrentUser CurrentUser() 
	{ 
		// To ensure only one instance is created 
		if (single_instance == null) 
		{ 
			single_instance = new CurrentUser(); 
		} 
		return single_instance; 
	} 
     
}
