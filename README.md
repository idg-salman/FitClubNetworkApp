# FitClubApp

## Details

- **Course Code:** COIT132229
- **Course Title:** Applied Distributed Systems
- **Assessment:**  Java Client/Server Application


## Objectives

This assessment item is designed to test your understanding in Java TCP/UDP networking, Java Object Serialization\Deserialization, file reading and writing using client/server application development.

## The Case

A fitness club would like to create an application that allows members to register for club membership online. The application should be implemented using client/server model, where client can register for membership by providing their details (such as name, address, phone number, etc.) using a program for client. The details should then be sent to a server program, where the details are saved into files. The server program should allow for multiple clients to communicate and save details concurrently. The application should also have another client program for club manager to access and view the details of all existing members in the data files.

## Files Description

- `Member.java` - Source for the Member class
- `TCPClient.java` - Source for the TCPClient class
- `TCPServer.java` - Source for the TCPServer class
- `UDPClient.java` - Source for the UDPClient class
- `UDPServer.java` - Source for the UDPServer class
- `memberlist.txt` - The text file containing member details after your test.
- `memberlistObject` - The Java Object file containing member details after your test.

## Usage Guide

1. **Registration Process:**
   - Open the terminal or command prompt.
   - Navigate to the directory containing the FitClubNetworkApp files.
   - Compile the Java files using the command `javac *.java`.
   - Run the TCP server by executing `java TCPServer`.
   - Run the TCP client by executing `java TCPClient`.
   - Follow the prompts to enter your membership details for registration.

2. **Manager Access:**
   - Open another terminal or command prompt.
   - Navigate to the directory containing the FitClubNetworkApp files.
   - Run the UDP server by executing `java UDPServer`.
   - Run the UDP client for manager access by executing `java UDPClient`.
   - Send a request to retrieve member details.

3. **Checking Saved Files:**
   - After registration and manager access, you can check the saved files:
     - `memberlist.txt` contains registered member details.
     - `memberlistObject` contains serialized member details.