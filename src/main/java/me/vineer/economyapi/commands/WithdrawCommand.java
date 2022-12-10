package me.vineer.economyapi.commands;

import me.vineer.economyapi.events.CheckCreateEvent;
import me.vineer.economyapi.money.Balance;
import me.vineer.economyapi.money.MoneyType;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Barrel;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.jetbrains.annotations.NotNull;

public class WithdrawCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return true;
        if(args.length != 2)return true;
        Player player = (Player) sender;
        Balance balance = Balance.getPlayerBalance(player.getName());
        LuckPerms luckPerms = LuckPermsProvider.get();
        User user = luckPerms.getPlayerAdapter(Player.class).getUser(player);
        String prefix = user.getCachedData().getMetaData().getPrefix();
        String suffix = user.getCachedData().getMetaData().getSuffix();
        if(prefix == null)prefix = "";
        if(suffix == null)suffix = "";
        String name =  prefix + player.getName() + suffix;
        name = name.replaceAll("&", "§");
        if(args[0].equals("money") || args[0].equals("$")) {
            int amount = Integer.parseInt(args[1]);
            if(amount <= balance.getMoney() && amount > 0) {
                ItemStack item = Balance.createCheck(MoneyType.MONEY, amount, name, player.getName());
                CheckCreateEvent event = new CheckCreateEvent(item, player, name, amount, MoneyType.MONEY);
                Bukkit.getServer().getPluginManager().callEvent(event);
                if(!event.isCancelled()) {
                    if(player.getInventory().addItem(item).size() != 0 ) {
                        player.getWorld().dropItem(player.getLocation(), item);
                    }
                }
            } else {
                player.sendMessage(ChatColor.YELLOW + "[EA] " + ChatColor.RED + "У вас недостаточно денег либо введено отрицательное число!");
            }
        } else if (args[0].equals("donateMoney") || args[0].equals("Ⓟ")) {
            int amount = Integer.parseInt(args[1]);
            if(amount <= balance.getDonateMoney() && amount > 0) {
                ItemStack item = Balance.createCheck(MoneyType.DONATE_MONEY, amount, name, player.getName());
                CheckCreateEvent event = new CheckCreateEvent(item, player, name, amount, MoneyType.DONATE_MONEY);
                Bukkit.getServer().getPluginManager().callEvent(event);
                if(!event.isCancelled()) {
                    if (player.getInventory().addItem(item).size() != 0) {
                        player.getWorld().dropItem(player.getLocation(), item);
                    }
                }
            } else {
                player.sendMessage(ChatColor.YELLOW + "[EA] " + ChatColor.RED + "У вас недостаточно денег либо введено отрицательное число!");
            }
        }
        return true;
    }
}
