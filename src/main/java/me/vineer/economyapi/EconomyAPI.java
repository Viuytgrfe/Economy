package me.vineer.economyapi;

import me.vineer.economyapi.config.Config;
import org.bukkit.plugin.java.JavaPlugin;

public final class EconomyAPI extends JavaPlugin {
    private static JavaPlugin plugin;
    @Override
    public void onEnable() {
        EconomyAPI.setPlugin(this);

        getConfig().options().copyDefaults(true);
        saveConfig();

        Config config = new Config("TestConfig");
        config.getCustomFile().addDefault("Taco", "Rice");
        config.saveCustomFile();
    }
    @Override
    public void onDisable() {
        System.out.println("Disabling EconomyAPI!");
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }
    public static void setPlugin(final JavaPlugin plugin) {
        EconomyAPI.plugin = plugin;
    }
}
