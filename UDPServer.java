/*
 **********************************************************************************************************
 * Author:      Md Afsar Uddin Salman
 * Student ID:  12190848
 * File Name:   TCPClient.java
 * Date:        26-Mar-2024
 * Purpose:     Establishes a UDP server that listens for client requests containing filenames. If the
 *              requested file exists, it reads serialized objects from the file, formats them into a
 *              table, and sends the table back to the client. Error messages are sent if the file does
 *              not exist or if an invalid request is received.
 **********************************************************************************************************
 */

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServer {

  private static final int SERVER_PORT = 2248;
  private static final int BUFFER_SIZE = 1000;
  private static final String SERIALIZED_FILE = "memberlistObject";

  private static DatagramSocket datagramSocket = null;
  private static DatagramPacket datagramPacket = null;

  public static void main(String args[]) {
    File serializeFile = new File(SERIALIZED_FILE);

    while (true) {
      try {
        // created a datagram socket to listen at the specified port
        datagramSocket = new DatagramSocket(SERVER_PORT);

        // displaying message to the server console that server is running
        System.out.println("UDP Server is running at port: " + SERVER_PORT);

        // buffer to receive data
        byte[] buffer = new byte[BUFFER_SIZE];

        // created a datagram packet to receive the data from the client
        datagramPacket = new DatagramPacket(buffer, buffer.length);

        // receiving the data from the client and storing it in the datagram packet
        datagramSocket.receive(datagramPacket);

        // converting the data from byte to string
        String clientRequest = new String(
          datagramPacket.getData(),
          0,
          datagramPacket.getLength()
        );

        // checking that if the client request is equal to the file name
        if (clientRequest.equals(serializeFile.getName())) {
          // checking if the serialize file exists
          if (serializeFile.exists()) {
            // created an object input stream to read the serialized file
            ObjectInputStream objectInputStream = new ObjectInputStream(
              new FileInputStream(serializeFile)
            );

            // created a string builder to store the data in a string format
            StringBuilder stringBuilder = new StringBuilder();

            try {
              // created a string format to display the data in a table format
              String format = "| %1$-20s| %2$-20s| %3$-38s| %4$-13s|\n";

              // appending the line at the top of the table
              stringBuilder.append("=".repeat(100) + "\n");

              // appending the header of the table
              stringBuilder.append(
                String.format(
                  format,
                  "First Name",
                  "Last Name",
                  "Address",
                  "Phone Number"
                )
              );

              // appending the line to separate the header from the data
              stringBuilder.append("-".repeat(100) + "\n");

              // declaring an object to store the object read from the file
              Object object = null;

              // reading the object from the file and storing it in the object
              while ((object = objectInputStream.readObject()) != null) {
                if (object instanceof Member) {
                  // appended objects in file to string builder in table format
                  stringBuilder.append(
                    String.format(
                      format,
                      ((Member) object).getFirstName(),
                      ((Member) object).getLastName(),
                      ((Member) object).getAddress(),
                      ((Member) object).getPhoneNumber()
                    )
                  );
                }
              }
            } catch (EOFException e) {
              // do nothing
            } finally {
              // closing the object input stream
              objectInputStream.close();

              // appending the line at the bottom of the table
              stringBuilder.append("=".repeat(100) + "\n");

              sendDataToClient(
                stringBuilder.toString(),
                "Client Request: " + clientRequest
              );
            }
          } else {
            sendDataToClient("File does not exist", "File Not Found");
          }
        } else {
          sendDataToClient("Invalid Request", "Invalid Request");
        }
      } catch (SocketException e) {
        System.out.println(e.getMessage());
      } catch (IOException | ClassNotFoundException e) {
        System.out.println(e.getMessage());
      } finally {
        if (datagramSocket != null) datagramSocket.close();
      }
    }
  }

  private static void sendDataToClient(String data, String confirmationMsg) {
    try {
      byte[] sendData = data.getBytes();

      // format data to datagram packet and use address and port from initial request host
      DatagramPacket reply = new DatagramPacket(
        sendData,
        sendData.length,
        datagramPacket.getAddress(),
        datagramPacket.getPort()
      );

      datagramSocket.send(reply);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      System.out.println(confirmationMsg);
    }
  }
}
