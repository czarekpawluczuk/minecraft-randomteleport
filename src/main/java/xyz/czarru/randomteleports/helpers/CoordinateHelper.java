package xyz.czarru.randomteleports.helpers;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import xyz.czarru.randomteleports.Main;

import java.util.ArrayList;

public class CoordinateHelper {

    public static int Xmax = 100;
    public static int Xmin = 100;
    public static int Zmax = 100;
    public static int Zmin = 100;


    public static int getRandomX() {
        return Main.r.nextInt(Xmax - Xmin) + Xmin;
    }

    public static int getRandomZ() {
        return Main.r.nextInt(Zmax - Zmin) + Zmin;
    }

    public static Location getRandomLocation(final World w) {
        boolean safe = false;
        Location l;
        do {
            l = w.getHighestBlockAt(getRandomX(), getRandomZ()).getLocation();
            for (int i = 1; i <= 5; ++i) {
                if (l.subtract(0.0, 1.0, 0.0).getBlock().getType() != Material.AIR) {
                    safe = true;
                    l.add(0.0, i, 0.0);
                }
            }
        } while (!safe);
        return l;
    }

    public static ArrayList<Player> getNearbyPlayers(final Location l, final Player p) {
        final ArrayList<Player> nearby = new ArrayList<Player>();
        final double range = 3.0;
        for (final Entity e : l.getWorld().getNearbyEntities(l, range, range, range)) {
            if (e instanceof Player && !((Player)e).equals(p)) {
                if(p.getLocation().getBlock().getType() == Material.IRON_PLATE) {
                    nearby.add((Player) e);
                }
            }
        }
        return nearby;
    }
}
