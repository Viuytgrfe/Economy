package me.vineer.economyapi;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Utils {
    public static void tell(final CommandSender sender, final String message) {
        sender.sendMessage(colorize(message));
    }

    public static void log(final String message) {
        tell(Bukkit.getConsoleSender(), "[" + EconomyAPI.getPlugin().getName() + "] " + message);
    }

    public static String colorize(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
