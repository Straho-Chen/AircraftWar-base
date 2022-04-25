package edu.hitsz.swing;

import edu.hitsz.dao.Player;
import edu.hitsz.dao.PlayerDao;
import edu.hitsz.dao.PlayerDaoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RankList {
    public JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel bottomPanel;
    private JPanel topPanel;
    private JPanel middlePanel;
    private JLabel difficultyLabel;
    private JLabel name;
    private JScrollPane dataScrollPanel;
    private JTable dataTable;
    private JButton deleteButton;

    private PlayerDao playerDao = new PlayerDaoImpl();

    private String[] columnName = {"名次","玩家名","得分","记录时间"};

    public RankList(String difficulty, int score, String playerName) {
        if (difficulty.equals("Easy")) {
            difficultyLabel.setText("难度：EASY");
        }
        else if (difficulty.equals("Medium")) {
            difficultyLabel.setText("难度：MEDIUM");
        }
        else {
            difficultyLabel.setText("难度：HARD");
        }
        if (playerName != null) {
            Player player = new Player();
            player.setName(playerName);
            Date date = new Date();
            SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
            player.setTime(df.format(date));
            player.setScore(score);
            player.setDifficulty(difficulty);
            playerDao.doAdd(player);
            playerDao.writeFile();
        }

        // TODO: place custom component creation code here
        DefaultTableModel model = new DefaultTableModel(setDataVector(difficulty), columnName){
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        dataTable.setModel(model);
        dataScrollPanel.setViewportView(dataTable);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = dataTable.getSelectedRow();
                if (row != -1) {
                    int isDelete = JOptionPane.showConfirmDialog(null, "是否确定删除选中的玩家？");
                    if (isDelete == 0) {
                        String name = model.getValueAt(row, 1).toString();
                        String time = model.getValueAt(row, 3).toString();
                        playerDao.doDelete(name, time);
                        DefaultTableModel replace = (DefaultTableModel) dataTable.getModel();
                        replace.setDataVector(setDataVector(difficulty), columnName);
                        dataTable.updateUI();
                    }
                    playerDao.writeFile();
                }
            }
        });
    }

    private Object[][] setDataVector(String difficulty) {
        List<Player> players = new ArrayList<>();
        for (Player player : playerDao.getAllPlayers()) {
            if (player.getDifficulty().equals(difficulty)) {
                players.add(player);
            }
        }
        Object[][] tableData = new Object[players.size()][4];
        for (int i = 0; i < players.size(); i++) {
            tableData[i][0] = i+1;
            tableData[i][1] = players.get(i).getName();
            tableData[i][2] = players.get(i).getScore();
            tableData[i][3] = players.get(i).getTime();
        }
        return tableData;
    }
}
