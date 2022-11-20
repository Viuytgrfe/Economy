package me.vineer.economyapi.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import me.vineer.economyapi.money.Balance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MoneyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(command.getName().equals("money")) {
            Player player = (Player) sender;
            if(args.length == 2 && player.isOp()) {
                Balance.setPlayerBalance(Integer.parseInt(args[0]), Integer.parseInt(args[1]), player.getName());
            }
            player.sendMessage(PlaceholderAPI.setPlaceholders(player, "%economy_balance%"));
            player.sendMessage(PlaceholderAPI.setPlaceholders(player, "%economy_donate_balance%"));
        }
        return true;
    }
}
