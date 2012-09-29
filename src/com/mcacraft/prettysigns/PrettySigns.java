package com.mcacraft.prettysigns;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author kennywilliams
 */
public class PrettySigns extends JavaPlugin implements Listener
{
    //Declarations
    Logger log = Logger.getLogger("Minecraft");
    private BlockListener listener = new BlockListener();
    private PluginManager pm = Bukkit.getServer().getPluginManager();
    public static boolean update = false;
    public static String name = "";
    public static long size = 0;
    ArrayList<String> a = new ArrayList();
    
    public void onEnable()
    {
        //Register commands and events
        registerEvents();
        //Create defualt config file if it does not exist
        createConfig();
        PluginDescriptionFile pdf = this.getDescription();
        
        //Check for out of date config file
        if(!this.getConfig().getString("config-version").equalsIgnoreCase("0.1")){
            log.warning("[PrettySigns] Incorrect config.yml version. Re-generate the file to continue.");
            log.info("[PrettySigns] Disabling PrettySigns ...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        
        //Check for mcstats.org reporting
        if(this.getConfig().getBoolean("mcstats")){
            try {
                org.mcstats.Metrics metrics = new org.mcstats.Metrics(this);
                metrics.start();
                log.info("[PrettySigns] Info submitted to mcstats.org");
            } catch (IOException e) {
                log.severe("PrettySigns failed to submit stats...");
            }
        }else{
            log.info("[PrettySigns] Data was not sent to mcstats.org");
        }
        
        //Check for auto-update feature
        if(this.getConfig().getBoolean("auto-update")){
            log.info("[PrettySigns] Self-Update enabled. Will check for updates.");
            net.h31ix.updater.Updater updater = new net.h31ix.updater.Updater(this, "prettysigns", this.getFile(), net.h31ix.updater.Updater.UpdateType.DEFAULT, false);
        }else{
            net.h31ix.updater.Updater updater = new net.h31ix.updater.Updater(this, "prettysigns", this.getFile(), net.h31ix.updater.Updater.UpdateType.NO_DOWNLOAD, false);
            if(updater.getResult() == net.h31ix.updater.Updater.UpdateResult.UPDATE_AVAILABLE){
                name = updater.getLatestVersionString(); 
                size = updater.getFileSize();
                a.add(ChatColor.YELLOW+"An update is available: " + PrettySigns.name + "(" + PrettySigns.size + " bytes)");
                a.add(ChatColor.YELLOW+"Type "+ChatColor.WHITE+"/ps update"+ChatColor.YELLOW+" to update.");
            }
        }
        log.info("PrettySigns version "+pdf.getVersion()+" is now enabled.");
    }
    
    
    public void onDisable()
    {
        //Not much in here..
        PluginDescriptionFile pdf = this.getDescription();
        log.info("PrettySigns  version "+pdf.getVersion()+" is now disabled.");
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        //Message informing player about update when they join
        if(!player.hasPermission("prettysigns.update.info")){
            return;
        }
        if(this.getConfig().getBoolean("auto-update")){
            return;
        }
        if(a.size() > 0){
            player.sendMessage(a.get(0));
            player.sendMessage(a.get(1));
        }
        //String version = name.split(" ")[1].replaceAll("v", "");
    }
    
    public void registerEvents()
    {
        //registers the events
        pm.registerEvents(this.listener, this);
        pm.registerEvents(this, this);
        getCommand("ps").setExecutor(new CommandListener(this));
    }
    
    public void createConfig()
    {
        //Creates the config file ..
        File file = new File(getDataFolder()+File.separator+"config.yml"); 
        if(!file.exists()){
            log.info("[PrettySigns] Creating default config file ...");
            saveDefaultConfig();
            log.info("[PrettySigns] Config created successfully!");
        }

    }
    
    public void updatePlugin()
    {
        //This function is called from the CommandListener class. getFile() is a protected function.
        net.h31ix.updater.Updater updater = new net.h31ix.updater.Updater(this, "prettysigns", this.getFile(), net.h31ix.updater.Updater.UpdateType.NO_VERSION_CHECK, true);
    }
}
