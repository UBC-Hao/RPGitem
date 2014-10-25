package com.qq.q1277832129.RPGItems.Exception;/*
 *@author:yudi
 *@QQ:1277832129
 *@Twitter:yudi_327
 */
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ItemsException extends Exception{
    String str;
    Player p;
    public ItemsException(Player p,String str){
        super(str);
        this.str=str;
    }
    public void send(){
        p.sendMessage(ChatColor.RED+str);
    }
}
