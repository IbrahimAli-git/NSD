package com.ibrahim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private static String host = "localhost";
    private static int port = 1234;

    public static void run(){
        try (Socket socket = new Socket(host, port);
            PrintWriter pr = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        )
        {
        String message = "";
        while ((message = console.readLine()) != null){
            pr.println(message);
            System.out.println("Client: " + input.readLine());
        }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        run();
    }
}
