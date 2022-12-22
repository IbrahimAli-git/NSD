package com.ibrahim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    /*private String host = "localhost";
    private int port = 1234;*/
    private Socket socket;
    private PrintWriter pr;
    private BufferedReader br;

    public Client(Socket socket) {
        try {
            this.socket = socket;
            this.pr = new PrintWriter(socket.getOutputStream());
            this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessages() {
        String message = "";
        while (socket.isConnected()) {
            System.out.println("Enter message: ");
            Scanner sc = new Scanner(System.in);
            message = sc.nextLine();

            pr.println(message);
            pr.flush();
            System.out.println("Client: " + message);
        }
    }

    public void receiveMessages(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String message = br.readLine();
                    System.out.println("Message Received: " + message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 1234);
        Client client = new Client(socket);
        client.receiveMessages();
        client.sendMessages();
    }
}
