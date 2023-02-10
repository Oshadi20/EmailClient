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
//IndexNo: 200458M
import java.util.*;
import java.util.Scanner;
import java.io.*;  
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileInputStream; 
import java.io.FileOutputStream; 
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class Email_Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //start email client
        //code to create objects for each recipient in clientList.txt
        //contains details of all recipients
        ArrayList<ArrayList<String> > data =  new ArrayList< >();
        
        //load the recipient details from the text file
        try{
            //a new file instance
            File file = new File("C:\\Users\\OshadiPC\\Desktop\\netbeans\\OOP\\clientList.txt"); 
            //reads the file
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            
            String line = reader.readLine();
            while(line != null){
                //split details of the recipient into an array
                String[] dataItemArr = line.split("[: ,]+");
                //convert array into a arraylist
                ArrayList<String> dataItem = new ArrayList<>(Arrays.asList(dataItemArr));
                //add details of each recipient to data arrayList
                data.add(dataItem);
                //read next line
                line = reader.readLine();
            }
            fileReader.close();
            
        }catch(IOException e){
            e.printStackTrace();
        }
        
        //iterate through data list to separate into 3 lists
        //(official recipient, official friend, personal recipient)
        ArrayList<ArrayList<String> > official_recipient =  new ArrayList<ArrayList<String> >();
        ArrayList<ArrayList<String> > official_friend =  new ArrayList<ArrayList<String> >();
        ArrayList<ArrayList<String> > personal_recipient =  new ArrayList<ArrayList<String> >();
        
        for(int i=0 ; i<data.size(); i++){
            String type;
            type = data.get(i).get(0);
            //details list of each recipient
            ArrayList<String> dataIn = data.get(i);
            if(type.equals("Official")){
                official_recipient.add(dataIn);
            }
            else if(type.equals("Office_friend")){
                official_friend.add(dataIn);
            }
            else{
                personal_recipient.add(dataIn);
            }
        }
        
        //create objects for each recipient in clientList.txt
        //Official_recipient objects
        int noOffRec = official_recipient.size();
        OfficialRecipient[] offRecObj = new OfficialRecipient[noOffRec];
        for(int i=0; i<noOffRec; i++){
            offRecObj[i] = new OfficialRecipient();
            offRecObj[i].setName(official_recipient.get(i).get(1));
            offRecObj[i].setEmailAddress(official_recipient.get(i).get(2));
            offRecObj[i].setDesignation(official_recipient.get(i).get(3));
        }
        //Official_friend objects
        int noOffFrnd = official_friend.size();
        OfficialFriend[] offFrndObj = new OfficialFriend[noOffFrnd];
        for(int i=0; i<noOffFrnd; i++){
            offFrndObj[i] = new OfficialFriend();
            offFrndObj[i].setName(official_friend.get(i).get(1));
            offFrndObj[i].setEmailAddress(official_friend.get(i).get(2));
            offFrndObj[i].setDesignation(official_friend.get(i).get(3));
            offFrndObj[i].setBirthday(official_friend.get(i).get(4));
        }
        //Personal_recipient objects
        int noPerRec = personal_recipient.size();
        PersonalRecipient[] perRec = new PersonalRecipient[noPerRec];
        for(int i=0; i<noPerRec; i++){
            perRec[i] = new PersonalRecipient();
            perRec[i].setName(personal_recipient.get(i).get(1));
            perRec[i].setNickName(personal_recipient.get(i).get(2));
            perRec[i].setEmailAddress(personal_recipient.get(i).get(3));
            perRec[i].setBirthday(personal_recipient.get(i).get(4));
        }
        
        //send birthday greetings
        //arrayList contains recipients who have birthday on current date
        ArrayList<Recipient> recOfBdays = new ArrayList();
        Scanner input5 = new Scanner(System.in);
        System.out.println("Enter current Date(yyyy/MM/dd): ");
        String currDate = input5.nextLine();
        //only consider (MM/dd) for birthday
        String bdate = currDate.substring(5);
        for(int i=0; i<noOffFrnd; i++){
            if(bdate.equals(offFrndObj[i].getBirthday().substring(5))){
              recOfBdays.add(offFrndObj[i]);
            }
        }
        for(int i=0; i<noPerRec; i++){
            if(bdate.equals(perRec[i].getBirthday().substring(5))){
                recOfBdays.add(perRec[i]);
            }
        }
        //arrayList contains all email objects
        ArrayList<Email> emailObj = new ArrayList();
        String sub = "A Birthday Greeting"; //subject of email
        for(int i=0; i<recOfBdays.size(); i++){
            emailObj.add(i, new Email());
            emailObj.get(i).setDate(currDate);
            //check for Officialfriend or personal recipient and send emails
            if((recOfBdays.get(i)) instanceof OfficialFriend){ 
                emailObj.get(i).createAndSendEmail(recOfBdays.get(i).getEmailAddress(), sub, "Wish you a Happy Birthday. Oshadi Perera");
            }
            else{
                emailObj.get(i).createAndSendEmail(recOfBdays.get(i).getEmailAddress(), sub, "Hugs and love on your birthday. Oshadi Perera");
            }
        }
        
        Scanner scanner = new Scanner(System.in);
            System.out.println("Enter option type: \n"
                  + "1 - Adding a new recipient\n"
                  + "2 - Sending an email\n"
                  + "3 - Printing out all the recipients who have birthdays\n"
                  + "4 - Printing out details of all the emails sent\n"
                  + "5 - Printing out the number of recipient objects in the application");

            int option = scanner.nextInt();

            switch(option){
                  case 1:
                      // input format - Official: nimal,nimal@gmail.com,ceo
                      // Use a single input to get all the details of a recipient
                      Scanner input1 = new Scanner(System.in);
                      System.out.println("Enter details of recipient(format - Official: nimal,nimal@gmail.com,ceo): ");
                      String userReci = input1.nextLine();
                      
                      // code to add a new recipient
                      // store details in clientList.txt file 
                      try(FileWriter fWriter = new FileWriter("clientList.txt", true); //"true" for append data
                              BufferedWriter bWriter = new BufferedWriter(fWriter);
                              PrintWriter pWriter = new PrintWriter(bWriter);){
                          pWriter.println(userReci); //add data in a new line
                      }catch(IOException exec){
                          exec.printStackTrace();
                      }
                      // Hint: use methods for reading and writing files
                      break;
                  case 2:
                      // input format - email, subject, content
                      Scanner input2 = new Scanner(System.in);
                      System.out.println("Enter mail details(format - email, subject, content): ");
                      String emailInput = input2.nextLine();
                      String[] contentArr = emailInput.split(",");
                      String emailAddress, subject, content;
                      emailAddress = contentArr[0];
                      subject = contentArr[1];
                      content = contentArr[2];
                      
                      // code to send an email
                      Email email =new Email();
                      email.setDate(currDate);
                      email.createAndSendEmail(emailAddress, subject, content);
                      
                      //add this email object to emailObj arrayList
                      emailObj.add(email);
                      
                      break;
                  case 3:
                      // input format - yyyy/MM/dd (ex: 2018/09/17)
                      Scanner input3 = new Scanner(System.in);
                      System.out.println("Enter current date(yyyy/MM/dd): ");
                      String bdayinput = input3.nextLine();
                      //only consider (MM/dd) for birthday
                      String bdaysub = bdayinput.substring(5);
                      
                      // code to print recipients who have birthdays on the current date
                      System.out.println("Recipients who have birthdays on today");
                      for(int i=0; i<noOffFrnd; i++){
                          if(bdaysub.equals(offFrndObj[i].getBirthday().substring(5))){
                              System.out.println(offFrndObj[i].getName());
                          }
                      }
                      for(int i=0; i<noPerRec; i++){
                          if(bdaysub.equals(perRec[i].getBirthday().substring(5))){
                              System.out.println(perRec[i].getName());
                          }
                      }
                      
                      break;
                  case 4:
                      // input format - yyyy/MM/dd (ex: 2018/09/17)
                      Scanner input4 = new Scanner(System.in);
                      System.out.println("Enter date(yyyy/MM/dd): ");
                      String inputDate = input4.nextLine();
                      
                      String desDate = inputDate.replace("/", ""); //date for deserialize
                      // code to print the details of all the emails sent on the input date
                      //deserialize all email objects
                      Object obj = null;
                      try{
                          //deserialize objects from files which are created according to date
                          //(ex: email20221010.ser)
                          String desFileName = "email" + desDate + ".ser";
                          //path of the file to be deserialized
                          String desPath = "C:\\Users\\OshadiPC\\Desktop\\netbeans\\OOP\\"+desFileName;
                          //a new file instance
                          File desFile = new File(desPath);
                          //make a FileInputStream
                          FileInputStream fileIn = new FileInputStream(desFile);
                          //make an ObjectInputStream
                          ObjectInputStream objIn = new ObjectInputStream(fileIn);
                          while((obj = objIn.readObject()) instanceof EndOfFile == false){
                              if((((Email)obj).getDate()).equals(inputDate)){
                                  System.out.println("Recipient: " + ((Email)obj).getEmailAddressTo());
                                  System.out.println("Subject: "+((Email)obj).getSubject());
                              }
                          }
                          objIn.close();
                          fileIn.close();
                      }catch(IOException exec){
                          exec.printStackTrace();
                      }catch(ClassNotFoundException c){
                          c.printStackTrace();
                      }catch(NullPointerException ne){
                          ne.printStackTrace();
                      }
                      
                      break;
                  case 5:
                      // code to print the number of recipient objects in the application
                      System.out.println("number of recipient objects in the application: " + Recipient.getNumOfRecipients());
                      break;

            }
        //serialize all email objects which are sent throught the day
        try{
            //serialize objects to files which are created according to date
            //(ex: email20221010.ser)
            String serDate = currDate.replace("/", "");
            String serFileName = "email" + serDate + ".ser";
            //path of the file
            String serPath = "C:\\Users\\OshadiPC\\Desktop\\netbeans\\OOP\\"+serFileName;
            //a new file instance
            File serFile = new File(serPath);
            //make a FileOutputStream
            FileOutputStream fileOut = new FileOutputStream(serFile);
            //make an ObjectOutputStream
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
            for(int i=0; i<(emailObj.size()); i++){
                //write the object
                objOut.writeObject(emailObj.get(i));
            }
            objOut.writeObject(new EndOfFile()); //to indicate the end of file
            objOut.close();
            fileOut.close();
        }catch(IOException exec){
            exec.printStackTrace();
        }
           

       
    }
    
}
