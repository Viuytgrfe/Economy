package me.vineer.economyapi;

import me.vineer.economyapi.commands.MoneyCommand;
import me.vineer.economyapi.commands.WithdrawCommand;
import me.vineer.economyapi.database.Database;
import me.vineer.economyapi.listeners.EconomyListener;
import me.vineer.economyapi.tabCompleters.MoneyTabCompleter;
import me.vineer.economyapi.tabCompleters.WithdrawCompleter;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class EconomyAPI extends JavaPlugin {
    public static Plugin plugin;
    @Override
    public void onEnable() {
        plugin = this;
        Database.initDatabase();
        registerEvents();
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void onDisable() {
        Database.disconnect();
    }


    private void registerEvents() {
        new EconomyExpansion().register();
        getServer().getPluginManager().registerEvents(new EconomyListener(), this);
        this.getCommand("money").setExecutor(new MoneyCommand());
        this.getCommand("money").setTabCompleter(new MoneyTabCompleter());
        this.getCommand("withdraw").setExecutor(new WithdrawCommand());
        this.getCommand("withdraw").setTabCompleter(new WithdrawCompleter());
    }
}
