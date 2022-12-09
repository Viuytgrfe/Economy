package me.vineer.economyapi.listeners;

import me.vineer.economyapi.money.Balance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;

public class EconomyListener implements Listener {
    @EventHandler(priority= EventPriority.HIGH)
    public void onUseCheck(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(Balance.MoneyType, PersistentDataType.STRING) != null) {
                String type = player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(Balance.MoneyType, PersistentDataType.STRING);
                int amount = player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(Balance.Moneykey, PersistentDataType.INTEGER);
                event.getPlayer().getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount()-1);
                if(type.equals("money")) Balance.changePlayerBalance(event.getPlayer().getName(), amount, 0);
                else Balance.changePlayerBalance(event.getPlayer().getName(), 0, amount);
            }
        }
    }
}
