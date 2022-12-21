package com.ibrahim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Client {
    private static String host = "localhost";
    private static int port = 1234;
    private String userName;
    private Socket socket;
    private PrintWriter pr;
    private BufferedReader br;

    public Client(Socket socket, String username) {
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
        try {
            while ((message = br.readLine()) != null) {
                pr.println(message);
                pr.flush();
                System.out.println("Client: " + br.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void menu(){
    }

    public static void main(String[] args) throws IOException {
        // handle exception
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Please enter username: ");
        String userName = br.readLine();

        Socket socket = new Socket(host, port);
        Client client = new Client(socket, userName);
        client.sendMessages();
    }
}
