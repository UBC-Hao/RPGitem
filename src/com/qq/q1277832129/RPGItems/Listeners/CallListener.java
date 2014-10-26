package com.qq.q1277832129.RPGItems.Listeners;/*
 *@author:yudi
 *@QQ:1277832129
 *@Twitter:yudi_327
 */

import com.qq.q1277832129.RPGItems.API.AutoSetUp;
import com.qq.q1277832129.RPGItems.API.InventoryShell;
import com.qq.q1277832129.RPGItems.API.ItemShell;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class CallListener implements Listener{
//触摸铁块打开界面
  @EventHandler
  void onClick(PlayerInteractEvent event){
    try {
        Block block = event.getClickedBlock();
        if(block.getTypeId()==1) AutoSetUp.gift(event.getPlayer());
        if (!((block != null) && (block.getType() == Material.IRON_BLOCK))) {
            return;
        }
        if(event.getPlayer().getItemInHand() != null) {
            if(event.getPlayer().getItemInHand().getType()!=Material.AIR)
            AutoSetUp.callInventory(event.getPlayer());
            else
            {
                event.getPlayer().sendMessage("你需要先在手上拿东西");
                return;
            }
        }
        else{
            event.getPlayer().sendMessage("手上需要先拿着物品");
        }
    }catch(Exception e){}
}
  @EventHandler
  void onItemsClick(InventoryClickEvent event){
    Inventory inv = event.getInventory();
    if(!inv.getTitle().contains("开始强化装备吧")) return;
    if(event.getCurrentItem()==null) return;
    if(!event.getCurrentItem().hasItemMeta()) return;
    if(!event.getCurrentItem().getItemMeta().hasDisplayName()) return;
    Player p = (Player)event.getWhoClicked();
    String type = event.getCurrentItem().getItemMeta().getDisplayName();
    ItemShell is = ItemShell.getShell(event.getCurrentItem(),p);
    if(p.getItemInHand() == null) return;
    if(p.getItemInHand().getType() ==  Material.AIR) return;
    ItemShell hand = ItemShell.getShell(p.getItemInHand(),p);
    if(type.contains("鉴定武器")){
       try {
           if (this.takeLastSlot(p, 1, "鉴定石")) {
               if (!hand.hasLooked()) hand.randomState();
           } else {
               p.sendMessage(ChatColor.RED + "请将鉴定石放最后一格");
               return;
           }
       }
       finally {
           exit(p);
       }
    }

    if(type.contains("基础强化")){
        try {
            Random rand = new Random();
            boolean safe = false;
            int num = rand.nextInt(200);
            if (!hand.hasLooked()) {
                exit(p);
                p.sendMessage(ChatColor.RED + "这个武器未进行鉴定");
                return;
            }
            if (this.takeLastSlot(p, 1, "强化石")) {
                if (this.takeOther(p, 1, "保护石")) safe = true;
                if (num < (200 - 10 * hand.getLevel("物品等级"))) {
                    try {
                        hand.levelUp();
                        p.sendMessage(ChatColor.BOLD+"成功强化,恭喜你");
                    } catch (Exception e) {
                        p.sendMessage("没有鉴定过的武器,请不要尝试强化,扣除一个强化石");
                    }
                } else {
                    if (!safe)
                        hand.broke();
                    p.sendMessage(ChatColor.RED + "本次强化失败,若你使用了保护石则进行了保护滴");
                    exit(p);
                    return;
                }
            } else {
                p.sendMessage("请将强化石放最后一格");

            }
        }finally {
            exit(p);
        }

    }
    if(type.contains("装备打孔")){
      try {
          if (hand.search("打孔口")) {
              if (this.takeLastSlot(p, 1, "打孔石")) {
                  hand.replaceLine("打孔口", "钻孔");
                  p.sendMessage(ChatColor.BLUE+"成功打孔");
              } else {
                  p.sendMessage("请将打孔石放最后一格");
              }
          } else {
              p.sendMessage(ChatColor.RED + "这个武器不支持打孔");
          }
      }finally {
          exit(p);
      }
    }
    if(type.contains("宝石镶嵌")){
      try {
          ItemStack item = p.getInventory().getItem(8);
          if (item == null
                  ?
                  true : !(item.getItemMeta().hasDisplayName())) {
              p.sendMessage(ChatColor.RED + "你需要将宝石放在最后一格");
              return;
          }
          String name = item.getItemMeta().getDisplayName();
          String type1 = name.split("-")[1];
          if(!name.contains("镶嵌宝石")){
              p.sendMessage(ChatColor.RED + "你需要将宝石放在最后一格");
              return;
          }
          if(!hand.search("钻孔")){
              p.sendMessage("你的武器不支持打孔");
              return;
          }
          if(this.takeLastSlot(p,1,"镶嵌宝石")){
              hand.replaceLine("钻孔",type1);
              p.sendMessage(ChatColor.GREEN+"成功镶嵌");
          }else{
              p.sendMessage(ChatColor.RED+"请将镶嵌宝石放最后一格");
          }
      }
      finally {
          exit(p);
      }
    }
    if(type.contains("装备洗炼")){
        try{
        if(takeLastSlot(p,1,"洗炼石")){
            hand.randomState();
            p.sendMessage(ChatColor.GREEN+"成功洗炼");
        }else{
            p.sendMessage("请将洗炼石放最后一格");
        }
        }finally{
        exit(p);
        }
    }
    if(type.contains("装备维修")){
      try{
          if(this.takeLastSlot(p, 1, "修复石"))
          {
              hand.fixup();
              p.sendMessage(ChatColor.AQUA+"修复成功");
          }
          else{
          p.sendMessage("请在最后一格放修复石");}
      }finally{
      exit(p);
      }
    }

  }
    //拿走倒数第一格的物品 返回 是否成功拿走
    boolean takeLastSlot(Player p,int amount,String name){
        InventoryShell is = new InventoryShell(p);
        return is.take(8,amount,name);
    }
    //拿走倒数第二格的物品
    boolean takeOther(Player p,int amount,String name){
        InventoryShell is = new InventoryShell(p);
        return is.take(7,amount,name);
    }

    void exit(Player player){
        player.closeInventory();
        player.updateInventory();
        effect(player);
    }
    void effect(Player p){
        p.getWorld().playEffect(p.getLocation(), Effect.MOBSPAWNER_FLAMES,3);

    }
}
