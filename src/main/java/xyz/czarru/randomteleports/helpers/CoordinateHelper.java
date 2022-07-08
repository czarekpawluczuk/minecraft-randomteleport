package xyz.czarru.randomteleports.helpers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import xyz.czarru.randomteleports.RandomTeleportPlugin;

import java.util.ArrayList;
import java.util.Random;

public class CoordinateHelper {

    private Random random = new Random();
    private RandomTeleportPlugin plugin;

    public CoordinateHelper(RandomTeleportPlugin plugin){
        this.plugin = plugin;
    }

    public int getRandomX() {
        int Xmax = plugin.getConfig().getInt("config.coordinates.maxX");
        int Xmin = plugin.getConfig().getInt("config.coordinates.minX");
        return random.nextInt(Xmax - Xmin) + Xmin;
    }

    public int getRandomZ() {
        int Zmax = plugin.getConfig().getInt("config.coordinates.maxZ");
        int Zmin = plugin.getConfig().getInt("config.coordinates.minZ");
        return random.nextInt(Zmax - Zmin) + Zmin;
    }

    public Location getRandomLocation(final World w) {
        boolean safe = false;
        Location l;
        do {
            l = w.getHighestBlockAt(getRandomX(), getRandomZ()).getLocation();
            for (int i = 1; i <= 5; ++i) {
                if (l.subtract(0.0, 1.0, 0.0).getBlock().getType().equals(Material.AIR)) {
                    safe = true;
                    l.add(0.0, i, 0.0);
                }
            }
        } while (!safe);
        return l;
    }

    public ArrayList<Player> getNearbyPlayers(final Location loc, final Player p) {
        final ArrayList<Player> nearbyPlayersList = new ArrayList<>();
        final double range = 3.0;
        for (final Entity entity : loc.getWorld().getNearbyEntities(loc, range, range, range)) {
            if (entity instanceof Player && !entity.equals(p)) {
                if(p.getLocation().getBlock().getType().equals(Material.getMaterial(plugin.getConfig().getString("config.blocks.plate")))) {
                    nearbyPlayersList.add((Player)entity);
                }
            }
        }
        return nearbyPlayersList;
    }
}
