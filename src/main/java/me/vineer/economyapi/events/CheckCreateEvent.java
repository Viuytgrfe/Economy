package me.vineer.economyapi.events;

import me.vineer.economyapi.items.ItemCreator;
import me.vineer.economyapi.money.Balance;
import me.vineer.economyapi.money.MoneyType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CheckCreateEvent extends Event implements Cancellable {
    private static final HandlerList handles = new HandlerList();

    Player whoCreated;
    String fromPlayerName;
    ItemStack check;
    double amount;
    MoneyType type;
    boolean cancelled = false;

    public ItemStack getCheck() {
        return check;
    }

    public Player getWhoUsed() {
        return whoCreated;
    }

    public String getFromPlayerName() {
        return fromPlayerName;
    }

    public void setFromPlayerName(String fromPlayerName) {
        this.fromPlayerName = fromPlayerName;
        Balance.changeCheck(check, null, null, fromPlayerName);
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
        Balance.changeCheck(check, null, amount, null);
    }

    public MoneyType getType() {
        return type;
    }

    public void setType(MoneyType type) {
        this.type = type;
        Balance.changeCheck(check, type, null, null);
    }


    public CheckCreateEvent(ItemStack check, Player whoCreated, String fromPlayerName, double amount, MoneyType type) {
        this.amount = amount;
        this.whoCreated = whoCreated;
        this.fromPlayerName = fromPlayerName;
        this.type = type;
        this.check = check;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handles;
    }

    public static HandlerList getHandlerList() {
        return handles;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
}
