package me.vineer.economyapi.database;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

import java.sql.*;

public class Database {
    public static String url = "jdbc:sqlite:Economy.db";
    public static Connection con;
    static ConsoleCommandSender console = Bukkit.getConsoleSender();

    public static void connect() {
        if (!isConnected()) {
            try {
                con = DriverManager.getConnection(url);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void disconnect() {
        if (isConnected()) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isConnected() {
        return (con != null);
    }

    public static Connection getConnection() {
        return con;
    }

    public static void initDatabase() {
        Database.connect();
        try {
            PreparedStatement ps = Database.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS Players(Name VARCHAR(100), money int, donateMoney int, PRIMARY KEY (Name))");
            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
