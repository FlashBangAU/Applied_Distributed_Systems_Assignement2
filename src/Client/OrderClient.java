/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class OrderClient {

    public static void main(String args[]) {
        //initialises variables
        int intInput;
        int amount = 0;
        double price = 0;
        String orderType1 = null;
        String orderType2 = null;
        String message;
        
        int returnAmount = 0;
        double returnPrice = 0;
        double returnTax = 0;
        
        //scanner class created
        Scanner input = new Scanner(System.in);

        //User options and inputs
        System.out.print("PLEASE PLACE YOUR ORDER BY SELECTING A NUMBER" + "\n"
                + "*************************" + "\n"
                + "1.Purchase Book" + "\n"
                + "2.Purchase Movie" + "\n"
                + "3.Exit" + "\n"
                + "*************************" + "\n"
                + "Enter your option: ");
        intInput = input.nextInt();

        if (intInput == 3) {
            System.exit(0);
        } else {
            switch (intInput) {
                case 1:
                    orderType1 = "book";
                    orderType2 = "Book";
                    break;
                case 2:
                    orderType1 = "movie";
                    orderType2 = "Movie";
                    break;
            }
            System.out.print("Enter the number of " + orderType1 + "s: ");
            amount = input.nextInt();
            
            System.out.print("Enter the " + orderType1 + " price: ");
            price = input.nextDouble();  
        }
        message = intInput + ":" + amount + ":" + price;

        //arguments supply message and hostname of destination
        //if running from a command prompt
        Socket s = null;
        String hostName = "localhost";
        try {
            int serverPort = 3000;

            s = new Socket(hostName, serverPort);
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            //send message
            System.out.println("SENDING OBJECT TO SERVER..........");
            out.writeUTF(message);

            String data = in.readUTF();
            System.out.println("RECIEVING COMPUTED OBJECT FROM SERVER........");
            
            System.out.println("Number of " + orderType2 + "s:" + returnAmount + 
                    "       Price:" + returnPrice + "       Tax:" + returnTax +
                    "       Bill Total for " + orderType2 + ":" +
                    ((returnAmount * returnPrice)+returnTax));
            
        } catch (UnknownHostException e) {
            System.out.println("Sock:" + e.getMessage());
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO:" + e.getMessage());
        } finally {
            if (s != null)
	     try {
                s.close();
            } catch (IOException e) {
                System.out.println("close:" + e.getMessage());
            }
        }
    }
}
