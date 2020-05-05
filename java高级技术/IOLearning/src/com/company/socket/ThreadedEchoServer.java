package com.company.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ThreadedEchoServer {
    public static void main(String[] args){
        try (ServerSocket server = new ServerSocket(8081)) {
            int i = 1;
            while (true) {
                Socket income = server.accept();
                System.out.println("Spawning" + i);
                new Thread(new ThreadHandler(income)).start();
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ThreadHandler implements Runnable {

    private Socket income;

    public ThreadHandler() {
    }

    public ThreadHandler(Socket income) {
        this.income = income;
    }


    @Override
    public void run() {
        if (income == null) {
            return;
        }
        try (InputStream inputStream = income.getInputStream();
             OutputStream outputStream = income.getOutputStream()) {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), true);
            Scanner in = new Scanner(inputStream, "UTF-8");
            boolean done = false;
            while (!done && in.hasNextLine()) {
                String line = in.nextLine();
                System.out.println(line);
                out.println("echo:" + line);
                if ("bye".equalsIgnoreCase(line)) {
                    done = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
