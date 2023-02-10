/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.oop;

/**
 *
 * @author OshadiPC
 */
public class Recipient {
    private String name;
    private String emailAddress;
    private static int numOfRecipients = 0; //keep a count of the recipient objects
    
    public Recipient(){
        numOfRecipients = numOfRecipients + 1; //increase the recipient count by 1
    }
    public void setName(String name){
        this.name = name;
    }
    public void setEmailAddress(String emailAddress){
        this.emailAddress = emailAddress;
    }
    public String getName(){
        return name;
    }
    public String getEmailAddress(){
        return emailAddress;
    }
    public static int getNumOfRecipients(){
        return numOfRecipients;
    }
}
