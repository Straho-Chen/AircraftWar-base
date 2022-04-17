package edu.hitsz.factory;

import edu.hitsz.aircraft.strategy.ScatteringBallistic;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.Boss;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class BossFactory extends AbstractEnemyFactory {

    @Override
    public AbstractAircraft creatEnemy() {
        return new Boss(
                (int) ( Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth()))*1,
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2)*1,
                2,
                0,
                100,
                new ScatteringBallistic(),
                1
        );
    }
}
