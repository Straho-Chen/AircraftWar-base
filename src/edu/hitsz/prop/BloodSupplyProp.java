package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;

public class BloodSupplyProp extends AbstractProp {

    private int hp = 10;

    public BloodSupplyProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    public void increaseHp(HeroAircraft heroAircraft) {
        heroAircraft.increaseHp(hp);
    }

}
