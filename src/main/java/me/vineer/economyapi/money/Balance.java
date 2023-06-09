package me.vineer.economyapi.money;

import me.vineer.economyapi.EconomyAPI;
import me.vineer.economyapi.database.Database;
import me.vineer.economyapi.items.ItemCreator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Balance {

    public static final NamespacedKey Moneykey = new NamespacedKey(EconomyAPI.getPlugin(), "money");
    public static final NamespacedKey MoneyType = new NamespacedKey(EconomyAPI.getPlugin(), "type");


    private double money;
    private double donateMoney;
    private final String member;

    private Balance(double money, double donateMoney, String member) {
        this.money = money;
        this.donateMoney = donateMoney;
        this.member = member;
    }
    public void setMoney(double money) {
        this.money = money;
        setPlayerBalance(this.money, donateMoney, member);
    }

    public double getMoney() {
        return money;
    }

    public double getDonateMoney() {
        return donateMoney;
    }

    public void setDonateMoney(double donateMoney) {
        this.donateMoney = donateMoney;
        setPlayerBalance(money, this.donateMoney, member);
    }

    public String getMember() {
        return member;
    }


    public boolean sendMoneyTo(String toSendPlayer, double amount) {
        Balance toBalance = Balance.getPlayerBalance(toSendPlayer);
        if(getMoney() - amount < 0)return false;
        setMoney(getMoney() - amount);
        toBalance.setMoney(toBalance.getMoney() + amount);
        return true;
    }

    public static void setPlayerBalance(double money, double donateMoney, String member) {
        if(hasBalance(member)) {
            try {
                PreparedStatement pr = Database.getConnection().prepareStatement("UPDATE Players SET money = ?, donateMoney = ? WHERE Name = ?");
                pr.setDouble(1, money);
                pr.setDouble(2, donateMoney);
                pr.setString(3, member);
                pr.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                PreparedStatement pr = Database.getConnection().prepareStatement("INSERT INTO Players(Name, money, donateMoney) VALUES (?, ?, ?)");
                pr.setString(1, member);
                pr.setDouble(2, money);
                pr.setDouble(3, donateMoney);
                pr.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Balance getPlayerBalance(String player) {
        boolean has_account = false;
        try {
            PreparedStatement pr = Database.getConnection().prepareStatement("SELECT * FROM Players WHERE Name = ?");
            pr.setString(1, player);
            ResultSet rs = pr.executeQuery();
            has_account = rs.next();
            if(has_account) {
                return new Balance(rs.getDouble("money"), rs.getDouble("donateMoney"), player);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(!has_account) {
            setPlayerBalance(300, 0, player);
            return new Balance(300, 0, player);
        }
        return new Balance(300, 0, player);
    }

    public static void changePlayerBalance(String member, double money, double donateMoney) {
        if(hasBalance(member)) {
            try {
                PreparedStatement pr = Database.getConnection().prepareStatement("UPDATE Players SET money = money + ?, donateMoney = donateMoney + ? WHERE Name = ?");
                pr.setDouble(1, money);
                pr.setDouble(2, donateMoney);
                pr.setString(3, member);
                pr.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            setPlayerBalance(300 + money, donateMoney, member);
        }
    }

    public static boolean hasBalance(String member) {
        boolean has_account = false;
        try {
            PreparedStatement pr = Database.getConnection().prepareStatement("SELECT * FROM Players WHERE Name = ?");
            pr.setString(1, member);
            ResultSet rs = pr.executeQuery();
            has_account = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return has_account;
    }
    public static ItemStack createCheck(MoneyType type, double amount, String fromPlayerDisplay, String fromPlayer) {
        ItemStack item = null;
        if(type.equals(me.vineer.economyapi.money.MoneyType.MONEY)) {
            item = ItemCreator.createGuiItem(Material.PAPER, ChatColor.WHITE + "Чек", ChatColor.GREEN + "$" + ChatColor.WHITE + amount, ChatColor.WHITE + "от " + fromPlayerDisplay);
            Balance.changePlayerBalance(fromPlayer, -amount, 0);
        } else if (type.equals(me.vineer.economyapi.money.MoneyType.DONATE_MONEY)) {
            item = ItemCreator.createGuiItem(Material.PAPER, ChatColor.WHITE + "Чек", ChatColor.YELLOW + "℗" + ChatColor.WHITE + amount, ChatColor.WHITE + "от " + fromPlayerDisplay);
            Balance.changePlayerBalance(fromPlayer, 0, -amount);
        }
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.getPersistentDataContainer().set(Moneykey, PersistentDataType.DOUBLE, amount);
        itemMeta.getPersistentDataContainer().set(MoneyType, PersistentDataType.STRING, type.getName());
        item.setItemMeta(itemMeta);
        return item;
    }
    public static void changeCheck(@NotNull ItemStack check, MoneyType type, Double amount, String fromPlayer) {
        ItemMeta meta = check.getItemMeta();
        if(type != null) {
            meta.getPersistentDataContainer().set(MoneyType, PersistentDataType.STRING, type.getName());
        }
        if(amount != null) {
            meta.getPersistentDataContainer().set(Moneykey, PersistentDataType.DOUBLE, amount);
            if(meta.getPersistentDataContainer().get(MoneyType, PersistentDataType.STRING).equals(me.vineer.economyapi.money.MoneyType.MONEY.getName())) {
                List<String> lore = meta.getLore();
                lore.set(0, ChatColor.GREEN + "$" + ChatColor.WHITE + amount);
                meta.setLore(lore);
            } else {
                List<String> lore = meta.getLore();
                lore.set(0, ChatColor.YELLOW + "℗" + ChatColor.WHITE + amount);
                meta.setLore(lore);
            }

        } else if (fromPlayer != null) {
            List<String> lore = meta.getLore();
            lore.set(1, ChatColor.WHITE + "от " + fromPlayer);
            meta.setLore(lore);
        }
        check.setItemMeta(meta);
    }
}
