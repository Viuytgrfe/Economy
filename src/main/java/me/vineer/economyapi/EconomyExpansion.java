package me.vineer.economyapi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.vineer.economyapi.money.Balance;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EconomyExpansion extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "economy";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Vineer";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if(params.equals("balance")) {
            return String.valueOf(Balance.getPlayerBalance(player.getName()).getMoney());
        } else if(params.equals("donate_balance")) {
            return String.valueOf(Balance.getPlayerBalance(player.getName()).getDonateMoney());
        }
        return null;
    }
}
