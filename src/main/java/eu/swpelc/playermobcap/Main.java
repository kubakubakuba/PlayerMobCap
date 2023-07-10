package eu.swpelc.playermobcap;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Main extends JavaPlugin implements Listener {
    private int mobCap;
    private int maxDistance;

    @Override
    public void onEnable() {
        this.getConfig().addDefault("mobCap", 64);
        this.getConfig().addDefault("maxDistance", 128);

        this.getConfig().options().copyDefaults(true);
        saveConfig();

        mobCap = this.getConfig().getInt("mobCap");
        maxDistance = this.getConfig().getInt("maxDistance");

        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("mcpset")) {
            if (sender.hasPermission("mobcap.set")) {
                if (args.length == 1) {
                    try {
                        mobCap = Integer.parseInt(args[0]);
                        this.getConfig().set("mobCap", mobCap); // save mobCap to config
                        saveConfig();
                        sender.sendMessage("Mob cap set to " + mobCap);
                    } catch (NumberFormatException e) {
                        sender.sendMessage("Invalid number format!");
                    }
                } else {
                    sender.sendMessage("Usage: /mcpset <number>");
                }
            } else {
                sender.sendMessage("You don't have permission to use this command.");
            }
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("mcpdist")) {
            if (sender.hasPermission("mobcap.dist")) {
                if (args.length == 1) {
                    try {
                        maxDistance = Integer.parseInt(args[0]);
                        this.getConfig().set("maxDistance", maxDistance); // save maxDistance to config
                        saveConfig();
                        sender.sendMessage("Search distance set to " + maxDistance);
                    } catch (NumberFormatException e) {
                        sender.sendMessage("Invalid number format!");
                    }
                } else {
                    sender.sendMessage("Usage: /mcpdist <number>");
                }
            } else {
                sender.sendMessage("You don't have permission to use this command.");
            }
            return true;
        }
        return false;
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getEntity() instanceof Monster) { //if entity is monster
            if(!canSpawn(event.getLocation(), maxDistance)) {
                event.setCancelled(true);
            }
        }
    }

    private int getNumOfSurroundingEnemies(Player player, double searchDistance){
        int numEnemies = 0;

        List<Entity> nearbyEntities = player.getNearbyEntities(searchDistance, searchDistance, searchDistance);
        for(Entity e : nearbyEntities){
            if(! (e instanceof  Monster) ) continue;

            ++numEnemies;
        }

        return numEnemies;
    }

    private boolean canSpawn(Location location, double searchDistance){
        boolean res = false;

        List<Entity> nearbyEntities = (List<Entity>) location.getNearbyEntities(searchDistance, searchDistance, searchDistance);

        for(Entity e : nearbyEntities){
            if(!(e instanceof Player)) continue;

            if(getNumOfSurroundingEnemies((Player) e, searchDistance) >= this.mobCap){
                continue;
            }

            res = true;
            break;
        }

        return res;
    }
}
