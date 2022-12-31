package com.ibrahim;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private String username;

    // Default constructor
    public Client() {
    }

    // For initialising client objects and streams
    public Client(Socket socket, String username) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
        } catch (IOException e) {
            close();
        }
    }

    // For sending messages in the chat channel
    public void sendMessages() {
        String message = "";
        Scanner sc = new Scanner(System.in);
        this.setUsername();

        while (socket.isConnected()) {
            System.out.println("Enter message or exit: ");
            message = sc.nextLine();
            if (message.equals("exit")) break;
            try {
                bufferedWriter.write(message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } catch (IOException e) {
                System.out.println("Please re-enter massage");
            }
        }
        System.out.println("Would you like to read the messages?(y/n)");
        message = sc.nextLine();
        if (message.equals("y")) FileHandler.readFromFile();
    }


    // For initialising ServerSocket with the correct port
    // Ensures the port number entered is free to use and not reserved
    public static int initPort() {
        System.out.println("Please enter port: ");
        int port = 0;
        while (true) {
            Scanner sc = new Scanner(System.in);
            port = sc.nextInt();
            if (port > 1023 && port < 65535) break;
            System.out.println("Please enter again:");
        }
        return port;
    }

    // This sets the client username using the BufferedWriter
    // This ensures it will be set in the ClientThreadHandler Class for each client
    public void setUsername() {
        try {
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            System.out.println("Please re-run the program");
        }
    }

    // For closing all the streams
    public void close() {
        try {
            if (socket != null) socket.close();
            if (bufferedWriter != null) bufferedWriter.close();
            if (bufferedReader != null) bufferedReader.close();
        } catch (IOException e) {
            System.out.println("Please run the program again");
        }
    }


    // Main method for running class program
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter username: ");
        String username = sc.nextLine();

        System.out.println("Please enter host: ");
        String hostName = sc.nextLine();

        try {
            Socket socket = new Socket(hostName, initPort());
            Client client = new Client(socket, username);
            client.sendMessages();
            client.close();
        } catch (IOException e) {
            System.out.println("Please restart client");
            System.exit(0);
        }
    }
}
