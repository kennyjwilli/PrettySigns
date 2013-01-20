package com.mcacraft.prettysigns.listeners;

import com.mcacraft.prettysigns.PrettySigns;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 *
 * @author Kenny
 */
public class PlayerDeathListener implements Listener
{
    private PrettySigns plugin;
    
    public PlayerDeathListener(PrettySigns instance)
    {
        this.plugin = instance;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        Player player = event.getEntity();
        
        if(plugin.getConfig().getBoolean("sign-on-death.enable"))
        {
            Location loc = player.getLocation();
            loc.getBlock().setType(Material.SIGN_POST);
            Block block = loc.getBlock();
            Sign sign = (Sign) block.getState();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            try
            {
                for(int i = 0; i < plugin.getConfig().getStringList("sign-on-death.text").size(); i++)
                {
                    String replaceAll = plugin.getConfig().getStringList("sign-on-death.text").get(i).replaceAll("%player%", player.getName()).replaceAll("%date%", dateFormat.format(date));
                    sign.setLine(i, ChatColor.translateAlternateColorCodes('&', replaceAll));
                }
            }catch(Exception e)
            {
                for(int i = 0; i < 4; i++)
                {
                    String replaceAll = plugin.getConfig().getStringList("sign-on-death.text").get(i).replaceAll("%player%", player.getName()).replaceAll("%date%", dateFormat.format(date));
                    sign.setLine(i, ChatColor.translateAlternateColorCodes('&', replaceAll));
                }
            }
            
            sign.update();
        }
    }
    
}
