package xyz.czarru.randomteleports.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Button;
import xyz.czarru.randomteleports.RandomTeleportPlugin;
import xyz.czarru.randomteleports.helpers.ChatHelper;
import xyz.czarru.randomteleports.helpers.CoordinateHelper;
import java.util.ArrayList;

public class PlayerInteractListener implements Listener {

    public RandomTeleportPlugin plugin;
    private CoordinateHelper coordinateHelper;
    private ChatHelper chatHelper = new ChatHelper();

    public PlayerInteractListener(RandomTeleportPlugin plugin, CoordinateHelper coordinateHelper){
        this.plugin = plugin;
        this.coordinateHelper = coordinateHelper;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void interactButton(PlayerInteractEvent e){
        final Player p = e.getPlayer();
        final Action a = e.getAction();
        final Block clickedBlock = e.getClickedBlock();
        if(e.isCancelled())return;
        if(clickedBlock==null)return;
        if(!clickedBlock.getState().getType().name().contains("BUTTON")) return;
        final Button btn = (Button) e.getClickedBlock().getState().getData();
        final Block relativeBlock = clickedBlock.getRelative(btn.getAttachedFace());
        if(!a.equals(Action.RIGHT_CLICK_BLOCK))return;
        final ConfigurationSection cfg = plugin.getConfig();
        if(!clickedBlock.getType().equals(Material.getMaterial(cfg.getString("config.blocks.button"))))return;
        final Location randomLoc = coordinateHelper.getRandomLocation(p.getWorld());
        if(relativeBlock.getType().equals(Material.getMaterial(cfg.getString("config.blocks.solo")))){
            p.teleport(randomLoc);
            p.sendMessage(chatHelper.color(cfg.getString("config.messages.teleport")));
        }else if(relativeBlock.getType().equals(Material.getMaterial(cfg.getString("config.blocks.group")))){
            ArrayList<Player> nearbyPlayersList = coordinateHelper.getNearbyPlayers(relativeBlock.getLocation(), p);
            if(p.getLocation().getBlock().getType().equals(Material.getMaterial(plugin.getConfig().getString("config.blocks.plate")))) {
                if (nearbyPlayersList.isEmpty()) {
                    p.sendMessage(chatHelper.color(cfg.getString("config.messages.emptyList")));
                }
                Player opponent = nearbyPlayersList
                        .stream()
                        .findFirst()
                        .get();
                opponent.teleport(randomLoc);
                p.teleport(randomLoc);
            }
        }
    }
}
