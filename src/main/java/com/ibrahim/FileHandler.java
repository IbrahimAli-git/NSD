package com.ibrahim;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileHandler {
    private String fileName = "filename.txt";
    private ArrayList<String> list = new ArrayList<>();

    public FileHandler() {
    }

    public void readFromFile() {
        System.out.println("Reading from file...");
        for (String s : list) {
            System.out.println(s);
        }
        System.out.println();
    }

    public void writeToFile(String message) {
        synchronized (fileName) {
            try (FileWriter fileWriter = new FileWriter(fileName); BufferedWriter writer = new BufferedWriter(fileWriter);) {
                writer.write(message);
                writer.flush();
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
