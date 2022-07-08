package xyz.czarru.randomteleports.helpers;

import org.bukkit.ChatColor;

public class ChatHelper {

    public String color(String string){
        return (string==null ? "" : ChatColor.translateAlternateColorCodes('&', string));
    }
}
