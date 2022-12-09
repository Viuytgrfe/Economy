package me.vineer.economyapi.commands;

import me.vineer.economyapi.money.Balance;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MoneyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        if (args.length == 3 && args[0].equals("get") && args[1].equals("money") && sender.isOp()) {
            player.sendMessage("" + Balance.getPlayerBalance(args[2]).getMoney());
        } else if (args.length == 3 && args[0].equals("get") && args[1].equals("donateMoney") && sender.isOp()) {
            player.sendMessage("" + Balance.getPlayerBalance(args[2]).getDonateMoney());
        } else if (args.length == 4 && args[0].equals("set") && args[1].equals("money") && sender.isOp()) {
            Balance.setPlayerBalance(Integer.parseInt(args[2]), Balance.getPlayerBalance(args[3]).getDonateMoney(), args[3]);
        } else if (args.length == 4 && args[0].equals("set") && args[1].equals("donateMoney") && sender.isOp()) {
            Balance.setPlayerBalance(Balance.getPlayerBalance(args[3]).getMoney(), Integer.parseInt(args[2]), args[3]);
        } else if (args.length == 4 && args[0].equals("remove") && args[1].equals("money") && sender.isOp()) {
            Balance.changePlayerBalance(args[3], -Integer.parseInt(args[2]), 0);
        } else if (args.length == 4 && args[0].equals("remove") && args[1].equals("donateMoney") && sender.isOp()) {
            Balance.changePlayerBalance(args[3], 0, -Integer.parseInt(args[2]));
        } else if (args.length == 4 && args[0].equals("add") && args[1].equals("money") && sender.isOp()) {
            Balance.changePlayerBalance(args[3], Integer.parseInt(args[2]), 0);
        } else if (args.length == 4 && args[0].equals("add") && args[1].equals("donateMoney") && sender.isOp()) {
            Balance.changePlayerBalance(args[3], 0, Integer.parseInt(args[2]));
        } else if(!player.isOp() || args.length == 0) {
            player.sendMessage(ChatColor.WHITE + "Твои деньги: " + Balance.getPlayerBalance(sender.getName()).getMoney());
        }
        return true;
    }
}
