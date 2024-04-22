/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author User
 */
public class ServerCoordinator {
    //This class is to be used by TcpServer class, not public.

    public static void main(String args[]) {
        //Server waits for client to connect on same port
        try {
            final int serverPort = 3001;
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

    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;

    //connection created
    public Connection(Socket aClientSocket) {
        try {
            clientSocket = aClientSocket;
            in = new DataInputStream(
                    clientSocket.getInputStream());
            out = new DataOutputStream(
                    clientSocket.getOutputStream());
            this.start();
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    //retreives data, saves data and sends reply
    public void run() {
        try { // an echo server
            //data recieved
            String data = in.readUTF();

            //data is sent back to client
            out.writeUTF("Save Data of the member number: ");

        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO:" + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {/*close failed*/
            }
        }
    }
}
