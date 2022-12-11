package me.vineer.economyapi;

import me.vineer.economyapi.commands.MoneyCommand;
import me.vineer.economyapi.commands.WithdrawCommand;
import me.vineer.economyapi.database.Database;
import me.vineer.economyapi.listeners.EconomyListener;
import me.vineer.economyapi.tabCompleters.MoneyTabCompleter;
import me.vineer.economyapi.tabCompleters.WithdrawCompleter;
import org.bukkit.plugin.java.JavaPlugin;

public final class EconomyAPI extends JavaPlugin {
    private static final JavaPlugin plugin = JavaPlugin.getProvidingPlugin(EconomyAPI.class);
    public static EconomyAPI instance;
    @Override
    public void onEnable() {
        instance = this;
        Database.initDatabase();
        registerEvents();
    }

    public static EconomyAPI getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        instance = null;
        Database.disconnect();
    }

    public static JavaPlugin getPlugin() {
        return plugin;
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
