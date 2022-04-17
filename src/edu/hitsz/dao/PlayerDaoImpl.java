package edu.hitsz.dao;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Strah
 */
public class PlayerDaoImpl implements PlayerDao{
    private List<Player> players = new ArrayList<>();

    public PlayerDaoImpl() {
        File file = new File("C:\\Users\\Strah\\Documents\\AircraftWar-base\\src\\edu\\hitsz\\dao\\data\\PlayerInfo.dat");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Player[] player = (Player[]) ois.readObject();
            players = new ArrayList<Player>(Arrays.asList(player));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void findByName(String name) {
        for (Player player : players) {
            if (name.equals(player.getName())) {
                System.out.println("第"+player.getRank()+"名："+player.getName()+','+player.getScore()+','+player.getTime());
                return;
            }
        }
        System.out.println("Can not find this player!");
    }

    @Override
    public List<Player> getAllPlayers() {
        return players;
    }

    @Override
    public void doAdd(Player player) {
        players.add(player);
        Collections.sort(players);
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setRank(i + 1);
        }
    }

    @Override
    public void doDelete(String name) {
        for (Player player : players) {
            if (name.equals(player.getName())) {
                players.remove(player);
                return;
            }
        }
        System.out.println("Can not find this player!");
    }
}
