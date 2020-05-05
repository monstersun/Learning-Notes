package com.company.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ServerSocketTest {
    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(8188)) {
            try (Socket income = server.accept()) {
                PrintWriter out = new PrintWriter(new OutputStreamWriter(income.getOutputStream(),
                        StandardCharsets.UTF_8), true);
                out.println("hello, please some message enter bye to quit");
                Scanner in = new Scanner(income.getInputStream());
                boolean done = false;
                while (!done && in.hasNextLine()) {
                    String line = in.nextLine();
                    out.println("Echo: " + line);
                    if ("bye".equalsIgnoreCase(line)) {
                        done = true;
                    }
                }
            }
        }
    }
}
