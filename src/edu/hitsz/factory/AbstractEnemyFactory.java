package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractAircraft;

public abstract class AbstractEnemyFactory {

    /**
     * 创建敌机
     * @return
     * 返回抽象类AbstractAircraft
     */
    public abstract AbstractAircraft creatEnemy();

}
