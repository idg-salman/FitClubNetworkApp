/*
 **********************************************************************************************************
 * Author:      Md Afsar Uddin Salman
 * Student ID:  12190848
 * File Name:   TCPClient.java
 * Date:        26-Mar-2024
 * Purpose:     Implements a TCP server application for a fitness club's membership system. It listens
 *              for incoming client connections, receives member registration data, saves it to a text
 *              file, and periodically serializes the data to an object file for storage.
 **********************************************************************************************************
 */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.TimerTask;

public class TCPServer {

  private static final int SERVER_PORT = 1148;
  private static final int OBJECT_SAVE_INTERVAL = 2000;

  public static void main(String args[]) {
    ServerSocket listenServerSocket = null;
    int clientNumber = 0;

    try {
      // setting up server socket to listen for incoming connections
      listenServerSocket = new ServerSocket(SERVER_PORT);

      // displaying message to the server console that server is running
      System.out.println(
        "TCP Server is running at: " +
        listenServerSocket.getInetAddress() +
        ":" +
        listenServerSocket.getLocalPort()
      );

      // created a timer to save objects to file based on OBJECT_SAVE_INTERVAL value
      java.util.Timer timer = new java.util.Timer();
      timer.schedule(
        new WriteToFile(),
        OBJECT_SAVE_INTERVAL,
        OBJECT_SAVE_INTERVAL
      );

      do { // loop to listen for incoming connections
        Socket clientSocket = listenServerSocket.accept();
        new ClientConnection(clientSocket, ++clientNumber);
      } while (true);
    } catch (IOException e) {
      System.out.println(e.getMessage());
    } finally {
      try { // closing the server socket
        listenServerSocket.close();
      } catch (IOException e) {
        System.out.println(e.getMessage());
      }
    }
  }
}

//Using thread to allow multiple processes running at the same time.
class ClientConnection extends Thread {

  private static final String TEXT_FILENAME = "memberlist.txt";

  private DataInputStream dataInputStream;
  private DataOutputStream dataOutputStream;
  private Socket clientSocket;
  private int clientNumber;

  ClientConnection(Socket clientSocket, int clientNumber) {
    try {
      // assignning upcomming client socket to this class object
      this.clientSocket = clientSocket;
      this.clientNumber = clientNumber;

      // assign input and output streams to this class object
      this.dataInputStream = new DataInputStream(clientSocket.getInputStream());
      this.dataOutputStream =
        new DataOutputStream(clientSocket.getOutputStream());

      // starting a new thread for upcomming client
      this.start();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  public void run() {
    try {
      // reading data comming from the client
      String clientData = this.dataInputStream.readUTF();

      // displaying message to the server console that data is being received from client
      System.out.println(
        "Receiving data from client: " + Integer.toString(clientNumber)
      );

      // saving client sent data to a file
      FileWriter fileWriter = new FileWriter(TEXT_FILENAME, true);
      fileWriter.write(clientData + System.lineSeparator());
      fileWriter.close();

      // sending acknowledgment to the client that data is saved successfully
      this.dataOutputStream.writeUTF("Data Saved Successfully!");
    } catch (EOFException e) {
      System.out.println("Connection Closed by Client!");
    } catch (IOException e) {
      System.out.println(e.getMessage());
    } finally {
      try {
        this.clientSocket.close();
      } catch (IOException e) {
        System.out.println(e.getMessage());
      }
    }
  }
}

class WriteToFile extends TimerTask {

  private static final String TEXT_FILENAME = "memberlist.txt";
  private static final String SERIALIZED_FILENAME = "memberlistObject";
  private static final String DELIMITER = ":";

  private BufferedReader bufferReader;

  public void run() {
    File memberListTextFile = new File(TEXT_FILENAME);

    if (memberListTextFile.exists()) {
      // this file will be used to save serialized objects
      File serializeFile = new File(SERIALIZED_FILENAME);

      try {
        // reading data from the text file via buffer reader
        FileReader fileReader = new FileReader(memberListTextFile.getName());
        bufferReader = new BufferedReader(fileReader);

        // will be used to store member objects created based on the text file data
        ArrayList<Member> membersArrayList = new ArrayList<Member>();

        // will be used to read each line from the text file
        String currentLine;

        // iterating through the text file until the end of file is reached
        // also creating an object for each line and adding it to the array list
        while ((currentLine = bufferReader.readLine()) != null) {
          // creating a string tokenizer to split the current line by delimiter
          StringTokenizer tokenizer = new StringTokenizer(
            currentLine,
            DELIMITER
          );

          // creating a member object to store the current line data
          Member member = new Member();

          // using string tokenizer to split the current line by delimiter
          // and assign each token to the member object
          while (tokenizer.hasMoreTokens()) {
            member.setFirstName(tokenizer.nextToken());
            member.setLastName(tokenizer.nextToken());
            member.setAddress(tokenizer.nextToken());
            member.setPhoneNumber(tokenizer.nextToken());
          }

          // adding the member object to the array list
          membersArrayList.add(member);
        }

        // creating an object output stream to write to the serialized file
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
          new FileOutputStream(serializeFile, false)
        );

        // looping through the array list and writing each object to the serialized file
        for (Member member : membersArrayList) {
          objectOutputStream.writeObject(member);
        }

        // lastly, closing the object output stream
        objectOutputStream.close();
      } catch (IOException e) {
        System.out.println(e.getMessage());
      } finally {
        try {
          // closing the buffer reader after reading the text file
          if (bufferReader != null) bufferReader.close();
        } catch (IOException e) {
          System.out.println(e.getMessage());
        }
      }
    }
  }
}
