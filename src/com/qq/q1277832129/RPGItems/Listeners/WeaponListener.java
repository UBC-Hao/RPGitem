package com.qq.q1277832129.RPGItems.Listeners;/*
 *@author:yudi
 *@QQ:1277832129
 *@Twitter:yudi_327
 */

import com.qq.q1277832129.RPGItems.API.ItemShell;
import com.qq.q1277832129.RPGItems.Exception.ItemsException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Random;

public class WeaponListener implements Listener{
/*Link start!*/
    @EventHandler(priority = EventPriority.MONITOR)
    void damageUpper(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player){
        try {
            Player p = (Player)event.getDamager();
            ItemShell is = getShell(p);
            event.setDamage(event.getDamage() +is.getLevel("力量"));
        }catch(Exception e){}
        }
    }
    @EventHandler(priority = EventPriority.LOWEST)
    void noDamage(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player){
            try {
                Player p = (Player) event.getDamager();
                ItemShell is = getShell(p);
                int 命中率 = is.getLevel("命中");
                if(命中率==0) return ;
                Random rand = new Random();
                int chance = rand.nextInt(101);
                if(chance > 命中率){
                    event.setCancelled(true);
                }
            }catch (Exception e){}
        }
    }
    ItemShell getShell(Player p) throws ItemsException {
        return ItemShell.getShell(p.getItemInHand(),p);
    }

}
