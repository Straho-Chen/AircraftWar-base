package edu.hitsz.application;

import edu.hitsz.swing.DifficultySelect;
import edu.hitsz.swing.RankList;

import javax.swing.*;
import java.awt.*;

/**
 * 程序入口
 * @author hitsz
 */
public class Main {

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;
    public static final Object object = new Object();
    public static void main(String[] args) {

        System.out.println("Hello Aircraft War");

        // 获得屏幕的分辨率，初始化 Frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Aircraft War");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        //设置窗口的大小和位置,居中放置
        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        synchronized (object) {
            try {
                DifficultySelect difficultySelect = new DifficultySelect();
                frame.add(difficultySelect.mainPanel);
                frame.setVisible(true);
                object.wait();
                frame.remove(difficultySelect.mainPanel);
                Game game = new Game(difficultySelect.bgmStart);
                frame.add(game);
                frame.setVisible(true);
                game.action();
                object.wait();
                frame.remove(game);
                String outputStream = "游戏结束，你的得分是"+game.score+'.'+'\n'+"请输入名字记录得分：";
                String playerName = JOptionPane.showInputDialog(null, outputStream);
                RankList rankList = new RankList(difficultySelect.returnDifficulty(), game.score, playerName);
                frame.add(rankList.mainPanel);
                frame.setVisible(true);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
