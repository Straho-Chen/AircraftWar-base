package edu.hitsz.factory;

import edu.hitsz.aircraft.strategy.DirectBallistic;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class MobFactory extends AbstractEnemyFactory {

    @Override
    public AbstractAircraft creatEnemy(int hp) {
        return new MobEnemy(
                (int) ( Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth()))*1,
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2)*1,
                0,
                2,
                hp,
                new DirectBallistic(),
                1
        );
    }
}
