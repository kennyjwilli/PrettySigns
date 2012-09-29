package com.mcacraft.prettysigns;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

/**
 *
 * @author kennywilliams
 */
class BlockListener implements Listener{
    
    @EventHandler(priority = EventPriority.LOW)
    public void onSignChange(SignChangeEvent event){
        Player player = event.getPlayer();
        int counter = 0;
        if(player.hasPermission("prettysigns.create")){
            while(counter < 4){
                if(event.getLine(counter).contains("&")){
                    event.setLine(counter, ChatColor.translateAlternateColorCodes('&', event.getLine(counter)));
                }
                counter++;
            }
        }
    }
}
