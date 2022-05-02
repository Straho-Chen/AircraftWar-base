package edu.hitsz.aircraft.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class DirectBallistic implements FireStrategy {
    private int shootNum = 1;
    private int power = 100;
    @Override
    public List<AbstractBullet> fireSet(AbstractAircraft abstractAircraft, int direction) {
        List<AbstractBullet> bullets = new LinkedList<>();
        int x = abstractAircraft.getLocationX();
        int y = abstractAircraft.getLocationY() + direction*2;
        int speedY = abstractAircraft.getSpeedY() + direction*5;
        int speedX = 0;
        AbstractBullet abstractBullet;
        if (HeroAircraft.class.isAssignableFrom(abstractAircraft.getClass())) {
            for (int i = 0; i < shootNum; i++) {
                abstractBullet = new HeroBullet(x + (i * 2 - shootNum + 1)*5, y, speedX, speedY, power);
                bullets.add(abstractBullet);
            }
        }
        else {
            for (int i = 0; i < shootNum; i++) {
                abstractBullet = new EnemyBullet(x + (i * 2 - shootNum + 1)*5, y, speedX, speedY, power);
                bullets.add(abstractBullet);
            }
        }
        return bullets;
    }
}
