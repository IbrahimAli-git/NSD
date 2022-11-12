package com.ibrahim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

// Push code to GitHub
// Exception handling
// Create a gui

public class Server {
    private static final int port = 1234;

    public static void run() {
        String message = "";

        try (ServerSocket serverSocket = new ServerSocket(port);
             Socket socket = serverSocket.accept();
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter pr = new PrintWriter(socket.getOutputStream());
        ) {

            while ((message = in.readLine()) != null){
                pr.println(message);
                System.out.println("Server: " + in.readLine());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        run();
    }
}
