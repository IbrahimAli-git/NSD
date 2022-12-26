package com.ibrahim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    private ServerSocket serverSocket;
    private static int numOfClients = 0;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

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

    public void close() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int initServerSocket() {
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

    public static void main(String[] args) throws IOException {
        // Handle malformed requests
        ServerSocket serverSocket = new ServerSocket(initServerSocket());
        Server server = new Server(serverSocket);
        server.run();
    }
}