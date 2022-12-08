package me.vineer.economyapi.listeners;

import me.vineer.economyapi.events.CheckCreateEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EconomyListener implements Listener {
    @EventHandler
    public void onCheckCreate(CheckCreateEvent event) {
        if(event.getAmount() > 50) {
            event.setCancelled(true);
            event.getWhoUsed().sendMessage("очень большой чек!");
        } else {
            event.getWhoUsed().sendMessage("отличный чек!");
        }
    }
}
