package frontend.model.entities;

import frontend.model.entities.ammos.Ammunition;
import frontend.model.enums.AttackType;

public interface Spaceship {

    public GameObject attack(double x, double y, AttackType attackType);
}
