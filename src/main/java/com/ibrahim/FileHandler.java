package com.ibrahim;

import java.io.*;
import java.util.ArrayList;

public class FileHandler {
    // File for storing chat messages
    private static String fileName = "C:\\Users\\Ibrahim\\Intellij Projects\\NSD\\src\\main\\resources\\filename.txt";

    // ArrayList for storing all the chat messages
    // It is static so only one is created and used for storing messages
    private static ArrayList<String> list = new ArrayList<>();

    // Constructor for initialising FileHandler objects
    public FileHandler() {
    }

    // Prints all the chat messages to the screen
    public static void readFromFile() {
        synchronized (fileName) {
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String s;
                while ((s = br.readLine()) != null) {
                    System.out.println(s);
                }
                System.out.println();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // For adding chat messages to the arraylist
    public static void addToList(String message) {
        synchronized (list) {
            list.add(message);
        }
    }

    // For writing chat messages to the file
    // Try with resources is used to simultaneously read from a stream and close it
    public static void writeToFile() {
        synchronized (fileName) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                for (String message : list) {
                    writer.write(message);
                    writer.flush();
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
