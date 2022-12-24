package com.ibrahim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private PrintWriter pr;
    private BufferedReader br;

    public Client() {
    }

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
        ReadThread readThread = new ReadThread(socket);
        Thread thread = new Thread(readThread);
        thread.start();
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
    public void menu()  {
        System.out.println("Please enter host and port: ");
        Scanner sc = new Scanner(System.in);
        String hostName = sc.nextLine();
        int port = sc.nextInt();

        try {
            Socket socket = new Socket(hostName, port);
            Client client = new Client(socket);
            client.receiveMessages();
            client.sendMessages();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)  {
        Client client = new Client();
        client.menu();
    }
}
