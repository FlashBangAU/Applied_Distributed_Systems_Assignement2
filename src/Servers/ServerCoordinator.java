/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author User
 */
public class ServerCoordinator {
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
            Socket s = null;
            final String hostName = "localhost";
            int serverPort = 0;
            String response = "Error happened with ServerCoordinator";

            try { // an echo server
                //data recieved
                String data = in.readUTF();

                String[] orderArray = data.split(":");
                if (orderArray[0].equals("1")) {
                    serverPort = 3001;
                } else if (orderArray[0].equals("2")) {
                    serverPort = 3002;
                }

                try {
                    //connection is made
                    s = new Socket(hostName, serverPort);
                    DataInputStream in = new DataInputStream(s.getInputStream());
                    DataOutputStream out = new DataOutputStream(s.getOutputStream());

                    //send message
                    out.writeUTF(data);
                    System.out.println("Sending Data to Movie or Book Server..............");

                    //server response
                    response = in.readUTF();
                    System.out.println("Server Response: ");

                    //counter for how many clients have been entered while client has been running
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

                //data is sent back to client
                out.writeUTF(response);

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
}
