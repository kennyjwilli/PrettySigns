package com.mcacraft.prettysigns.commands;

import com.mcacraft.prettysigns.PrettySigns;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Kenny
 */
public class PSCommand implements CommandExecutor
{
    private PrettySigns plugin;
    
    public PSCommand(PrettySigns instance)
    {
        this.plugin = instance;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args)
    {
        Player player;
        ChatColor gold = ChatColor.GOLD;
        ChatColor blue = ChatColor.BLUE;
        
        if(sender instanceof Player){
           player = (Player) sender; 
        }else{
            Bukkit.getLogger().log(Level.INFO, "Error: This can only be used by players at this time.");
            return true;
        }
        
        if(lbl.equalsIgnoreCase("ps"))
        {
            if(args.length == 0)
            {
                player.sendMessage(ChatColor.AQUA+"Type "+ChatColor.YELLOW+"/ps help"+ChatColor.AQUA+" for a list of commands.");
                return true;
            }
            if(args.length == 1)
            {
                if(args[0].equalsIgnoreCase("help"))
                {
                    if(!player.hasPermission("prettysigns.help"))
                    {
                        player.sendMessage(ChatColor.RED+"You don't have permission.");
                        return true;
                    }
                    player.sendMessage(ChatColor.GREEN+"==========PrettySigns Help==========");
                    player.sendMessage(gold+"/ps reload"+blue+" Reloads the config file");
                    player.sendMessage(gold+"/ps version"+blue+" Displays the current version of PrettySigns");
                    player.sendMessage(gold+"/ps update"+blue+" Updates the plugin");
                    player.sendMessage(gold+"/ps setwarp"+blue+" Sets a warp");
                    player.sendMessage(gold+"/ps deletewarp"+blue+" Deletes a warp");
                    player.sendMessage(gold+"/ps warplist"+blue+" Lists all avaliable warps");
                    Bukkit.getLogger().log(Level.INFO, "[PLAYER_COMMAND] "+player.getName()+": /ps help");
                    return true;
                }else if(args[0].equalsIgnoreCase("reload"))
                {
                    if(!player.hasPermission("prettysigns.reload"))
                    {
                        player.sendMessage(ChatColor.RED+"You don't have permission.");
                        return true;
                    }
                    plugin.reloadConfig();
                    
                    player.sendMessage(ChatColor.GREEN+"PrettySigns version "+plugin.getDescription().getVersion()+" reloaded successfully!");
                    Bukkit.getLogger().log(Level.INFO, "[PLAYER_COMMAND] "+player.getName()+": /ps reload");
                    return true;
                }else if(args[0].equalsIgnoreCase("version"))
                {
                    if(!player.hasPermission("PrettySigns.version")){
                        player.sendMessage(ChatColor.RED+"You don't have permission.");
                        return true;
                    }
                    player.sendMessage(ChatColor.ITALIC+"PrettySigns version "+plugin.getDescription().getVersion());
                    Bukkit.getLogger().log(Level.INFO, "[PLAYER_COMMAND] "+player.getName()+": /ps version");
                }else if(args[0].equalsIgnoreCase("update"))
                {
                    if(plugin.getConfig().getBoolean("auto-update")){
                        player.sendMessage(ChatColor.RED+"Error: Self-Update must be set to false to use this command.");
                        return true;
                    }
                    Bukkit.getLogger().log(Level.INFO, "[PLAYER_COMMAND] "+player.getName()+": /ps update");
                    plugin.updatePlugin();
                    player.sendMessage(ChatColor.GREEN+"Success! Updates will apply after a server restart.");
                }else if(args[0].equalsIgnoreCase("setwarp") || args[0].equalsIgnoreCase("sw"))
                {
                    if(!player.hasPermission("prettysigns.warp.setwarp"))
                    {
                        player.sendMessage(ChatColor.RED+"You don't have permission.");
                        return true;
                    }
                    player.sendMessage(ChatColor.RED+"Usage: /ps setwarp <warp>");
                    return true;
                }else if(args[0].equalsIgnoreCase("deletewarp") || args[0].equalsIgnoreCase("dw"))
                {
                    if(!player.hasPermission("prettysigns.warp.deletewarp"))
                    {
                        player.sendMessage(ChatColor.RED+"You don't have permission.");
                        return true;
                    }
                    player.sendMessage(ChatColor.RED+"Usage: /ps deletewarp <warp>");
                    return true;
                }else if(args[0].equalsIgnoreCase("warplist") || args[0].equalsIgnoreCase("wl"))
                {
                    if(!player.hasPermission("prettysigns.warp.list"))
                    {
                        player.sendMessage(ChatColor.RED+"You don't have permission.");
                        return true;
                    }
                    String warpsList = "";
                    boolean first = true;
                    int i = 1;
                    for(String str : plugin.getAPI().getWarpConfig().getConfig().getKeys(false))
                    {
                        if(!first)
                        {
                            warpsList += ChatColor.WHITE+", ";
                        }else
                        {
                            first = false;
                        }
                        
                        if(i % 2 == 0)
                        {
                            warpsList += ChatColor.AQUA+str;
                        }else
                        {
                            warpsList += ChatColor.GOLD+str;
                        }
                        i++;
                    }
                    player.sendMessage(ChatColor.GREEN+"==========PrettySigns Warp List==========");
                    player.sendMessage(warpsList);
                    return true;
                }else
                {
                    player.sendMessage(ChatColor.RED+"Error: Unrecognized argument");
                    return true;
                }
            }
            if(args.length == 2)
            {
                if(args[0].equalsIgnoreCase("setwarp") || args[0].equalsIgnoreCase("sw"))
                {
                    if(!player.hasPermission("prettysigns.warp.set"))
                    {
                        player.sendMessage(ChatColor.RED+"You don't have permission.");
                        return true;
                    }
                    try
                    {
                        plugin.getAPI().createWarp(args[1], player);
                        player.sendMessage(ChatColor.BLUE+"Warp "+ChatColor.GREEN+"created"+ChatColor.BLUE+" successfully!");
                    }catch(Exception e)
                    {
                        player.sendMessage(ChatColor.RED+"An error has occured. Check server logs.");
                    }
                }else if(args[0].equalsIgnoreCase("deletewarp") || args[0].equalsIgnoreCase("dw"))
                {
                    try
                    {
                        plugin.getAPI().deleteWarp(args[1]);
                        player.sendMessage(ChatColor.BLUE+"Warp "+ChatColor.RED+"deleted"+ChatColor.BLUE+" successfully!");
                    }catch(Exception e)
                    {
                        player.sendMessage(ChatColor.RED+"An error has occured. Check server logs.");
                    }
                    return true;
                }else
                {
                    player.sendMessage(ChatColor.RED+"Error: Unrecognized argument");
                    return true;
                }
            }
        }
        return false;
    }
}
