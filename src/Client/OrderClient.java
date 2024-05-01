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
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class OrderClient {

    public static void main(String args[]) throws ClassNotFoundException {
        while (0 == 0) {
            //initialises variables
            String inputStr;
            int choice;
            int amount = 0;
            double price = 0;
            String orderType1 = null;
            String orderType2 = null;

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
            inputStr = input.nextLine();
            while (!inputStr.matches("[1-3]+")) {
                System.out.println("You must enter appropriate number."+"\n");
                System.out.print("Enter your option: ");
                inputStr = input.nextLine();
            }
            choice = Integer.parseInt(inputStr);

            switch (choice) {
                case 1:
                    orderType1 = "book";
                    orderType2 = "BookOrder";
                    break;
                case 2:
                    orderType1 = "movie";
                    orderType2 = "MovieOrder";
                    break;
                case 3:
                    System.exit(0);
            }

            System.out.print("Enter the number of " + orderType1 + "s: ");
            inputStr = input.nextLine();
            while (!inputStr.matches("[0-9]+") || Integer.parseInt(inputStr) <= 0) {
                System.out.println("You must enter appropriate number."+"\n");
                System.out.print("Enter the number of " + orderType1 + "s: ");
                inputStr = input.nextLine();
            }
            amount = Integer.parseInt(inputStr);

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

                ObjectInputStream in = null;
                ObjectOutputStream out = null;

                out = new ObjectOutputStream(s.getOutputStream());
                in = new ObjectInputStream(s.getInputStream());

                if (choice == 1) {
                    t = new BookOrder(amount, price);
                } else if (choice == 2) {
                    t = new MovieOrder(amount, price);
                }

                //send message
                System.out.println("SENDING OBJECT TO SERVER..........");
                out.writeObject(t);

                System.out.println("RECIEVING COMPUTED OBJECT FROM SERVER........");
                t = (Task) in.readObject();

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
            } catch (NullPointerException e){
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
