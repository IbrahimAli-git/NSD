package com.ibrahim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ReadThread implements Runnable {
    private Socket socket;
    private PrintWriter pr;
    private BufferedReader br;

    public ReadThread(Socket socket) {
        try {
            this.socket = socket;
            this.pr = new PrintWriter(socket.getOutputStream());
            this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String message = br.readLine();
            System.out.println("Message Received: " + message);
        } catch (IOException e) {
            close();
        }
    }

    public void close(){
        try {
            if(socket != null)  socket.close();
            if(pr != null) pr.close();
            if (br != null) br.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
