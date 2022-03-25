package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BloodSupplyProp;

public class BloodSupplyFactory extends PropFactory{

    @Override
    public AbstractProp creatProp(AbstractAircraft enemyAircraft) {
        return new BloodSupplyProp(
                enemyAircraft.getLocationX(),
                enemyAircraft.getLocationY(),
                0,
                enemyAircraft.getSpeedY()
        );
    }
}
