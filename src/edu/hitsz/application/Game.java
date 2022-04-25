package edu.hitsz.application;

import edu.hitsz.dao.Player;
import edu.hitsz.dao.PlayerDao;
import edu.hitsz.dao.PlayerDaoImpl;
import edu.hitsz.prop.strategy.ChangeBallistic;
import edu.hitsz.aircraft.*;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.factory.*;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BloodSupplyProp;
import edu.hitsz.prop.BombSupplyProp;
import edu.hitsz.prop.FireSupplyProp;
import edu.hitsz.thread.MusicThread;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public class Game extends JPanel {

    private int backGroundTop = 0;

    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    private AbstractEnemyFactory enemyFactory;
    private final List<AbstractAircraft> enemyAircrafts;
    private final List<AbstractBullet> heroBullets;
    private final List<AbstractBullet> enemyBullets;
    private AbstractPropFactory propFactory;
    private final List<AbstractProp> propSupply;
    private Player player = new Player();
    public List<Player> players;
    private PlayerDao playerDao = new PlayerDaoImpl();

    private int enemyMaxNumber = 5;

    public boolean gameOverFlag = false;
    public int score = 0;
    boolean bossExit = false;
    private int bossScoreThreshold = 100;
    private int time = 0;
    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    private int cycleDuration = 600;
    private int cycleTime = 0;
    private boolean bgmStart;
    private MusicThread bgmThread;
    private MusicThread bossBgmThread;
    private MusicThread bombBgmThread;
    private MusicThread bulletHitBgmThread;
    private MusicThread gameOverBgmThread;
    private MusicThread getSupplyBgmThread;


    public Game(boolean bgmStart) {
        heroAircraft = HeroAircraft.getSingleton();
        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        propSupply = new LinkedList<>();

        this.bgmStart = bgmStart;

        /**
         * Scheduled 线程池，用于定时任务调度
         * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
         * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {
        if (bgmStart) {
            bgmThread = new MusicThread("src/videos/bgm.wav", 2);
            bgmThread.start();
        }

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            time += timeInterval;

            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {
                System.out.println(time);
                // 新敌机产生
                Random r = new Random();
                int t = r.nextInt(100);
                if (t%2 == 0 && enemyAircrafts.size() < enemyMaxNumber) {
                    enemyFactory = new MobFactory();
                    enemyAircrafts.add(enemyFactory.creatEnemy());
                }
                else if (t%3 == 0 && enemyAircrafts.size() < enemyMaxNumber) {
                    enemyFactory = new EliteFactory();
                    enemyAircrafts.add(enemyFactory.creatEnemy());
                }
                if(isThreshold()) {
                    bossExit = false;
                    for (AbstractAircraft enemy : enemyAircrafts) {
                        if (Boss.class.isAssignableFrom(enemy.getClass())) {
                            bossExit = true;
                            break;
                        }
                    }
                    if (!bossExit) {
                        enemyFactory = new BossFactory();
                        enemyAircrafts.add(enemyFactory.creatEnemy());
                        if (bgmStart) {
                            bossBgmThread = new MusicThread("src/videos/bgm_boss.wav", 2);
                            bossBgmThread.start();
                        }
                    }
                }
                // 飞机射出子弹
                shootAction();
            }

            // 子弹移动
            bulletsMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            //道具移动
            propsupplyMoveAction();

            // 撞击检测
            crashCheckAction();

            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            repaint();

            // 游戏结束检查
            if (heroAircraft.getHp() <= 0) {

                Object object = Main.object;
                synchronized (object) {
                    // 游戏结束
                    executorService.shutdown();
                    if (bgmStart) {
                        gameOverBgmThread = new MusicThread("src/videos/game_over.wav", 1);
                        gameOverBgmThread.start();
                        bgmThread.setMusicOverFlag(true);
                        if (bossExit) {
                            bossBgmThread.setMusicOverFlag(true);
                        }
                    }
                    gameOverFlag = true;
                    object.notify();
                    System.out.println("Game Over!");
                }
            }

        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

    }

    //***********************
    //      Action 各部分
    //***********************

    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private boolean isThreshold() {
        if (score != 0 && score % bossScoreThreshold == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    private void shootAction() {
        // TODO 敌机射击
        for (AbstractAircraft enemy : enemyAircrafts) {
            if (EliteEnemy.class.isAssignableFrom(enemy.getClass())) {
                enemyBullets.addAll(enemy.executeStrategy(enemy));
            }
            else if (Boss.class.isAssignableFrom(enemy.getClass())) {
                enemyBullets.addAll(enemy.executeStrategy(enemy));
            }
        }
//         英雄射击
        heroBullets.addAll(heroAircraft.executeStrategy(heroAircraft));
    }

    private void bulletsMoveAction() {
        for (AbstractBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (AbstractBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    private void propsupplyMoveAction() {
        for (AbstractProp propsupply: propSupply) {
            propsupply.forward();
        }
    }


    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // TODO 敌机子弹攻击英雄
        for (AbstractBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.notValid()) {
                // 已被其他子弹或敌机击毁的英雄机，不再检测
                break;
            }
            if (heroAircraft.crash(bullet)) {
                // 英雄机撞击到敌机子弹
                // 英雄机损失一定生命值
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
                if (heroAircraft.notValid()) {
                    // 英雄机被击毁，不再检测
                    break;
                }
            }
        }
        // 英雄子弹攻击敌机
        for (AbstractBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    if (bgmStart) {
                        bulletHitBgmThread = new MusicThread("src/videos/bullet_hit.wav", 1);
                        bulletHitBgmThread.start();
                    }
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        // TODO 获得分数，产生道具补给
                        if (!MobEnemy.class.isAssignableFrom(enemyAircraft.getClass())) {
                            Random r = new Random();
                            int t = r.nextInt(7);
                            if (t == 4) {
                                propFactory = new BloodSupplyFactory();
                                propSupply.add(propFactory.creatProp(enemyAircraft));
                            }
                            else if (t == 5 || t == 2 || t == 3) {
                                propFactory = new FireSupplyFactory();
                                propSupply.add(propFactory.creatProp(enemyAircraft));
                            }
                            else if (t == 6) {
                                propFactory = new BombSupplyFactory();
                                propSupply.add(propFactory.creatProp(enemyAircraft));
                            }
                        }
                        if (MobEnemy.class.isAssignableFrom(enemyAircraft.getClass())) {
                            score += 10;
                        }
                        else if (EliteEnemy.class.isAssignableFrom(enemyAircraft.getClass())) {
                            score += 20;
                        }
                        else {
                            score += 30;
                            if (bgmStart) {
                                bossBgmThread.setMusicOverFlag(true);
                            }
                        }
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // Todo: 我方获得道具，道具生效
        for (AbstractProp prop : propSupply) {
            if (prop.notValid()) {
                continue;
            }
            if (heroAircraft.notValid()) {
                // 已被其他子弹或敌机击毁的英雄机，不再检测
                break;
            }
            if (heroAircraft.crash(prop)) {
                // 英雄机撞击到道具
                if (bgmStart) {
                    getSupplyBgmThread = new MusicThread("src/videos/get_supply.wav", 1);
                    getSupplyBgmThread.start();
                }
                if (BloodSupplyProp.class.isAssignableFrom(prop.getClass())) {
                    ((BloodSupplyProp) prop).increaseHp(heroAircraft);
                }
                else if (FireSupplyProp.class.isAssignableFrom(prop.getClass())) {
                    ((FireSupplyProp) prop).setPropStrategy(new ChangeBallistic());
                    ((FireSupplyProp) prop).executeStrategy(heroAircraft);
                }
                else if (BombSupplyProp.class.isAssignableFrom(prop.getClass())) {
                    if (bgmStart) {
                        bombBgmThread = new MusicThread("src/videos/bomb_explosion.wav", 1);
                        bombBgmThread.start();
                    }
                    ((BombSupplyProp) prop).boom();
                }
                prop.vanish();
                if (heroAircraft.notValid()) {
                    // 英雄机被击毁，不再检测
                    break;
                }
            }
        }
    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 检查英雄机生存
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        propSupply.removeIf(AbstractFlyingObject::notValid);
    }


    //***********************
    //      Paint 各部分
    //***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param  g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);

        paintImageWithPositionRevised(g, enemyAircrafts);
        paintImageWithPositionRevised(g, propSupply);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }


}
