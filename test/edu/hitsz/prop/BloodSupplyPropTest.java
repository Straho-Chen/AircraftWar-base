package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.Main;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BloodSupplyPropTest {

    private BloodSupplyProp prop;
    private HeroAircraft heroAircraft;

    @BeforeEach
    void setUp() {
        System.out.println("--Test BloodSupplyProp--");
        prop = new BloodSupplyProp(10, 10, 0, 5);
        heroAircraft = HeroAircraft.getSingleton();
    }

    @AfterEach
    void tearDown() {
        System.out.println("--Test finish--");
        prop = null;
        heroAircraft = null;
    }

    @Test
    void forward() {
        System.out.println("--Test forward method--");

        prop.forward();
        assertFalse(prop.notValid());

        prop = new BloodSupplyProp(10, Main.WINDOW_HEIGHT, 0, 5);
        prop.forward();
        assertTrue(prop.notValid());
    }

    @Test
    void increaseHp() {
        System.out.println("--Test increaseHP method--");

        heroAircraft.decreaseHp(20);
        prop.increaseHp(heroAircraft);
        assertEquals(90, heroAircraft.getHp());
    }
}