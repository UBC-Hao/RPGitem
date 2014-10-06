package com.qq.q1277832129.RPGItems.API;/*
 *@author:yudi
 *@QQ:1277832129
 *@Twitter:yudi_327
 */

import com.qq.q1277832129.RPGItems.Exception.ItemsException;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryShell {
    Inventory inv;
    Player holder;
    public InventoryShell(Player p){
       this.inv = p.getInventory();
        this.holder=p;
    }
    public void add(ItemStack itemStack){
        inv.addItem(itemStack);
    }
    
    public boolean take(int slot,int amount){
        //如果能成功拿走就返回true 否则返回false
        ItemStack item = inv.getItem(slot);
        if(item.getAmount()>=amount){
            if(item.getAmount()==amount) {
                item.setType(Material.AIR);
            }else{
                item.setAmount(item.getAmount()-amount);
            }
          inv.setItem(slot,item);
          return true;
        }

        return false;
    }
    public ItemStack get(int slot){
        //返回指定位置的副本,注意 只是副本
        return inv.getItem(slot);
    }
}
