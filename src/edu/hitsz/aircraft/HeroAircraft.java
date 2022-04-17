package edu.hitsz.aircraft;

import edu.hitsz.aircraft.strategy.DirectBallistic;
import edu.hitsz.aircraft.strategy.FireStrategy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    /**
     * 创建单例模式
     */
    private volatile static HeroAircraft heroAircraft;
    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp    初始生命值
     */
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp, FireStrategy fireStrategy, int direction) {
        super(locationX, locationY, speedX, speedY, hp, fireStrategy, direction);
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

    public void increaseHp(int increase) {
        hp += increase;
        if (hp >= maxHp) {
            hp = maxHp;
        }
    }

    public static HeroAircraft getSingleton() {
        if (heroAircraft == null) {
            synchronized (HeroAircraft.class) {
                if (heroAircraft == null) {
                    heroAircraft = new HeroAircraft(
                            Main.WINDOW_WIDTH / 2,
                            Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight() ,
                            0, 0, 100, new DirectBallistic(), -1);
                }
            }
        }
        return heroAircraft;
    }
}
