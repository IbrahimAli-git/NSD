package com.ibrahim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private PrintWriter pr;
    private BufferedReader br;
    private String username;

    // Default constructor
    public Client() {
    }

    // For initialising client objects and streams
    public Client(Socket socket, String username) {
        try {
            this.socket = socket;
            this.pr = new PrintWriter(socket.getOutputStream());
            this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
        } catch (IOException e) {
            close();
        }
    }

    // For sending messages
    public void sendMessages() {
        String message = "";
        Scanner sc = new Scanner(System.in);

        while (socket.isConnected()) {
            System.out.println("Enter message or exit: ");
            message = sc.nextLine();

            if (message.equals("exit")) break;

            FileHandler.addToList(username + ": " + message);
            pr.println(message);
            pr.flush();
            System.out.println(username + ": " + message);
        }

        FileHandler.writeToFile();
        System.out.println("Would you like to read the messages?(y/n)");
        message = sc.nextLine();
        if (message.equals("y")) FileHandler.readFromFile();
        close();
    }

    // Creates a new thread which listens/receives messages sent on ClientThreadHandler
    public void receiveMessages() {
//        new Thread(new ReadThread(socket)).start();
        new Thread(() -> {
            try {
                String message = br.readLine();
                System.out.println("Message: " + message);
            } catch (IOException e) {
                close();
            }
        }).start();
    }

    // For closing all the streams
    public void close() {
        try {
            if (socket != null) socket.close();
            if (pr != null) pr.close();
            if (br != null) br.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // For initialising ServerSocket with the correct port
    // Ensures the port number entered is free to use and not reserved
    public static int initSocket() {
        System.out.println("Please enter port: ");
        Scanner sc = new Scanner(System.in);
        int port = 0;
        while (true) {
            port = sc.nextInt();
            if (port > 1023 && port < 65535) break;
            System.out.println("Please enter again:");
        }
        return port;
    }

    // Main method for running class program
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter username: ");
        String username = sc.nextLine();

        System.out.println("Please enter host: ");
        String hostName = sc.nextLine();

        Socket socket = new Socket(hostName, initSocket());
        Client client = new Client(socket, username);
        client.receiveMessages();
        client.sendMessages();
    }
}
