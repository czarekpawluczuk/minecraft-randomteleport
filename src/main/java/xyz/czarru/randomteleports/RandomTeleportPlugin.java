package xyz.czarru.randomteleports;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.czarru.randomteleports.helpers.CoordinateHelper;
import xyz.czarru.randomteleports.listeners.PlayerInteractListener;

import java.util.Random;

public class RandomTeleportPlugin extends JavaPlugin {

    public RandomTeleportPlugin(){
    }

    public void onEnable(){
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this, new CoordinateHelper(this)), this);
    }
}
