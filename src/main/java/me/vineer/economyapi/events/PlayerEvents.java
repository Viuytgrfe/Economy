package me.vineer.economyapi.events;

import me.vineer.economyapi.money.Balance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
public class PlayerEvents implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Balance balance;
        Balance.setPlayerBalance(player, 200, 5);
        balance = Balance.getPlayerBalance(player);
        System.out.println(balance.getMember());
    }
}
