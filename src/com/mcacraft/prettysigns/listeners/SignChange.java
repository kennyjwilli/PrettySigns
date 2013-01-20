package com.mcacraft.prettysigns.listeners;

import com.mcacraft.prettysigns.PrettySigns;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 *
 * @author kennywilliams
 */
public class SignChange implements Listener
{
    private PrettySigns plugin;
    
    public SignChange(PrettySigns instance)
    {
        this.plugin = instance;
    }
    
    @EventHandler(priority = EventPriority.LOW)
    public void onSignChange(SignChangeEvent event)
    {
        Player player = event.getPlayer();
        
        /*
         * Warp sign creation
         */
        if(player.hasPermission("prettysigns.warp.create"))
        {
            if(event.getLine(0).equalsIgnoreCase("PSWarp"))
            {
                for(String s : plugin.getAPI().getWarpConfig().getConfig().getKeys(false))
                {
                    if(s.equalsIgnoreCase(event.getLine(1)))
                    {
                        if(event.getLine(2).equalsIgnoreCase(""))
                        {
                            event.setLine(0, ChatColor.BLUE+"[PSWarp]");
                            event.setLine(1, s);
                            event.setLine(2, "");
                            event.setLine(3, "");
                            return;
                        }else
                        {
                            try
                            {
                                double price = Double.parseDouble(event.getLine(2));

                                event.setLine(0, ChatColor.BLUE+"[PSWarp]");
                                event.setLine(1, s);
                                event.setLine(2, ChatColor.ITALIC+""+price);
                                event.setLine(3, "");
                                return;
                            }catch(Exception e)
                            {
                                event.setLine(0, "");
                                event.setLine(1, ChatColor.RED+"ERROR:");
                                event.setLine(2, ChatColor.RED+"Invalid Price");
                                event.setLine(3, "");
                                return;
                            }
                        }
                    }
                }
                
                event.setLine(0, "");
                event.setLine(1, ChatColor.RED+"ERROR:");
                event.setLine(2, ChatColor.RED+"Invalid Warp");
                event.setLine(3, "");
                return;
            }
        }
        
        if(player.hasPermission("prettysigns.loc.create"))
        {
            if(event.getLine(0).equalsIgnoreCase("psloc"))
            {
                if(event.getLine(2).equalsIgnoreCase(""))
                {
                    String[] locAry = event.getLine(1).split(" ");
                    //Check for a location that does not have three coordiantes
                    if(locAry.length != 3)
                    {
                        event.setLine(0, "");
                        event.setLine(1, ChatColor.RED+"ERROR:");
                        event.setLine(2, ChatColor.RED+"Invalid Location");
                        event.setLine(3, "");
                        return;
                    }
                    //Check for a location not using numbers
                    for(String s : locAry)
                    {
                        try
                        {
                            int i1 = Integer.parseInt(s);
                        }catch(Exception e)
                        {
                            event.setLine(0, "");
                            event.setLine(1, ChatColor.RED+"ERROR:");
                            event.setLine(2, ChatColor.RED+"Invalid Location");
                            event.setLine(3, "");
                        return;
                        }
                    }
                    event.setLine(0, ChatColor.BLUE+"[PSLoc]");
                    event.setLine(1, event.getLine(1));
                    event.setLine(2, "");
                    event.setLine(3, "");
                    return;
                }else
                {
                    String[] locAry = event.getLine(1).split(" ");
                    //Check for a location that does not have three coordiantes
                    if(locAry.length != 3)
                    {
                        event.setLine(0, "");
                        event.setLine(1, ChatColor.RED+"ERROR:");
                        event.setLine(2, ChatColor.RED+"Invalid Location");
                        event.setLine(3, "");
                        return;
                    }
                    //Check for a location not using numbers
                    for(String s : locAry)
                    {
                        try
                        {
                            int i1 = Integer.parseInt(s);
                        }catch(Exception e)
                        {
                            event.setLine(0, "");
                            event.setLine(1, ChatColor.RED+"ERROR:");
                            event.setLine(2, ChatColor.RED+"Invalid Location");
                            event.setLine(3, "");
                        return;
                        }
                    }
                    
                    try
                    {
                        double price = Double.parseDouble(event.getLine(2));

                        event.setLine(0, ChatColor.BLUE+"[PSLoc]");
                        event.setLine(1, event.getLine(1));
                        event.setLine(2, ChatColor.ITALIC+""+price);
                        event.setLine(3, "");
                        return;
                    }catch(Exception e)
                    {
                        event.setLine(0, "");
                        event.setLine(1, ChatColor.RED+"ERROR:");
                        event.setLine(2, ChatColor.RED+"Invalid Price");
                        event.setLine(3, "");
                        return;
                    }
                }
            }
        }
        
        /*
         * Translate color codes
         */
        int counter = 0;
        
        if(player.hasPermission("prettysigns.create"))
        {
            while(counter < 4)
            {
                if(event.getLine(counter).contains("&"))
                {
                    event.setLine(counter, ChatColor.translateAlternateColorCodes('&', event.getLine(counter)));
                }
                counter++;
            }
        }
        
    }
}
