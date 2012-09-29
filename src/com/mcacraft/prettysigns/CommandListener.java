package com.mcacraft.prettysigns;

import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author kennywilliams
 */
public class CommandListener implements CommandExecutor{
    private Logger log = Logger.getLogger("Minecraft");
    PrettySigns plugin;
    
    public CommandListener(PrettySigns instance){
        plugin = instance;
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args){
        Player player;
        ChatColor gold = ChatColor.GOLD;
        ChatColor blue = ChatColor.BLUE;
        
        if(sender instanceof Player){
           player = (Player) sender; 
        }else{
            log.info("Error: This can only be used by players at this time.");
            return true;
        }
        
        if(lbl.equalsIgnoreCase("ps")){
            if(args.length == 0){
                player.sendMessage(ChatColor.AQUA+"Type "+ChatColor.YELLOW+"/ps help"+ChatColor.AQUA+" for a list of commands.");
                return true;
            }
            if(args.length == 1){
                if(args[0].equalsIgnoreCase("help")){
                    if(!player.hasPermission("prettysigns.help")){
                        player.sendMessage(ChatColor.RED+"You don't have permission.");
                        return true;
                    }
                    player.sendMessage(ChatColor.GREEN+"==========PrettySigns Help==========");
                    player.sendMessage(gold+"/ps reload"+blue+" Reloads the config file");
                    player.sendMessage(gold+"/ps version"+blue+" Displays the current version of PrettySigns");
                    player.sendMessage(gold+"/ps update"+blue+" Updates the plugin");
                    log.info("[PLAYER_COMMAND] "+player.getName()+": /ps help");
                    return true;
                }else if(args[0].equalsIgnoreCase("reload")){
                    if(!player.hasPermission("prettysigns.reload")){
                        player.sendMessage(ChatColor.RED+"You don't have permission.");
                        return true;
                    }
                    plugin.reloadConfig();
                    player.sendMessage(ChatColor.GREEN+"PrettySigns version "+plugin.getDescription().getVersion()+" reloaded successfully!");
                    log.info("[PLAYER_COMMAND] "+player.getName()+": /ps reload");
                    return true;
                }else if(args[0].equalsIgnoreCase("version")){
                    if(!player.hasPermission("PrettySigns.version")){
                        player.sendMessage(ChatColor.RED+"You don't have permission.");
                        return true;
                    }
                    player.sendMessage(ChatColor.ITALIC+"PrettySigns version "+plugin.getDescription().getVersion());
                    log.info("[PLAYER_COMMAND] "+player.getName()+": /ps version");
                }else if(args[0].equalsIgnoreCase("update")){
                    if(plugin.getConfig().getBoolean("auto-update")){
                        player.sendMessage(ChatColor.RED+"Error: Self-Update must be set to false to use this command.");
                        return true;
                    }
                    log.info("[PLAYER_COMMAND] "+player.getName()+": /vx update");
                    plugin.updatePlugin();
                    player.sendMessage(ChatColor.GREEN+"Success! Updates will apply after a server restart.");
                }else{
                    player.sendMessage(ChatColor.RED+"Error: Unrecognized argument");
                    return true;
                }
            }
            if(args.length > 1){
                player.sendMessage(ChatColor.RED+"Error: Too many arguments");
                return true;
            }
        }
        return false;
    }
}
