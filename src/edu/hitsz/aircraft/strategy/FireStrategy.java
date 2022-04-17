package edu.hitsz.aircraft.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.AbstractBullet;

import java.util.List;

public interface FireStrategy {
    /**
     * 为不同机型设置不同火力
     * @param abstractAircraft
     * @return
     */
    public List<AbstractBullet> fireSet(AbstractAircraft abstractAircraft, int direction);
}
