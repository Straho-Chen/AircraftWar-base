package edu.hitsz.aircraft;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class HeroAircraftTest {

    private HeroAircraft heroAircraft;

    @BeforeEach
    void setUp() {
        System.out.println("--Test HeroAircraft--");
        heroAircraft = HeroAircraft.getSingleton();
    }

    @AfterEach
    void tearDown() {
        System.out.println("--Test finish--");
        heroAircraft = null;
    }

    @Test
    void decreaseHp() {
        System.out.println("--Test decreaseHP method--");
        int decrease = 10;
        int hp = heroAircraft.getHp();
        heroAircraft.decreaseHp(decrease);
        assertEquals(hp - decrease, heroAircraft.getHp());
        hp = heroAircraft.getHp();
        decrease = hp + 1;
        heroAircraft.decreaseHp(decrease);
        assertTrue(heroAircraft.notValid());
    }

    @Test
    void shoot() {
        System.out.println("--Test shoot method--");
        assertNotNull(heroAircraft.shoot());
    }

    @Test
    void increaseHp() {
        System.out.println("--Test increaseHP method--");
        int increase = 10;
        heroAircraft.increaseHp(increase);
        assertEquals(heroAircraft.maxHp, heroAircraft.getHp());
        heroAircraft.decreaseHp(30);
        int hp = heroAircraft.getHp();
        heroAircraft.increaseHp(increase);
        assertEquals(hp + increase, heroAircraft.getHp());
    }
}