package com.ibrahim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static final int port = 1234;
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void run() {
        try {
            while(!serverSocket.isClosed()){
                Socket clientSocket = serverSocket.accept();
                ClientThreadHandler clientThreadHandler = new ClientThreadHandler(clientSocket);
                Thread thread = new Thread(clientThreadHandler);
                thread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        // Handle malformed requests
        ServerSocket serverSocket = new ServerSocket(port);
        Server server = new Server(serverSocket);
        server.run();
    }
}