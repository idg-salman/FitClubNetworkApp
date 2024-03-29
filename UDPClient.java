/*
 **********************************************************************************************************
 * Author:      Md Afsar Uddin Salman
 * Student ID:  12190848
 * File Name:   TCPClient.java
 * Date:        26-Mar-2024
 * Purpose:     Designed to implement a client application that sends data to a server using the
 *              User Datagram Protocol (UDP). It takes a file name as a command-line argument, sends
 *              the data to the server, receives a response, and displays it to the user.
 **********************************************************************************************************
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPClient {

  private static final int BUFFER_SIZE = 1000;
  private static final int SERVER_PORT = 2248;
  private static final String SERVER_IP = "localhost";

  public static void main(String args[]) {
    if (args.length != 1) {
      System.out.println("Usage: java UDPClient <file name>");
      return;
    }

    DatagramSocket datagramSocket = null;
    try {
      // creating a datagram socket to send the data
      datagramSocket = new DatagramSocket();

      // creating a datagram packet to send the data to the server
      DatagramPacket request = new DatagramPacket(
        args[0].getBytes(),
        args[0].length(),
        InetAddress.getByName(SERVER_IP),
        SERVER_PORT
      );

      // sending the data to the server
      datagramSocket.send(request);

      // creating a buffer to receive the data
      byte[] buffer = new byte[BUFFER_SIZE];

      // creating a datagram packet to receive the data
      DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
      datagramSocket.receive(reply);

      // converting the data from byte to string
      String response = new String(reply.getData(), 0, reply.getLength());

      // displaying the server response
      System.out.print("Server Response:\n" + response);
    } catch (SocketException e) {
      System.out.println(e.getMessage());
    } catch (IOException e) {
      System.out.println(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (datagramSocket != null) datagramSocket.close();
    }
  }
}
