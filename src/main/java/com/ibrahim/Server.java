package com.ibrahim;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private ServerSocket serverSocket;

    // Default Constructor
    public Server() {
    }

    // Constructor initialises the ServerSocket
    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    // For running the server and accepting connections
    public void run() {
        System.out.println("Running server...");
        try {
            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                ClientThreadHandler clientThreadHandler = new ClientThreadHandler(clientSocket);
                Thread thread = new Thread(clientThreadHandler);
                thread.start();
            }
        } catch (IOException e) {
            close();
        }
    }

    // For closing ServerSocket stream
    public void close() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.out.println("Please run the program again");
        }
    }

    // For initialising ServerSocket with the correct port
    // Ensures the port number entered is free to use and not reserved
    public static int initServerSocket() {
        System.out.println("Please enter port: ");
        Scanner sc = new Scanner(System.in);
        int port = 0;
        while (true) {
            port = sc.nextInt();
            if ((port > 1023 && port < 65535)) break;

            System.out.println("Please enter again:");
        }
        return port;
    }

    // Main method for initialising Server program
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(initServerSocket());
            Server server = new Server(serverSocket);
            server.run();
            server.close();
        } catch (IOException e) {
            System.out.println("Please run server again");
        }
    }
}