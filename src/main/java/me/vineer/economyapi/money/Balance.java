package me.vineer.economyapi.money;

import me.clip.placeholderapi.PlaceholderAPI;
import me.vineer.economyapi.database.Database;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Balance {
    private int money;
    private int donateMoney;
    private String member;

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
        if(!hasBalance(player)) {
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
}
