package com.company.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SocketTest {
    public static void main(String[] args) {
        try (Socket socket = new Socket("time-a.nist.gov", 13);
             Scanner in = new Scanner(socket.getInputStream(), "UTF-8")) {
            socket.setSoTimeout(10000);
            while(in.hasNextLine()){
                String s = in.nextLine();
                System.out.println(s);
            }
        } catch (SocketException e) {
            // todo logg socket out of time

        } catch (UnknownHostException e) {
            // todo log unknown host exception

        } catch (IOException e) {
            // todo log I/0 Exception

        }
    }
}
