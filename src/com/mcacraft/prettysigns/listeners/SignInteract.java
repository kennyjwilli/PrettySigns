package com.mcacraft.prettysigns.listeners;

import com.mcacraft.prettysigns.PrettySigns;
import java.util.logging.Level;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 *
 * @author Kenny
 */
public class SignInteract implements Listener 
{
    private static Economy economy = null;
    private PrettySigns plugin;
    
    public SignInteract(PrettySigns instance)
    {
        this.plugin = instance;
    }
    
    private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }
    
    @EventHandler
    public void onSignInteract(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK)
        {
            Block block = event.getClickedBlock();
            
            if(block.getType() == Material.SIGN ||block.getType() == Material.SIGN_POST)
            {
                Sign sign = (Sign) block.getState();
                
                if(sign.getLine(0).equalsIgnoreCase(ChatColor.BLUE+"[PSWarp]"))
                {
                    if(player.hasPermission("prettysigns.warp.use"))
                    {
                        if(sign.getLine(2).equalsIgnoreCase(""))
                        {
                            try
                            {
                                World world = Bukkit.getWorld(plugin.getAPI().getWarpConfig().getConfig().getString(sign.getLine(1)+".world"));
                                String[] locAry = plugin.getAPI().getWarpConfig().getConfig().getString(sign.getLine(1)+".location").split(" ");
                                int x = Integer.parseInt(locAry[0]);
                                int y = Integer.parseInt(locAry[1]);
                                int z = Integer.parseInt(locAry[2]);
                                Location loc = player.getLocation();
                                loc.setX(x);
                                loc.setY(y);
                                loc.setZ(z);
                                loc.setWorld(world);
                                player.teleport(loc);
                            }catch(Exception e)
                            {
                                player.sendMessage(ChatColor.RED+"Uh oh.. PrettySigns encountered a problem! Ensure the warp sign was created correctly and try again.");
                            }
                        }else
                        {
                            try
                            {
                                setupEconomy();
                                economy.withdrawPlayer(player.getName(), Double.parseDouble(sign.getLine(2).replaceAll(ChatColor.ITALIC+"", "")));

                                World world = Bukkit.getWorld(plugin.getAPI().getWarpConfig().getConfig().getString(sign.getLine(1)+".world"));
                                String[] locAry = plugin.getAPI().getWarpConfig().getConfig().getString(sign.getLine(1)+".location").split(" ");
                                int x = Integer.parseInt(locAry[0]);
                                int y = Integer.parseInt(locAry[1]);
                                int z = Integer.parseInt(locAry[2]);
                                Location loc = player.getLocation();
                                loc.setX(x);
                                loc.setY(y);
                                loc.setZ(z);
                                loc.setWorld(world);
                                player.teleport(loc);

                                player.sendMessage(ChatColor.BLUE+"You have been charged "+ChatColor.YELLOW+sign.getLine(2)+ChatColor.BLUE+".");
                                player.sendMessage(ChatColor.BLUE+"Current Balance: "+ChatColor.YELLOW+economy.getBalance(player.getName()));
                            }catch(Exception e)
                            {
                                player.sendMessage(ChatColor.RED+"Uh oh.. PrettySigns encountered a problem! Ensure the warp sign was created correctly and try again.");
                            }
                        }
                    }
                }else if(sign.getLine(0).equalsIgnoreCase(ChatColor.BLUE+"[PSLoc]"))
                {
                    if(player.hasPermission("prettysigns.loc.use"))
                    {
                        if(sign.getLine(2).equalsIgnoreCase(""))
                        {
                            try
                            {
                                Location loc = player.getLocation();
                                String[] locAry = sign.getLine(1).split(" ");
                                loc.setX(Integer.parseInt(locAry[0]));
                                loc.setY(Integer.parseInt(locAry[1]));
                                loc.setZ(Integer.parseInt(locAry[2]));
                                player.teleport(loc);
                                return;
                            }catch(Exception e)
                            {
                                player.sendMessage(ChatColor.RED+"Uh oh.. PrettySigns encountered a problem! Ensure the location teleport sign was created correctly and try again.");
                            }
                            
                        }else
                        {
                            try
                            {
                                setupEconomy();
                                economy.withdrawPlayer(player.getName(), Double.parseDouble(sign.getLine(2).replaceAll(ChatColor.ITALIC+"", "")));

                                Location loc = player.getLocation();
                                String[] locAry = sign.getLine(1).split(" ");
                                loc.setX(Integer.parseInt(locAry[0]));
                                loc.setY(Integer.parseInt(locAry[1]));
                                loc.setZ(Integer.parseInt(locAry[2]));
                                player.teleport(loc);

                                player.sendMessage(ChatColor.BLUE+"You have been charged "+ChatColor.YELLOW+sign.getLine(2)+ChatColor.BLUE+".");
                                player.sendMessage(ChatColor.BLUE+"Current Balance: "+ChatColor.YELLOW+economy.getBalance(player.getName()));
                            }catch(Exception e)
                            {
                                player.sendMessage(ChatColor.RED+"Uh oh.. PrettySigns encountered a problem! Ensure the location teleport sign was created correctly and try again.");
                            }
                        }
                    }
                }
            }
        }
    }
    
}
