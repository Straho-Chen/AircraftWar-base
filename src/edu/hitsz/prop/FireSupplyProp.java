package edu.hitsz.prop;

import edu.hitsz.prop.strategy.PropStrategy;
import edu.hitsz.aircraft.HeroAircraft;

public class FireSupplyProp extends AbstractProp {
    private PropStrategy propStrategy;
    public FireSupplyProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    public void setPropStrategy(PropStrategy propStrategy) {
        this.propStrategy = propStrategy;
    }

    public void executeStrategy(HeroAircraft heroAircraft) {
        propStrategy.firePorpSet(heroAircraft);
    }
}
