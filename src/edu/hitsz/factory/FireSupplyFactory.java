package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.FireSupplyProp;

public class FireSupplyFactory extends AbstractPropFactory {

    @Override
    public AbstractProp creatProp(AbstractAircraft enemyAircraft) {
        return new FireSupplyProp(
                enemyAircraft.getLocationX(),
                enemyAircraft.getLocationY(),
                speedX,
                speedY
        );
    }
}
