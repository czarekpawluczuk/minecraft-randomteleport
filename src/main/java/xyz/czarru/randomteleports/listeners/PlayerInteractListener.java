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
        Bukkit.broadcastMessage("0");
        if(!clickedBlock.getState().getType().name().contains("BUTTON")) return;
        Bukkit.broadcastMessage("01");
        final Button btn = (Button) e.getClickedBlock().getState().getData();
        final Block relativeBlock = clickedBlock.getRelative(btn.getAttachedFace());
        if(!a.equals(Action.RIGHT_CLICK_BLOCK))return;
        Bukkit.broadcastMessage("1");
        final ConfigurationSection cfg = plugin.getConfig();
        if(!clickedBlock.getType().equals(Material.getMaterial(cfg.getString("config.blocks.button"))))return;
        Bukkit.broadcastMessage("2");
        final Location randomLoc = coordinateHelper.getRandomLocation(p.getWorld());
        Bukkit.broadcastMessage("02");
        Bukkit.broadcastMessage("RETURN MATERIAL: "+Material.getMaterial(cfg.getString("config.blocks.solo")));
        Bukkit.broadcastMessage("RETURN RELATIVE: "+relativeBlock.getType());
        if(relativeBlock.getType().equals(Material.getMaterial(cfg.getString("config.blocks.solo")))){
            Bukkit.broadcastMessage("3");
            p.teleport(randomLoc);
            p.sendMessage(chatHelper.color(cfg.getString("config.messages.teleport")));
        }else if(relativeBlock.getType().equals(Material.getMaterial(cfg.getString("config.blocks.group")))){
            ArrayList<Player> nearbyPlayersList = coordinateHelper.getNearbyPlayers(relativeBlock.getLocation(), p);
            if(nearbyPlayersList.isEmpty()) {
                p.sendMessage(chatHelper.color(cfg.getString("config.messages.emptyList")));
                return;
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
