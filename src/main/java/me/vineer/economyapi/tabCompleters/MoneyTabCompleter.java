package me.vineer.economyapi.tabCompleters;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MoneyTabCompleter implements TabCompleter {
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> complete = new ArrayList<>();
        if(sender.isOp()) {
            if(args.length == 1) {
                complete.add("get");
                complete.add("set");
                complete.add("remove");
                complete.add("add");
                return complete;
            } else if (args.length == 2 && (args[0].equals("get") || args[0].equals("set") || args[0].equals("remove") || args[0].equals("add"))) {
                complete.add("$");
                complete.add("Ⓟ");
                return complete;
            } else if (args.length == 3 && args[0].equals("get")) {
                Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
                Bukkit.getServer().getOnlinePlayers().toArray(players);
                for(int i = 0; i < players.length; i++) {
                    complete.add(players[i].getName());
                }
                return complete;
            } else if (args.length == 3 && (args[0].equals("set") || args[0].equals("remove") || args[0].equals("add"))) {
                complete.add("<amount of money>");
                complete.add("1");
                complete.add("2");
                complete.add("5");
                complete.add("10");
                complete.add("20");
                complete.add("50");
                complete.add("100");
                complete.add("200");
                complete.add("500");
                return complete;
            } else if (args.length == 4 && (args[0].equals("set") || args[0].equals("remove") || args[0].equals("add"))) {
                Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
                Bukkit.getServer().getOnlinePlayers().toArray(players);
                for(int i = 0; i < players.length; i++) {
                    complete.add(players[i].getName());
                }
                return complete;
            }
        }
        return null;
    }
}
