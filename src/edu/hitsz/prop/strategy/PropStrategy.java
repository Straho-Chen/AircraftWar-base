package edu.hitsz.prop.strategy;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.prop.AbstractProp;

public interface PropStrategy {

    /**
     * 对不同的火力道具设置不同的效果
     * @param heroAircraft
     * @return AbstractProp
     */
    public void firePorpSet(HeroAircraft heroAircraft);
}
