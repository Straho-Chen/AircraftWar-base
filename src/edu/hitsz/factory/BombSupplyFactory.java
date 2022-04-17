package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BombSupplyProp;

public class BombSupplyFactory extends AbstractPropFactory {

    @Override
    public AbstractProp creatProp(AbstractAircraft enemyAircraft) {
        return new BombSupplyProp(
                enemyAircraft.getLocationX(),
                enemyAircraft.getLocationY(),
                speedX,
                speedY
        );
    }
}
