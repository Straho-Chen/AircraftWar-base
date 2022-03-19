package edu.hitsz.aircraft;

import edu.hitsz.application.Game;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.bullet.EnemyBullet;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 精英敌机
 * 可射击
 */
public class EliteEnemy extends AbstractAircraft {

    /**攻击方式*/

    /**
     * 子弹一次发射数量
     */
    private int shootNum = 1;

    /**
     * 子弹伤害
     */
    private int power = 30;

    /**
     * 子弹射击方向（向上发射：1，向下发射：-1）
     */
    private int direction = 1;

    /**
     * @param locationX 精英机位置X坐标
     * @param locationY 精英机位置Y坐标
     * @param speedX 精英机射出的子弹的基准速度
     * @param speedY 精英机射出的子弹的基准速度
     * @param hp 精英机生命值
     */
    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    @Override
    /**
     * 通过设计产生子弹
     * @return 射击出的子弹List
     */
    public List<AbstractBullet> shoot() {
        List<AbstractBullet> up = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY() + direction*2;
        int speedX = 0;
        int speedY = this.getSpeedY() + direction*5;
        AbstractBullet abstractBullet;
        for (int i = 0; i < shootNum; i++) {
            //子弹发射位置相对飞机向前偏移
            //多个子弹横向分散
            abstractBullet = new EnemyBullet(x + (i*2 - shootNum + 1)*10, y, speedX, speedY, power);
            up.add(abstractBullet);
        }
        return up;
    }

}
