package com.company.socket;



import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class InetAddressTest {
    public static void main(String[] args) throws UnknownHostException, UnsupportedEncodingException {
        InetAddress address = InetAddress.getByName("time-a.nist.gov");
        InetAddress localHost = InetAddress.getLocalHost();
        System.out.println(address.getHostAddress());
        byte[] bytes = address.getAddress();
        System.out.println(bytes.length);
        System.out.println(address);
        System.out.println(localHost);
    }
}
