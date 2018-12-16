package com.group6.frontend.model.entities;

interface IGameObject extends Drawable {

    void move(double x, double y);
    void update(double delta);
    void intersect(GameObject gameObject);

}
