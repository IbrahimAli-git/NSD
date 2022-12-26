package com.ibrahim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientThreadHandler implements Runnable {
    // For storing ClientThreadHandler objects
    // It is static so only one instance of it is created
    private static ArrayList<ClientThreadHandler> clientThreadHandlers = new ArrayList<>();
    private Socket socket;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    private String username;

    // For initialising ClientThreadHandler objects and streams
    public ClientThreadHandler(Socket socket) {
        try {
            this.socket = socket;
            this.printWriter = new PrintWriter(socket.getOutputStream());
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            clientThreadHandlers.add(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // For running each client handler
    @Override
    public void run() {
        String message;
        try {
            while (socket.isConnected()) {
                message = bufferedReader.readLine();
                printMessage(message);
            }
        } catch (IOException e) {
            close();
        }
    }

    // For closing all the streams
    public void close() {
        try {
            clientThreadHandlers.remove(this);
            if(socket != null)  socket.close();
            if(printWriter != null) printWriter.close();
            if (bufferedReader != null) bufferedReader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // Prints the messages to all other clients
    public void printMessage(String message) {
        for (ClientThreadHandler clientThreadHandler : clientThreadHandlers) {
            if (clientThreadHandler != this) {
                clientThreadHandler.printWriter.println(message);
                clientThreadHandler.printWriter.flush();
            }
        }
    }
}
