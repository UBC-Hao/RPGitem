package com.qq.q1277832129.RPGItems;/*
 *@author:yudi
 *@QQ:1277832129
 *@Twitter:yudi_327
 */

import com.qq.q1277832129.RPGItems.API.Sender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;

public class Main extends JavaPlugin {
public static FileConfiguration config;


public static HashMap<String,Sender> map;
    @Override
    public void onEnable(){
       config=getConfig();
       List<String> list = config.getStringList("stats");
       //stats:
       //  - "stat:min-max"
       for(String pointer:list){
          String now = pointer.split(":")[0];
          String maxmin = pointer.split(":")[1];
          int min = Integer.parseInt(maxmin.split("-")[0]);
          int max = Integer.parseInt(maxmin.split("-")[1]);
          map.put(now,new Sender(min,max));
       }
    }
}
