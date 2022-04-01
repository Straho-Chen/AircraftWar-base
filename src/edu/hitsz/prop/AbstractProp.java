package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.basic.AbstractFlyingObject;

import java.util.List;

/**
 * 所有道具的抽象父类：
 * 回血、火力、炸弹（increaseHp, increaseFire, boom）
 */
public abstract class AbstractProp extends AbstractFlyingObject {

    public AbstractProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void forward() {
        super.forward();
        //判定 y 轴出界
        if (speedY > 0 && locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }
}