/*
 **********************************************************************************************************
 * Author:      Md Afsar Uddin Salman
 * Student ID:  12190848
 * File Name:   TCPClient.java
 * Date:        26-Mar-2024
 * Purpose:     Implementing TCP client-side functionality for a fitness club's membership registration
 *              system, facilitating user input validation and data transmission to the server.
 **********************************************************************************************************
 */

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPClient {

  private static final String SERVER_IP = "0.0.0.0";
  private static final int SERVER_PORT = 1148;

  @SuppressWarnings("resource")
  public static void main(String args[]) {
    Socket serverSocket = null;
    Scanner inputScanner = new Scanner(System.in);

    while (true) {
      try {
        // setting up the socket to connect to the server
        serverSocket = new Socket(SERVER_IP, SERVER_PORT);

        // defined data input and output streams to send and receive data from server
        DataInputStream dataInputStream = new DataInputStream(
          serverSocket.getInputStream()
        );
        DataOutputStream dataOutputStream = new DataOutputStream(
          serverSocket.getOutputStream()
        );

        // to store the member data
        Member member = new Member();

        // to get the member number from user and validate it to be numbers only
        System.out.print("Enter Member Number: ");
        String memberNumber = inputScanner.nextLine().trim();
        boolean isMemberNumberValid = false;

        do {
          isMemberNumberValid = memberNumber.matches("[0-9]+");

          if (!isMemberNumberValid) {
            System.out.println("Member Number Must Be Numbers Only");
            System.out.print("Enter Member Number: ");
            memberNumber = inputScanner.nextLine().trim();
          }
        } while (!isMemberNumberValid);

        // to get the member name from user
        // also to set the member name to the member object by validating it
        System.out.print(": ");
        String firstName = inputScanner.nextLine();
        boolean isFirstNameValid = false;

        do {
          try {
            member.setFirstName(firstName);
            isFirstNameValid = true;
          } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.print("Enter First Name: ");
            firstName = inputScanner.nextLine();
            continue;
          }
        } while (!isFirstNameValid);

        // to get the member last name from user
        // also to set the member last name to the member object by validating it
        System.out.print("Enter Last Name: ");
        String lastName = inputScanner.nextLine();
        boolean isLastNameValid = false;

        do {
          try {
            member.setLastName(lastName);
            isLastNameValid = true;
          } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.print("Enter Last Name: ");
            lastName = inputScanner.nextLine();
            continue;
          }
        } while (!isLastNameValid);

        // to get the member address from user
        // also to set the member address to the member object by validating it
        System.out.print("Enter Address: ");
        String address = inputScanner.nextLine();
        boolean isAddressValid = false;

        do {
          try {
            member.setAddress(address);
            isAddressValid = true;
          } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.print("Enter Address: ");
            address = inputScanner.nextLine();
            continue;
          }
        } while (!isAddressValid);

        // to get the member phone number from user
        // also to set the member phone number to the member object by validating it
        System.out.print("Enter Phone Number: ");
        String phoneNumber = inputScanner.nextLine();
        boolean isPhoneNumberValid = false;

        do {
          try {
            member.setPhoneNumber(phoneNumber);
            isPhoneNumberValid = true;
          } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.print("Enter Phone Number: ");
            phoneNumber = inputScanner.nextLine();
            continue;
          }
        } while (!isPhoneNumberValid);

        // prepare the data to be sent to the server
        String dataPayload = String.format(
          "%s:%s:%s:%s",
          firstName,
          lastName,
          address,
          phoneNumber
        );

        System.out.println("-".repeat(44));
        System.out.println("Sending Data to Server...");

        // send the data to the server
        dataOutputStream.writeUTF(dataPayload);

        // receiving and printing the server response
        String result = dataInputStream.readUTF();
        System.out.println("Server Response: " + result);
        System.out.println("=".repeat(44));
      } catch (UnknownHostException e) {
        System.out.println(e.getMessage());
      } catch (EOFException e) {
        System.out.println(e.getMessage());
      } catch (IOException e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
      } finally {
        try {
          // to close the socket if it is open
          if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {
          System.out.println(e.getMessage());
          break;
        }
      }
    }
  }
}
