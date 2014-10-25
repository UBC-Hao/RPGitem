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
        addItemStack(ChatColor.BLUE+"鉴定武器",inv,p,276,"点击鉴定手中武器","鉴定石放最后一格");
        addItemStack(ChatColor.BOLD+"基础强化",inv,p,302,"点击强化手中武器","强化石放物品栏最后一格","保护石放物品栏倒数第二格");
        addItemStack(ChatColor.DARK_BLUE+"装备打孔",inv,p,61,"为手中装备打孔","打孔石放最后一格");
        addItemStack(ChatColor.GRAY+"宝石镶嵌",inv,p,61,"为手中装备镶嵌","镶嵌宝石放最后一格");
        addItemStack(ChatColor.DARK_RED+"装备洗炼",inv,p,11,"为手中物品洗炼","将洗炼石放最后一格","重新分配属性","有几率失败变成石头","保护石放倒数第二格可以避免报废");
        addItemStack(ChatColor.YELLOW+"装备维修",inv,p,276,"修复手中的物品","修复石放最后一格","消耗一个修复石");
        p.openInventory(inv);
    }
    public static void  gift(Player p){
        Inventory inv = Bukkit.createInventory(null,18,"在这里领取你的宝物吧");
        addItemStack(ChatColor.BOLD+"修复石",inv,p,388,"用它放在最后一格来修复你的物品");
        addItemStack(ChatColor.BOLD+"打孔石",inv,p,388,"可以为你的武器开孔");//成功打孔后变成 钻孔
        addItemStack(ChatColor.BOLD+"洗炼石",inv,p,388,"为你的武器重新分配属性");
        addItemStack(ChatColor.BOLD+"镶嵌宝石-"+"凋零: 100",inv,p,388,"为你的武器镶嵌特殊能力");
        addItemStack(ChatColor.BOLD+"保护石",inv,p,388,"失败时不会报废物品");
        addItemStack(ChatColor.BOLD+"强化石",inv,p,388,"为你的武器进行强化");
        addItemStack(ChatColor.BOLD+"鉴定石",inv,p,388,"为你没有鉴定的武器进行鉴定");
        addItemStack(ChatColor.BOLD+"测试武器",inv,p,388,"未鉴定","打孔口","打孔口");
        p.openInventory(inv);
    }
    static void addItemStack(String name,Inventory inv,Player p,int id,String... line){
        ItemStack item = new ItemStack(id);
        try {

            ItemShell is = ItemShell.getShell(item, p);
            for(String str : line)
            is.addString(str);
            is.setName(name);
            inv.addItem(is.getItem());

        }catch(Exception e){}
    }
}
