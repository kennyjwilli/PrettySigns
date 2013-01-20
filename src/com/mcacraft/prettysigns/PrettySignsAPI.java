package com.mcacraft.prettysigns;

import org.bukkit.entity.Player;

/**
 *
 * @author Kenny
 */
public class PrettySignsAPI 
{
    private PrettySigns plugin;
    public ConfigAccessor warps;
    
    public PrettySignsAPI(PrettySigns instance)
    {
        this.plugin = instance;
        this.warps = new ConfigAccessor(plugin.getPlugin(), "warps.yml");
    }
    
    public void createWarp(String name, Player p)
    {
        Player player = p;
        
        int x = p.getLocation().getBlockX();
        int y = p.getLocation().getBlockY();
        int z = p.getLocation().getBlockZ();
        String worldName = player.getWorld().getName();
        
        this.warps.getConfig().set(name+".location", x+" "+y+" "+z);
        this.warps.getConfig().set(name+".world", worldName);
        this.warps.saveConfig();
    }
    
    public void deleteWarp(String name)
    {
        this.warps.getConfig().set(name, null);
        this.warps.saveConfig();
    }
    
    public ConfigAccessor getWarpConfig()
    {
        return this.warps;
    }
}
