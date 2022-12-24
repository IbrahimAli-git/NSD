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

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void run() {
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

    public void menu(){

    }

    public static void main(String[] args) throws IOException {
        // Handle malformed requests
        System.out.println("Please enter port");
        Scanner sc = new Scanner(System.in);
        int port = sc.nextInt();
        ServerSocket serverSocket = new ServerSocket(port);
        Server server;
        server = new Server(serverSocket);
        server.run();
    }
}