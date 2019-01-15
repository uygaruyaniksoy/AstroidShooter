package com.group6.backendserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Client {

    private static final int PORT = 5758;
    private static final String HOST = "localhost";

    public static void main(String[] args) throws IOException {
        new Client().connectServer();
    }

    private void connectServer() throws IOException {
        Socket socket = new Socket(HOST, PORT);

        System.out.println("connected to server");

        while(true);
    }


}
