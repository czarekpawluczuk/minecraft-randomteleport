package xyz.czarru.randomteleports;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.czarru.randomteleports.listeners.PlayerInteractListener;

import java.util.Random;

public class Main extends JavaPlugin {

    public static Main inst;
    public static Random r = new Random();

    public static Main getInst() {
        return inst;
    }

    public void onEnable(){
        inst = this;
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
    }
}
