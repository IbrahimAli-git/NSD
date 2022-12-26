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
    private String username;

    public Client() {
    }

    public Client(Socket socket, String username) {
        try {
            this.socket = socket;
            this.pr = new PrintWriter(socket.getOutputStream());
            this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
        } catch (IOException e) {
            close();
        }
    }

    public void sendMessages() {
        String message = "";
        Scanner sc = new Scanner(System.in);

        while (socket.isConnected()) {
            System.out.println("Enter message or exit: ");
            message = sc.nextLine();

            if (message.equals("exit")) break;

            FileHandler.writeToFile(message);
            pr.println(message);
            pr.flush();
            System.out.println(username + ": " + message);
        }

        System.out.println("Would you like to read the messages?(y/n)");
        message = sc.nextLine();
        if (message.equals("y")) FileHandler.readFromFile();
    }

    public void receiveMessages() {
//        new Thread(new ReadThread(socket)).start();
        new Thread(() -> {
            try {
                String message = br.readLine();
                System.out.println("Message: " + message);
            } catch (IOException e) {
                close();
            }
        }).start();
    }

    public void close() {
        try {
            if (socket != null) socket.close();
            if (pr != null) pr.close();
            if (br != null) br.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter username: ");
        String username = sc.nextLine();

        System.out.println("Please enter host and port: ");
        String hostName = sc.nextLine();
        int port = sc.nextInt();


        Socket socket = new Socket(hostName, port);
        Client client = new Client(socket, username);
        client.receiveMessages();
        client.sendMessages();
    }

}
