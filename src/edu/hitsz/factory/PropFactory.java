package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.prop.AbstractProp;

public abstract class PropFactory {

    public abstract AbstractProp creatProp(AbstractAircraft enemyAircraft);

}
