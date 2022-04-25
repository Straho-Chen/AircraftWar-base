package edu.hitsz.prop.strategy;

import edu.hitsz.aircraft.strategy.DirectBallistic;
import edu.hitsz.aircraft.strategy.ScatteringBallistic;
import edu.hitsz.aircraft.HeroAircraft;

public class ChangeBallistic implements PropStrategy{

    @Override
    public void firePorpSet(HeroAircraft heroAircraft) {
        Runnable r = () -> {
            int i = 10;
            while (i != 0) {
                heroAircraft.setFireStrategy(new ScatteringBallistic());
                try {
                    Thread.sleep(1000);
                    i--;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            heroAircraft.setFireStrategy(new DirectBallistic());
        };
        new Thread(r).start();
    }
}
