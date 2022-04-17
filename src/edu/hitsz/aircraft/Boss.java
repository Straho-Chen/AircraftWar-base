package edu.hitsz.aircraft;

import edu.hitsz.aircraft.strategy.FireStrategy;
import edu.hitsz.application.Main;

public class Boss extends AbstractAircraft{
    public Boss(int locationX, int locationY, int speedX, int speedY, int hp, FireStrategy fireStrategy, int direction) {
        super(locationX, locationY, speedX, speedY, hp, fireStrategy, direction);
    }

    @Override
    public void forward() {
        super.forward();
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }
}
