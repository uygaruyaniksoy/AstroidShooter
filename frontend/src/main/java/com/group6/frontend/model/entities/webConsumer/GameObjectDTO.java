package com.group6.backendserver;

import javafx.util.Pair;

import java.io.Serializable;

public class GameObjectDTO implements Serializable {
    public int id;
    public int curHealth;
    public Pair<Double, Double> position;
}
