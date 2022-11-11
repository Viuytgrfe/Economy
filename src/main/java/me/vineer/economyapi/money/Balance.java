package me.vineer.economyapi.money;

import me.vineer.economyapi.EconomyAPI;
import me.vineer.economyapi.config.ConfigAddress;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class Balance {
    private int money;
    private int donateMoney;
    private final Player member;

    private Balance(int money, int donateMoney, Player member) {
        this.money = money;
        this.donateMoney = donateMoney;
        this.member = member;
    }

    public int getMoney() {
        return money;
    }

    public int getDonateMoney() {
        return donateMoney;
    }

    public void setDonateMoney(int donateMoney) {
        this.donateMoney = donateMoney;
        setPlayerBalance(member, money, this.donateMoney);
    }

    public Player getMember() {
        return member;
    }

    public void setMoney(int money) {
        this.money = money;
        setPlayerBalance(member, this.money, donateMoney);
    }

    public static Balance getPlayerBalance(Player player) {
        Map<String,Object> value = (Map<String, Object>) EconomyAPI.getBalanceConfig().get(ConfigAddress.BALANCE(player));
        if(value == null) {
            setPlayerBalance(player, 0, 0);
            getPlayerBalance(player);
        }

        return Balance.deserialize(value);
    }

    public static void setPlayerBalance(Player player, int money, int donateMoney) {
        Map<String, Object> balance = new Balance(money, donateMoney, player).serialize();
        EconomyAPI.getBalanceConfig().set(ConfigAddress.BALANCE(player), balance);
    }

    @Override
    public String toString() {
        return "Balance{" +
                "money=" + money +
                ", donateMoney=" + donateMoney +
                ", member=" + member +
                '}';
    }
    public Map<String,Object> serialize() {
        Map<String,Object> m = new HashMap<String,Object>();
        m.put("money", getMoney());
        m.put("donateMoney", getDonateMoney());
        m.put("member", getMember());
        return m;
    }

    public static Balance deserialize(Map<String,Object> m) {
        return new Balance((int)m.get("money"), (int)m.get("donateMoney"), (Player) m.get("member"));
    }
}
