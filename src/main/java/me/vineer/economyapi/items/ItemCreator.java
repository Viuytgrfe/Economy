package me.vineer.economyapi.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ItemCreator {
    public static ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        if(!name.equals("default"))meta.setDisplayName(name);
        List<String> list = new LinkedList<String>(Arrays.asList(lore));
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i) == null || list.get(i).equals("")) {
                list.remove(i);
                i--;
            }
        }
        meta.setLore(list);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack modifyGuiItem(@NotNull ItemStack item, String name, String... lore) {
        ItemMeta meta = item.getItemMeta();

        if(meta != null) {
            if(name != null) {
                meta.setDisplayName(name);
            }
            List<String> list = new LinkedList<>(Arrays.asList(lore));
            for (int i = 0; i < list.size(); i++) {
                if(list.get(i) == null) {
                    list.remove(i);
                    i--;
                } else if(list.get(i).equals("default") && meta.getLore() != null && meta.getLore().size() > i) {
                    list.set(i, meta.getLore().get(i));
                }
            }
            meta.setLore(list);
            item.setItemMeta(meta);
        }
        return item;
    }
}
