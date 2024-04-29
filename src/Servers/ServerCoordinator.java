/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import orderItem.BookOrder;
import orderItem.MovieOrder;
import orderItem.Task;

/**
 *
 * @author User
 */
public class ServerCoordinator {
    //This class is to be used by TcpServer class, not public.

    public static void main(String args[]) {
        //Server waits for client to connect on same port
        try {
            final int serverPort = 3000;
            ServerSocket listenSocket = new ServerSocket(serverPort);
            while (true) {
                //listening for client
                Socket clientSocket = listenSocket.accept();
                Connection c = new Connection(clientSocket);
                System.out.println("Recieving data from client: " + clientSocket.getPort());
            }
        } catch (IOException e) {
            System.out.println("Listen :" + e.getMessage());
        }
    }
}

//This class is to be used by TcpServer class, not public.
class Connection extends Thread {

    ObjectInputStream in;
    ObjectOutputStream out;
    Socket clientSocket;

    //connection created
    public Connection(Socket aClientSocket) {
        try {
            clientSocket = aClientSocket;
            in = new ObjectInputStream(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            this.start();
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    //retreives data, saves data and sends reply
    public void run() {
        try { // an echo server
            //data recieved
            Task t = (Task) in.readObject();
            System.out.println(t);
            if (t.toString().contains("BookOrder")) {
                System.out.print("Sending data to ServerBook");
                t = (Task) navigateClient(3001);
            } else if (t.toString().contains("MovieOrder")) {
                System.out.print("Sending data to ServerMovie");
            }
            t.getResult();

            //data is sent back to client
            out.writeObject(t);

        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage());
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {/*close failed*/
            }
        }
    }

    public static Object navigateClient(int serverPort) throws ClassNotFoundException {
        Socket s = null;
        Task t = null;
        String hostName = "localhost";
        try {

            s = new Socket(hostName, serverPort);

            ObjectInputStream in = null;
            ObjectOutputStream out = null;

            out = new ObjectOutputStream(s.getOutputStream());
            in = new ObjectInputStream(s.getInputStream());
            
            //send message
            System.out.println("SENDING OBJECT TO SERVER..........");
            out.writeObject(t);

            System.out.println("RECIEVING COMPUTED OBJECT FROM SERVER........");
            t = (Task) in.readObject();

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
        return t;
    }
}
