package edu.hitsz.dao;

import java.util.List;

/**
 * @author Strah
 */
public interface PlayerDao {

    /**
     * 根据名字查询玩家信息
     * @param name
     */
    public void findByName(String name);

    /**
     * 获取所有玩家信息
     * @return
     */
    public List<Player> getAllPlayers();

    /**
     * 添加玩家信息
     * @param player
     */
    public void doAdd(Player player);

    /**
     * 根据名字删除指定玩家信息
     * @param name
     */
    public void doDelete(String name);
}
