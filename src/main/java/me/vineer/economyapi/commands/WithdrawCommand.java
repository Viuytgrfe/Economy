package me.vineer.economyapi.commands;

import me.vineer.economyapi.events.CheckCreateEvent;
import me.vineer.economyapi.money.Balance;
import me.vineer.economyapi.money.MoneyType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class WithdrawCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return true;
        if(args.length != 2)return true;
        Player player = (Player) sender;
        Balance balance = Balance.getPlayerBalance(player.getName());
        if(args[0].equals("money")) {
            int amount = Integer.parseInt(args[1]);
            if(amount <= balance.getMoney()) {
                ItemStack item = Balance.createCheck(MoneyType.MONEY, amount, player.getName());
                CheckCreateEvent event = new CheckCreateEvent(item, player, player.getName(), amount, MoneyType.MONEY);
                Bukkit.getServer().getPluginManager().callEvent(event);
                if(!event.isCancelled()) {
                    if(player.getInventory().addItem(item).size() != 0 ) {
                        player.getWorld().dropItem(player.getLocation(), item);
                    }
                }
            }
        } else if (args[0].equals("donateMoney")) {
            int amount = Integer.parseInt(args[1]);
            if(amount <= balance.getDonateMoney()) {
                ItemStack item = Balance.createCheck(MoneyType.DONATE_MONEY, amount, player.getName());
                CheckCreateEvent event = new CheckCreateEvent(item, player, player.getName(), amount, MoneyType.DONATE_MONEY);
                Bukkit.getServer().getPluginManager().callEvent(event);
                if(!event.isCancelled()) {
                    if (player.getInventory().addItem(item).size() != 0) {
                        player.getWorld().dropItem(player.getLocation(), item);
                    }
                }
            }
        }
        return true;
    }
}
