package me.vineer.economyapi;

import me.vineer.economyapi.commands.MoneyCommand;
import me.vineer.economyapi.commands.WithdrawCommand;
import me.vineer.economyapi.database.Database;
import me.vineer.economyapi.listeners.EconomyListener;
import me.vineer.economyapi.listeners.MenuListener;
import me.vineer.economyapi.tabCompleters.MoneyTabCompleter;
import me.vineer.economyapi.tabCompleters.WithdrawCompleter;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class EconomyAPI extends JavaPlugin {
    public static EconomyAPI plugin;
    public static LuckPerms luckPerms;

    private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();

    @Override
    public void onEnable() {
        plugin = this;
        luckPerms = LuckPermsProvider.get();
        Database.initDatabase();
        registerEvents();
    }

    public static EconomyAPI getPlugin() {
        return plugin;
    }

    @Override
    public void onDisable() {
        Database.disconnect();
    }


    private void registerEvents() {
        new EconomyExpansion().register();
        getServer().getPluginManager().registerEvents(new EconomyListener(), this);
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        this.getCommand("money").setExecutor(new MoneyCommand());
        this.getCommand("money").setTabCompleter(new MoneyTabCompleter());
        this.getCommand("withdraw").setExecutor(new WithdrawCommand());
        this.getCommand("withdraw").setTabCompleter(new WithdrawCompleter());
    }

    public static PlayerMenuUtility getPlayerMenuUtility(Player player) {
        PlayerMenuUtility playerMenuUtility;
        if(playerMenuUtilityMap.containsKey(player)) {
            return playerMenuUtilityMap.get(player);
        } else {
            playerMenuUtility = new PlayerMenuUtility(player);
            playerMenuUtilityMap.put(player, playerMenuUtility);

            return playerMenuUtility;
        }
    }
}
