package com.group6.backendserver;

import javafx.util.Pair;

import java.io.*;
import java.net.Socket;

public class ClientConnectionHandler implements Runnable {
    private final int sessionID;
    private final Socket socket;

    private ClientConnectionHandler rival;

    public ClientConnectionHandler(int sessionID, Socket socket) {
        this.sessionID = sessionID;
        this.socket = socket;
    }

    public int getSessionID() {
        return sessionID;
    }

    public boolean hasRival() {
        return rival != null;
    }

    public void setRival(ClientConnectionHandler client) {
        this.rival = client;
    }

    public ClientConnectionHandler getRival() {
        return rival;
    }

    public void startGameSession() throws IOException {
        // send message to socket so the client knows that the game is started.

        OutputStream outputStream = socket.getOutputStream();
        String message = "start";
        outputStream.write(message.getBytes());

        new Thread(this).start();
    }

    /**
     * Listens for player input
     */
    public void run() {
        System.out.println("" + sessionID + " is running.");
        System.out.println(socket.toString());
        ObjectInputStream in;
        ObjectOutputStream out;

        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(rival.socket.getOutputStream());

            while (!socket.isClosed()) {
                Pair<Double, Double> dto = (Pair<Double, Double>) in.readObject();
                out.writeObject(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
