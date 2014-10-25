package com.qq.q1277832129.RPGItems.API;/*
 *@author:yudi
 *@QQ:1277832129
 *@Twitter:yudi_327
 */

import com.qq.q1277832129.RPGItems.Exception.ItemsException;
import com.qq.q1277832129.RPGItems.Exception.NoMetaException;
import com.qq.q1277832129.RPGItems.Exception.NoSuchLine;
import com.qq.q1277832129.RPGItems.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ItemShell {
    private ItemStack item;
    private Player holder;
    ItemShell(ItemStack item,Player holder){
        this.item=item;
        this.holder=holder;
    }
    public static ItemShell getShell(ItemStack item,Player holder){
        return new ItemShell(item,holder);
    }

    public String getString(int line){

        ItemMeta meta = this.item.getItemMeta();
        List<String> list=null;
       if(meta.hasLore())
        list = meta.getLore();
        else
       list=new ArrayList<String>();
        int num = list.size();
        if(line>num-1) return "";
        return list.get(line);
    }

    public Player getHolder(){
        return this.holder;
    }

    public ItemStack getItem(){
        return this.item;
    }
    @Deprecated
    public synchronized void setStringAt(int num,String str){
        ItemMeta meta = item.getItemMeta();
        List<String> list = meta.getLore();
       list.set(num,str);
       meta.setLore(list);
       item.setItemMeta(meta);
    }

    public synchronized void addString(String str){
        ItemMeta meta = item.getItemMeta();
        List<String> list = null;
        if(meta.hasLore())
        list = meta.getLore();
        else
        list = new ArrayList<String>();
        list.add(str);
        meta.setLore(list);
        item.setItemMeta(meta);
    }

    public int getLevel(String str){
        for (int i = 0; i < getLoreSize() ; i++) {
            String line = getString(i);
            if(line.contains(str)) {
                if(line.contains(": ")) {
                    String strs[] = line.split(": ");
                    if(strs[0].contains(str)){
                        return Integer.parseInt(strs[1]);
                    }
                }
            }
        }
        return 0;
    }

    public int getLoreSize(){
        if(!this.item.hasItemMeta()) return 0;
        ItemMeta meta = this.item.getItemMeta();
        if(!this.item.getItemMeta().hasLore()) return 0;
        List<String> list = meta.getLore();
        return list.size();
    }

    public synchronized boolean setLevel(String str,int level){
      //返回是否拥有这个属性 如果有则返回true 没有就返回false
        //如果没有 会自动为你添加属性
       boolean has = false;
        for (int i = 0; i < getLoreSize() ; i++) {
            String line = getString(i);
            if(line.contains(str)) {
                if(line.contains(": ")) {
                    has = true;
                    String strs[] = line.split(": ");
                    String newer = strs[0]+": "+level;
                    setStringAt(i,newer);
                }
            }
        }
        if(!has){
           addString(str+": "+level);
        }
       return has;
    }

    public void setName(String str){
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(str);
        item.setItemMeta(meta);
    }
    public <T> boolean search(T t){
        if(!item.hasItemMeta()) return false;
        if(!item.getItemMeta().hasLore()) return false;
        for(String str:item.getItemMeta().getLore()){
            if (str.contains(t.toString())) return true;
        }
        return false;
    }
    //替换首次出现的那一行
    public synchronized void replaceLine(String str,String newer) {
        for (int i = 0; i < getLoreSize() ; i++) {
            String line = getString(i);
            if(line.contains(str)) {
                setStringAt(i,newer);
                break;
            }
        }
    }
    //给物品添加类型信息 若没有类型 则不产生任何反应
    public void addType(){
        if(Main.config.contains(item.getTypeId()+"")){
            this.addString(ChatColor.DARK_RED+"物品类型:"+Main.config.getString(item.getTypeId()+""));
        }
    }
    public void clearLore(){
        item.setItemMeta(null);
    }
    //随机给物品添加属性 注意 会清空以前的属性的哦
    public void fixup(){
        short s = 0;
        this.item.setDurability(s);
    }
    public void randomState()
    {
       Random rand = new Random();
       int index = rand.nextInt(5);
       this.clearLore();
       int copy = index;
       HashMap<String,Integer> map = new HashMap<String, Integer>();
       for(Map.Entry<String,Sender> entry : Main.map.entrySet()){
           if(entry.getValue().allow(item.getTypeId())) {
               if (rand.nextInt(10) < copy)
                   map.put(entry.getKey(), entry.getValue().random());
           }
       }
       for(Map.Entry<String,Integer> en : map.entrySet()){
           if(index--<0) break;

               this.setLevel(en.getKey(),en.getValue());

       }
    }
    //返回物品是否已经经过鉴定
    public boolean hasLooked(){
        return !search("未鉴定");
    }
    //获取该物品属性表
    public HashMap<String,Integer> getLevelMap(){
        HashMap<String,Integer> map = new HashMap<String, Integer>();
        for(Map.Entry<String,Sender> ma:Main.map.entrySet()){
            if(!search(ma.getKey())){
                continue;
            }
            int level = getLevel(ma.getKey());
            map.put(ma.getKey(),level);
        }
        return map;
    }
    //
    public void broke(){
       this.clearLore();
       this.getItem().setType(Material.STONE);
    }
    //给物品强化,如果没有鉴定过则抛出错误
    public void levelUp() throws ItemsException {
       if(!hasLooked()) throw new ItemsException(holder,"该物品未鉴定");
       if(!search("物品等级:")){
           this.addString("物品等级: 0");
           return;
       }

       int level = this.getLevel("物品等级:");
       this.setLevel("物品等级: ",level+1);
       for(Map.Entry<String,Integer> ent : this.getLevelMap().entrySet()){
           setLevel(ent.getKey(),ent.getValue()+1);
       }
    }

    @Override
    public int hashCode(){
        return item.hashCode();
    }

    @Override
    public String toString(){
        return item.toString();
    }

    @Override
    public boolean equals(Object obj){
       return obj instanceof ItemShell?((ItemShell)obj).getItem().equals(this.item):false;
    }
}
