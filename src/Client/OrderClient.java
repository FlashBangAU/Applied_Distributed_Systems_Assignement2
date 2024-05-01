/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client;

import orderItem.BookOrder;
import orderItem.MovieOrder;
import orderItem.Task;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *  This is a client end of a TCP Connection that receives user input for a purchase of a Book or Movie
 * @author HUGHEN FLINT 12177330
 */
public class OrderClient {

    public static void main(String args[]) throws ClassNotFoundException {
        while (0 == 0) {
            //initialises variables
            String inputStr;
            int choice;
            int amount;
            double price;
            String orderType1 = null;

            Task t = null;

            //scanner class created
            Scanner input = new Scanner(System.in);

            //User options and inputs
            System.out.print("PLEASE PLACE YOUR ORDER BY SELECTING A NUMBER" + "\n"
                    + "*********************" + "\n"
                    + "1.Purchase Book" + "\n"
                    + "2.Purchase Movie" + "\n"
                    + "3.Exit" + "\n"
                    + "*********************" + "\n"
                    + "Enter your option: ");
            //user selects their choice
            inputStr = input.nextLine();
            while (!inputStr.matches("[1-3]+")) {
                System.out.println("You must enter appropriate number." + "\n");
                System.out.print("Enter your option: ");
                inputStr = input.nextLine();
            }
            choice = Integer.parseInt(inputStr);

            //sets values for Strings in further questions or to exit application
            switch (choice) {
                case 1:
                    orderType1 = "book";
                    break;
                case 2:
                    orderType1 = "movie";
                    break;
                case 3:
                    System.exit(0);
            }

            //user enters quantity that they would like to purchase
            System.out.print("Enter the number of " + orderType1 + "s: ");
            inputStr = input.nextLine();
            while (!inputStr.matches("[0-9]+") || Integer.parseInt(inputStr) <= 0) {
                System.out.println("You must enter appropriate number." + "\n");
                System.out.print("Enter the number of " + orderType1 + "s: ");
                inputStr = input.nextLine();
            }
            amount = Integer.parseInt(inputStr);

            //user enters price of item that they are purchasing
            System.out.print("Enter the " + orderType1 + " price: ");
            inputStr = input.nextLine();
            while (!inputStr.matches("[0-9.]+") || Double.parseDouble(inputStr) <= 0) {
                System.out.println("You must enter appropriate number.");
                System.out.print("Enter the " + orderType1 + " price: ");
                inputStr = input.nextLine();
            }
            price = Double.parseDouble(inputStr);

            //arguments supply message and hostname of destination
            //if running from a command prompt
            Socket s = null;
            String hostName = "localhost";
            try {
                int serverPort = 3000;

                s = new Socket(hostName, serverPort);

                ObjectInputStream in;
                ObjectOutputStream out;

                out = new ObjectOutputStream(s.getOutputStream());
                in = new ObjectInputStream(s.getInputStream());

                //Task object is created with the inputed values to the appropriate server type
                if (choice == 1) {
                    t = new BookOrder(amount, price);
                } else if (choice == 2) {
                    t = new MovieOrder(amount, price);
                }

                //send object
                System.out.println("SENDING OBJECT TO SERVER..........");
                out.writeObject(t);
                
                //receive object
                System.out.println("RECIEVING COMPUTED OBJECT FROM SERVER........");
                t = (Task) in.readObject();

                //prints total price along with tax, amount and price per unit
                if (choice == 1) {
                    System.out.println(((BookOrder) t).getResult());
                } else if (choice == 2) {
                    System.out.println(((MovieOrder) t).getResult());
                }

            } catch (UnknownHostException e) {
                System.out.println("Sock:" + e.getMessage());
            } catch (EOFException e) {
                System.out.println("EOF:" + e.getMessage());
            } catch (IOException e) {
                System.out.println("IO:" + e.getMessage());
            } catch (NullPointerException e) {
                System.out.println("NullPointer:" + e.getMessage());
            } finally {
                if (s != null)
	     try {
                    s.close();
                } catch (IOException e) {
                    System.out.println("close:" + e.getMessage());
                }
            }
            System.out.println("===============================================" + "\n");
        }
    }
}
