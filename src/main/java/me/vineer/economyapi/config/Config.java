package me.vineer.economyapi.config;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {
    private final File file;
    private FileConfiguration customFile;

    public Config(String name) {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("EconomyAPI").getDataFolder(), name + ".yml");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        customFile = YamlConfiguration.loadConfiguration(file);
        getCustomFile().options().copyDefaults(true);
        saveCustomFile();
    }

    public FileConfiguration getCustomFile() {
        return customFile;
    }

    public void saveCustomFile() {
        try {
            customFile.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadCustomFile() {
        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public void set(String path, String value) {
        getCustomFile().set(path, value);
        saveCustomFile();
    }

    public Object get(String path) {
        return getCustomFile().get(path);
    }

    public void deleteConfig() {
        file.delete();
    }
}
