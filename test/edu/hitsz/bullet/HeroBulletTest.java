package edu.hitsz.bullet;

import edu.hitsz.aircraft.MobEnemy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeroBulletTest {

    private HeroBullet heroBullet;
    private MobEnemy Mob;

    @BeforeEach
    void setUp() {
        System.out.println("--Test HeroBullet--");
        heroBullet = new HeroBullet(10, 15, 0, -5, 10);
        Mob = new MobEnemy(10, 10, 0, 5, 10);
    }

    @AfterEach
    void tearDown() {
        System.out.println("--Test finish--");
        heroBullet = null;
        Mob = null;
    }

    @Test
    void forward() {
        System.out.println("--Test forward method--");

        heroBullet = new HeroBullet(10, 5, 0, -5, 10);
        heroBullet.forward();
        assertTrue(heroBullet.notValid());
    }

    @Test
    void crash() {
        System.out.println("--Test crash method--");

        heroBullet.forward();
        assertTrue(heroBullet.crash(Mob));
    }
}