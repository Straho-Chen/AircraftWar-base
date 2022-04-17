package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.prop.AbstractProp;

public abstract class AbstractPropFactory {

    /**
     * 所有道具统一速度
     */
    protected int speedX = 0;
    protected int speedY = 5;

    /**
     * 创建道具
     * @param enemyAircraft
     * @return
     * 返回抽象类AbstractProp
     */
    public abstract AbstractProp creatProp(AbstractAircraft enemyAircraft);

}
