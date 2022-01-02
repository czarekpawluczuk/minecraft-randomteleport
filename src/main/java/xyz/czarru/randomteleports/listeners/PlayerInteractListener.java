package xyz.czarru.randomteleports.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Button;
import xyz.czarru.randomteleports.Main;
import xyz.czarru.randomteleports.helpers.ColorHelper;
import xyz.czarru.randomteleports.helpers.CoordinateHelper;

import java.util.ArrayList;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        final Player p = e.getPlayer();
        final Action a = e.getAction();
        final Button btn = (Button)e.getClickedBlock().getState().getData();
        final Block clickedBlock = e.getClickedBlock();
        final Block backBlock = clickedBlock.getRelative(btn.getAttachedFace());
        if(e.isCancelled()){
            return;
        }
        if(clickedBlock.getType()== Material.STONE_BUTTON) {
            if (a == Action.RIGHT_CLICK_BLOCK) {
                Location randomLocation = CoordinateHelper.getRandomLocation(p.getWorld());
                if (backBlock.getType() == Material.SPONGE) {
                    p.teleport(randomLocation);
                    p.sendMessage(ColorHelper.fix("&aZostałeś/aś przeteleportowany w losowe koordynaty!"));
                } else if (backBlock.getType() == Material.GOLD_BLOCK) {
                    ArrayList<Player> listOfNearby = CoordinateHelper.getNearbyPlayers(backBlock.getLocation(), p);
                    if(listOfNearby.size()==0){
                        p.sendMessage(ColorHelper.fix("&cZaczekaj na przeciwnika, z którym będziesz mógł się teleportować!"));
                        return;
                    }
                    Player opponent = listOfNearby.get(Main.r.nextInt());
                    p.teleport(randomLocation);
                    opponent.teleport(randomLocation);
                    p.sendMessage(ColorHelper.fix("&aZostałeś/aś przeteleportowany w losowe koordynaty!"));
                    opponent.sendMessage(ColorHelper.fix("&aZostałeś/aś przeteleportowany w losowe koordynaty!"));
                }
            }
        }
    }
}
