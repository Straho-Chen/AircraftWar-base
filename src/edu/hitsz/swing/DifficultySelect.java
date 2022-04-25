package edu.hitsz.swing;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.thread.MusicThread;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DifficultySelect {
    public JPanel mainPanel;
    private JButton easyMode;
    private JPanel topPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel bottomPanel;
    private JPanel middlePanel;
    private JPanel easyPanel;
    private JPanel mediumPanel;
    private JPanel hardPanel;
    private JPanel musicPanel;
    private JButton mediumMode;
    private JButton hardMode;
    private JComboBox musicComboBox;
    private JLabel musicLabel;

    private String difficulty;
    public boolean bgmStart = true;

    public DifficultySelect() {
        Object object = Main.object;
        easyMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (object) {
                    ImageManager.setBackgroundImage("src/images/bg.jpg");
                    difficulty = "Easy";
                    object.notify();
                }
            }
        });
        mediumMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (object) {
                    ImageManager.setBackgroundImage("src/images/bg3.jpg");
                    difficulty = "Medium";
                    object.notify();
                }
            }
        });
        hardMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (object) {
                    ImageManager.setBackgroundImage("src/images/bg5.jpg");
                    difficulty = "Hard";
                    object.notify();
                }
            }
        });
        musicComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (musicComboBox.getSelectedIndex() == 1) {
                    bgmStart = false;
                }
            }
        });
    }

    public String returnDifficulty() {
        return difficulty;
    }
}
