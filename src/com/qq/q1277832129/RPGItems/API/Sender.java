package com.qq.q1277832129.RPGItems.API;/*
 *@author:yudi
 *@QQ:1277832129
 *@Twitter:yudi_327
 */

import java.util.List;
import java.util.Random;

public class Sender {
/*Link start!*/
    int max;
    int min;
    List<Integer> list;
    public Sender(int max,int min,List list){
        this.max=max;
        this.min=min;
        this.list=list;
    }
    public int getMax(){
        return max;
    }
    public int getMin(){
        return min;
    }
    public boolean allow(int id){
        return list.contains(id);
    }
    public int random(){
        Random rand = new Random();
        return rand.nextInt(max-min)+min;
    }
}
