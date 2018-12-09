package com.group6.frontend.model.entities;

import com.group6.frontend.model.enums.AttackType;

public interface Spaceship {

    GameObject attack(double x, double y, AttackType attackType);
}
