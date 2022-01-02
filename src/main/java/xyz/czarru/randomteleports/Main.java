package xyz.czarru.randomteleports;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.czarru.randomteleports.listeners.PlayerInteractListener;

import java.util.Random;

public class Main extends JavaPlugin {

    public static Random r = new Random();

    public void onEnable(){
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
    }
}
