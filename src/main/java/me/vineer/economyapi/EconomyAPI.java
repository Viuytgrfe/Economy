package me.vineer.economyapi;

import org.bukkit.plugin.java.JavaPlugin;

public final class EconomyAPI extends JavaPlugin {
    private static JavaPlugin plugin;
    @Override
    public void onEnable() {
        EconomyAPI.setPlugin(this);
    }
    @Override
    public void onDisable() {

    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }
    public static void setPlugin(final JavaPlugin plugin) {
        EconomyAPI.plugin = plugin;
    }
}
