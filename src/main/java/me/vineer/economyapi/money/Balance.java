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

    public static final NamespacedKey Moneykey = new NamespacedKey(EconomyAPI.getInstance(), "money");
    public static final NamespacedKey MoneyType = new NamespacedKey(EconomyAPI.getInstance(), "type");


    private int money;
    private int donateMoney;
    private final String member;

    private Balance(int money, int donateMoney, String member) {
        this.money = money;
        this.donateMoney = donateMoney;
        this.member = member;
    }
    public void setMoney(int money) {
        this.money = money;
        setPlayerBalance(this.money, donateMoney, member);
    }

    public int getMoney() {
        return money;
    }

    public int getDonateMoney() {
        return donateMoney;
    }

    public void setDonateMoney(int donateMoney) {
        this.donateMoney = donateMoney;
        setPlayerBalance(money, this.donateMoney, member);
    }

    public String getMember() {
        return member;
    }


    public boolean sendMoneyTo(String toSendPlayer, int amount) {
        Balance toBalance = Balance.getPlayerBalance(toSendPlayer);
        if(getMoney() - amount < 0)return false;
        setMoney(getMoney() - amount);
        toBalance.setMoney(toBalance.getMoney() + amount);
        return true;
    }

    public static void setPlayerBalance(int money, int donateMoney, String member) {
        if(hasBalance(member)) {
            try {
                PreparedStatement pr = Database.getConnection().prepareStatement("UPDATE Players SET money = ?, donateMoney = ? WHERE Name = ?");
                pr.setInt(1, money);
                pr.setInt(2, donateMoney);
                pr.setString(3, member);
                pr.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                PreparedStatement pr = Database.getConnection().prepareStatement("INSERT INTO Players(Name, money, donateMoney) VALUES (?, ?, ?)");
                pr.setString(1, member);
                pr.setInt(2, money);
                pr.setInt(3, donateMoney);
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
                return new Balance(rs.getInt("money"), rs.getInt("donateMoney"), player);
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

    public static void changePlayerBalance(String member, int money, int donateMoney) {
        if(hasBalance(member)) {
            try {
                PreparedStatement pr = Database.getConnection().prepareStatement("UPDATE Players SET money = money + ?, donateMoney = donateMoney + ? WHERE Name = ?");
                pr.setInt(1, money);
                pr.setInt(2, donateMoney);
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
    public static ItemStack createCheck(MoneyType type, int amount, String fromPlayerDisplay, String fromPlayer) {
        ItemStack item = null;
        if(type.equals(me.vineer.economyapi.money.MoneyType.MONEY)) {
            item = ItemCreator.createGuiItem(Material.PAPER, ChatColor.WHITE + "Чек", ChatColor.GREEN + "$" + ChatColor.WHITE + amount, ChatColor.WHITE + "от " + fromPlayerDisplay);
            Balance.changePlayerBalance(fromPlayer, -amount, 0);
        } else if (type.equals(me.vineer.economyapi.money.MoneyType.DONATE_MONEY)) {
            item = ItemCreator.createGuiItem(Material.PAPER, ChatColor.WHITE + "Чек", ChatColor.YELLOW + "℗" + ChatColor.WHITE + amount, ChatColor.WHITE + "от " + fromPlayerDisplay);
            Balance.changePlayerBalance(fromPlayer, 0, -amount);
        }
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.getPersistentDataContainer().set(Moneykey, PersistentDataType.INTEGER, amount);
        itemMeta.getPersistentDataContainer().set(MoneyType, PersistentDataType.STRING, type.getName());
        item.setItemMeta(itemMeta);
        return item;
    }
    public static void changeCheck(@NotNull ItemStack check, MoneyType type, Integer amount, String fromPlayer) {
        ItemMeta meta = check.getItemMeta();
        if(type != null) {
            meta.getPersistentDataContainer().set(MoneyType, PersistentDataType.STRING, type.getName());
        }
        if(amount != null) {
            meta.getPersistentDataContainer().set(Moneykey, PersistentDataType.INTEGER, amount);
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
