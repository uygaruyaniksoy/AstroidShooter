package com.group6.frontend.model.entities.webConsumer;

import java.io.Serializable;
import java.util.List;

public class GameState implements Serializable {
    public GameObjectDTO client1;
    public GameObjectDTO client2;

    public List<GameObjectDTO> gameObjects;
}
