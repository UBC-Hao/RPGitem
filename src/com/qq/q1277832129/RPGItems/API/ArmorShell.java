package com.qq.q1277832129.RPGItems.API;/*
 *@author:yudi
 *@QQ:1277832129
 *@Twitter:yudi_327
 */

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ArmorShell extends ItemShell
implements Armor
{
   public ArmorShell(ItemStack item,Player holder){
      super(item,holder);
   }
    public void setDefence(){

    }
    public int getDefence(){
      return 0;
    }
}
