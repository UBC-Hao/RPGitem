package com.qq.q1277832129.RPGItems.API;/*
 *@author:yudi
 *@QQ:1277832129
 *@Twitter:yudi_327
 */

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class AutoSetUp {
/*Link start!*/
    public static void setupListener(){}
    public static void callInventory(Player p){
        Inventory inv = Bukkit.createInventory(null,9,"开始强化装备吧!");
        addItemStack(ChatColor.BLUE+"鉴定武器",inv,p,"点击鉴定手中武器");
        addItemStack(ChatColor.BOLD+"基础强化",inv,p,"点击强化手中武器","强化石放物品栏最后一格","保护石放物品栏倒数第二格");
        addItemStack(ChatColor.DARK_BLUE+"装备打孔",inv,p,"为手中装备打孔");
        addItemStack(ChatColor.GRAY+"宝石镶嵌",inv,p,"为手中装备镶嵌","宝石放最后一格");
        addItemStack(ChatColor.DARK_RED+"装备洗炼",inv,p,"为手中物品洗炼","有几率失败");
        addItemStack(ChatColor.YELLOW+"装备维修",inv,p,"修复手中的物品");
    }
    static void addItemStack(String name,Inventory inv,Player p,String... line){
        ItemStack item = new ItemStack(Material.IRON_BLOCK);
        try {

            ItemShell is = ItemShell.getShell(item, p);
            for(String str : line)
            is.addString(str);
            is.setName(name);
            inv.addItem(is.getItem());

        }catch(Exception e){}
    }
}
