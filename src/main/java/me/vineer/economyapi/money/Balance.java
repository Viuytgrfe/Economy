package me.vineer.economyapi.money;

import me.vineer.economyapi.EconomyAPI;
import org.bukkit.entity.Player;

public class Balance {
    private int money;

    public Balance(int money) {
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public static Balance getPlayerBalance(Player player) {
        Object value = EconomyAPI.getBalanceConfig().get("Player." + player.getName() + ".balance");
        int money;
        if(value == null) {
            money = 0;
        } else {
            money = (int)value;
        }
        return new Balance(money);
    }

    public static void setPlayerBalance(Player player, int value) {
        EconomyAPI.getBalanceConfig().set("Player." + player.getName() + ".balance", String.valueOf(value));
    }
}
