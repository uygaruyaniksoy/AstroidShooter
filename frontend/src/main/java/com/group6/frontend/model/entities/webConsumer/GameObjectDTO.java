package com.group6.frontend.model.entities.webConsumer;

import javafx.util.Pair;

import java.io.Serializable;

public class GameObjectDTO implements Serializable {
    public int id;
    public int curHealth;
    public Pair<Double, Double> position;
}
