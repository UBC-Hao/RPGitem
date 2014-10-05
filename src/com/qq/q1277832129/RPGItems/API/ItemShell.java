package com.qq.q1277832129.RPGItems.API;/*
 *@author:yudi
 *@QQ:1277832129
 *@Twitter:yudi_327
 */

import com.qq.q1277832129.RPGItems.Exception.ItemsException;
import com.qq.q1277832129.RPGItems.Exception.NoMetaException;
import com.qq.q1277832129.RPGItems.Exception.NoSuchLine;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemShell {
    private ItemStack item;
    private Player holder;
    ItemShell(ItemStack item,Player holder){
        this.item=item;
        this.holder=holder;
    }
    public static ItemShell getShell(ItemStack item,Player holder) throws ItemsException{
        if((item == null)||(item.getType()== Material.AIR)) throw new ItemsException(holder,"不能没有任何东西");
        return new ItemShell(item,holder);
    }

    public String getString(int line) throws NoMetaException, NoSuchLine {
        if(!this.item.hasItemMeta()) throw new NoMetaException(holder,"该物品没有属性");
        ItemMeta meta = this.item.getItemMeta();
        if(!this.item.getItemMeta().hasLore()) throw new NoMetaException(holder,"该物品没有属性");
        List<String> list = meta.getLore();
        int num = list.size();
        if(num-1 < line) throw new NoSuchLine(holder,"未知的错误");
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

    public int getLevel(String str) throws NoSuchLine, NoMetaException {
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

    public int getLoreSize() throws NoMetaException, NoSuchLine {
        if(!this.item.hasItemMeta()) throw new NoMetaException(holder,"该物品没有属性");
        ItemMeta meta = this.item.getItemMeta();
        if(!this.item.getItemMeta().hasLore()) throw new NoMetaException(holder,"该物品没有属性");
        List<String> list = meta.getLore();
        return list.size();
    }

    public synchronized boolean setLevel(String str,int level) throws NoSuchLine, NoMetaException {
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

    public synchronized void replaceLine(String str,String newer) throws NoSuchLine, NoMetaException {
        for (int i = 0; i < getLoreSize() ; i++) {
            String line = getString(i);
            if(line.contains(str)) {
                setStringAt(i,newer);
                break;
            }
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
