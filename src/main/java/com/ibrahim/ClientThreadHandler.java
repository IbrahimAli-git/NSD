package com.ibrahim;

import java.io.*;
import java.net.Socket;

public class ClientThreadHandler implements Runnable {
    private static int numOfClients = 0;
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private String username;

    // Default Constructor
    public ClientThreadHandler() {
    }

    // For initialising ClientThreadHandler objects and streams
    public ClientThreadHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = bufferedReader.readLine();
            System.out.println(username + " entered the channel");
            System.out.println("Client number: " + ++numOfClients);
        } catch (IOException e) {
            close();
        }
    }

    // For running each client handler
    // It prints the messages of each client to the shared server window
    // It also saves each message to a shared file
    @Override
    public void run() {
        String message;
        try {
            while ((message = bufferedReader.readLine()) != null) {
                System.out.println(username + ": " + message);
                FileHandler.addToList(username + ": " + message);
            }
            System.out.println(username + " exited the channel");
            System.out.println("Client number: " + --numOfClients);
            FileHandler.writeToFile();
        } catch (IOException e) {
            close();
        }
    }

    // For closing all the streams
    public void close() {
        try {
            if (socket != null) socket.close();
            if (bufferedWriter != null) bufferedWriter.close();
            if (bufferedReader != null) bufferedReader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
