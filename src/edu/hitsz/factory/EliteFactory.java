package edu.hitsz.factory;

import edu.hitsz.aircraft.strategy.DirectBallistic;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

import java.util.Random;

public class EliteFactory extends AbstractEnemyFactory {

    private int randomDirection() {
        Random r = new Random();
        double t = r.nextDouble();
        if (t > 0.5) {
            return -1;
        }
        else {
            return 1;
        }
    }

    @Override
    public AbstractAircraft creatEnemy(int hp) {
        return new EliteEnemy(
                (int) ( Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth()))*1,
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2)*1,
                2 * this.randomDirection(),
                2,
                hp,
                new DirectBallistic(),
                1
        );
    }
}
