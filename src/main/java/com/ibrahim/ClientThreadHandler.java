package com.ibrahim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientThreadHandler implements Runnable {
    private static ArrayList<ClientThreadHandler> clientThreadHandlers = new ArrayList<>();
    private Socket socket;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    private String username;

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

    public void close() {
        try {
            if(socket != null)  socket.close();
            if(printWriter != null) printWriter.close();
            if (bufferedReader != null) bufferedReader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void printMessage(String message) {
        for (ClientThreadHandler clientThreadHandler : clientThreadHandlers) {
            if (clientThreadHandler == this) continue;
            clientThreadHandler.printWriter.println(message);
            clientThreadHandler.printWriter.flush();
        }
    }
}
