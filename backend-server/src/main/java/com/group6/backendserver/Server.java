package com.group6.backendserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

class Server {

    private static final int PORT = 8082;
    private static final AtomicInteger sessionId = new AtomicInteger(0);

    private final List<ClientConnectionHandler> clients = new ArrayList<ClientConnectionHandler>();

    public static void main(String[] args) throws IOException {
        new Server().runServer();
    }

    private void runServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("server up for connection");

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("connection received with session id " + sessionId.get());

            ClientConnectionHandler client = new ClientConnectionHandler(sessionId.getAndIncrement(), socket);
            ClientConnectionHandler lastClient = clients.size() > 0 ? clients.get(clients.size() - 1) : null;
            if (lastClient != null && !lastClient.hasRival()) {
                lastClient.setRival(client);
                client.setRival(lastClient);

                System.out.println("sessions with id: " + lastClient.getSessionID() + " and " + client.getSessionID() + " are paired.");

                client.startGameSession();
                client.getRival().startGameSession();
            }
            clients.add(client);

        }
    }

}
