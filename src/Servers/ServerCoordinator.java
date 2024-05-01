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
import java.net.UnknownHostException;
import orderItem.Task;

/**
 * This Server coordinates where each object type is to be sent to another server to be computed
 * @author HUGHEN FLINT 12177330
 */
public class ServerCoordinator {
    //This class is to be used by TcpServer class, not public.

    public static void main(String args[]) {
        int orderNum = 1;
        //Server waits for client to connect on same port
        try {
            final int serverPort = 3000;
            ServerSocket listenSocket = new ServerSocket(serverPort);
            System.out.println("TCP ServerCoordinator running..." + "\n");
            while (true) {
                //listening for client
                Socket clientSocket = listenSocket.accept();
                Connection c = new Connection(clientSocket);
                System.out.println("ServerCoordinator Recieved Client Object Number: " + orderNum);
                orderNum++;
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
            //t is observed to have a BookOrder in it or MovieOrder
            //t is then sent to navigateClient with appropriate serverport number
            if (t.toString().contains("BookOrder")) {
                System.out.println("Sending book object to ServerBook ....");
                t = (Task) navigateClient(3001, t);
            } else if (t.toString().contains("MovieOrder")) {
                System.out.println("Sending movie object to ServerMovie ....");
                t = (Task) navigateClient(3002, t);
            }

            //data is sent back to client
            System.out.println("Returning Order Back to Original Client ....");
            out.writeObject(t);

            System.out.println();
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
                System.out.println("clientsocket closed");
            }
        }
    }

    //A TCP client to send data to another Server and receive data from those servers
    public static Object navigateClient(int serverPort, Task t) throws ClassNotFoundException {
        Socket s = null;
        String hostName = "localhost";
        try {

            s = new Socket(hostName, serverPort);

            ObjectInputStream in;
            ObjectOutputStream out;

            out = new ObjectOutputStream(s.getOutputStream());
            in = new ObjectInputStream(s.getInputStream());

            //send object
            out.writeObject(t);

            //receive object
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
        //return object
        return t;
    }
}
