package xyz.czarru.randomteleports.helpers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;

public class CoordinateHelper {

    private Random random = new Random();
    private ConfigurationSection config = Bukkit.spigot().getConfig();

    private int Xmax = config.getInt("config.coordinates.maxX");
    private int Xmin = config.getInt("config.coordinates.minX");
    private int Zmax = config.getInt("config.coordinates.maxZ");
    private int Zmin = config.getInt("config.coordinates.minZ");


    public int getRandomX() {
        return random.nextInt(Xmax - Xmin) + Xmin;
    }

    public int getRandomZ() {
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
                if(p.getLocation().getBlock().getType().equals(Material.getMaterial(config.getString("config.blocks.plate")))) {
                    nearbyPlayersList.add((Player)entity);
                }
            }
        }
        return nearbyPlayersList;
    }
}
