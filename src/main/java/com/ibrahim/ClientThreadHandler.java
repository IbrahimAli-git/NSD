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

    }

    public void close(){
        clientThreadHandlers.remove(this);
        try {
            printWriter.close();
            bufferedReader.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printMessage(String message, ClientThreadHandler handler){
        for (ClientThreadHandler clientThreadHandler : clientThreadHandlers) {
            if (clientThreadHandler != handler){
                printWriter.println(message);
                printWriter.flush();
            }
        }
    }
}
