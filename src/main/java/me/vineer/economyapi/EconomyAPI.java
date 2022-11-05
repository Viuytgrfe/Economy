package me.vineer.economyapi;

import me.vineer.economyapi.config.Config;
import me.vineer.economyapi.events.PlayerEvents;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class EconomyAPI extends JavaPlugin {
    private static JavaPlugin plugin;
    private static Config BalanceConfig;
    @Override
    public void onEnable() {
        EconomyAPI.setPlugin(this);
        registerEvents();

        getConfig().options().copyDefaults(true);
        saveConfig();

        BalanceConfig = new Config("Balance");
    }
    @Override
    public void onDisable() {
        System.out.println("Disabling EconomyAPI!");
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    private static void setPlugin(final JavaPlugin plugin) {
        EconomyAPI.plugin = plugin;
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
    }

    public static Config getBalanceConfig() {
        return BalanceConfig;
    }
}
