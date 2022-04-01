package edu.hitsz.prop;

import edu.hitsz.application.Main;

public class FireSupplyProp extends AbstractProp {

    public FireSupplyProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    public void increaseFire() {
        System.out.print("FireSupply active!");
    }

}
