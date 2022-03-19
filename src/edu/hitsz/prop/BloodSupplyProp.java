package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.Main;

public class BloodSupplyProp extends AbstractProp {

    private int hp = 10;

    public BloodSupplyProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    public void IncreaseHP(HeroAircraft heroAircraft) {
        heroAircraft.increaseHP(hp);
    }

}
