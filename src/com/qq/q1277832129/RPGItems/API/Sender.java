package com.qq.q1277832129.RPGItems.API;/*
 *@author:yudi
 *@QQ:1277832129
 *@Twitter:yudi_327
 */

import java.util.Random;

public class Sender {
/*Link start!*/
    int max;
    int min;
    public Sender(int max,int min){
        this.max=max;
        this.min=min;
    }
    public int getMax(){
        return max;
    }
    public int getMin(){
        return min;
    }
    public int random(){
        Random rand = new Random();
        return rand.nextInt(max-min)+min;
    }
}
