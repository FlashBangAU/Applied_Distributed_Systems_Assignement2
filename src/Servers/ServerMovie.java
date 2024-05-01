/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servers;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import orderItem.Task;

/**
 * This Server receives movie related objects to be computed then returned
 * @author HUGHEN FLINT 12177330
 */
public class ServerMovie {

    public static void main(String args[]) {
        int orderNum = 1;
        try {
            int serverPort = 3002;
            ServerSocket listenSocket = new ServerSocket(serverPort);
            System.out.println("TCP ServerMovie running..." + "\n");
            while (true) {
                Socket clientSocket = listenSocket.accept();
                MovieConnection c = new MovieConnection(clientSocket);
                System.out.println("ServerMovie Recieved Client Object Number: " + orderNum);
                orderNum++;
            }

        } catch (IOException e) {
            System.out.println("Listen socket:" + e.getMessage());
        }
    }
}

class MovieConnection extends Thread {

    ObjectInputStream in;
    ObjectOutputStream out;
    Socket clientSocket;

    //connection created
    public MovieConnection(Socket aClientSocket) {

        try {
            clientSocket = aClientSocket;
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
            this.start();
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    public void run() {

        try {
            //object received
            Task t = (Task) in.readObject();
            
            //calculate total cost and tax
            t.executeTask();

            //returns object to server coordinator
            out.writeObject(t);
            System.out.println("Computed Total Bill for Movie Order. Sending back to client....");

            System.out.println("");
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException:" + e.getMessage());
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Error closing client socket: " + e.getMessage());
            }
        }

    }
}
