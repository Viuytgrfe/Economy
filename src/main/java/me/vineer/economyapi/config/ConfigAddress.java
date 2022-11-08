package me.vineer.economyapi.config;

import org.bukkit.entity.Player;

public class ConfigAddress {
    public static String BALANCE(Player player) {
        return "Player." + player.getName() + ".balance";
    }
    public static String MONEY(Player player) {
        return "Player." + player.getName() + ".balance.money";
    }
    public static String DONATE_MONEY(Player player) {
        return "Player." + player.getName() + ".balance.donate";
    }
}
