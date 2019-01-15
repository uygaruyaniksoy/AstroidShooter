package com.group6.backendserver;

public class GameSession {
    private final ClientConnectionHandler client1;
    private final ClientConnectionHandler client2;

    private GameState gameState;

    public GameSession(ClientConnectionHandler client1, ClientConnectionHandler client2) {

        this.client1 = client1;
        this.client2 = client2;
    }

    public void sendGameStateToClients() {

    }
}
