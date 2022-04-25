package edu.hitsz.prop.strategy;

import edu.hitsz.aircraft.strategy.DirectBallistic;
import edu.hitsz.aircraft.strategy.ScatteringBallistic;
import edu.hitsz.aircraft.HeroAircraft;

public class ChangeBallistic implements PropStrategy{

    @Override
    public void firePorpSet(HeroAircraft heroAircraft) {
        Runnable r = () -> {
            try {
                heroAircraft.setFireStrategy(new ScatteringBallistic());
                Thread.sleep(10000);
                heroAircraft.setFireStrategy(new DirectBallistic());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        new Thread(r).start();
    }
}
