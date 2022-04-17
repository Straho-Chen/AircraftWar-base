package edu.hitsz.prop.strategy;

import edu.hitsz.aircraft.strategy.ScatteringBallistic;
import edu.hitsz.aircraft.HeroAircraft;

public class ChangeBallistic implements PropStrategy{

    @Override
    public void firePorpSet(HeroAircraft heroAircraft) {
        heroAircraft.setFireStrategy(new ScatteringBallistic());
    }
}
