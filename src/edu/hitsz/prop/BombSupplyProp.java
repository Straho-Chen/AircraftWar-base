package edu.hitsz.prop;

public class BombSupplyProp extends AbstractProp {

    public BombSupplyProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    public void Boom() {
        System.out.print("BoomSupply active!");
    }

}
