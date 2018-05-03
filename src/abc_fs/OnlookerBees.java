/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abc_fs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author samsung1
 */
public class OnlookerBees {
    public int[][] onLook(int[][] foodsources,double[] foodfitnesses,int foldnumber,String filepath,double MR){
        Random              rand = new Random(1);
        double              n;
        int                 f[][]=new int[foodsources.length][foodsources[0].length]; // her bir food sourceden olusturulacak feature sayısı kadar foodsource
        
       
            int i=0;
            while(i<foodsources.length){
                System.arraycopy(foodsources[i], 0, f[i], 0, f.length);
                int numof1sfood[]=new int[f.length];
                System.arraycopy(f[i], 0, numof1sfood, 0, numof1sfood.length);
                int numof1;
                
                for (int j = 0; j < f[0].length; j++) { // onlookerlar değişikliği burada yapıyor 1 yerine 0 yazıyor
                    n=rand.nextDouble();
                    numof1=this.numberof1s(numof1sfood);
                    if(n<MR && f[i][j]!=0 && numof1>1){f[i][j]=0;}
                    System.arraycopy(f[i], 0, numof1sfood, 0, numof1sfood.length);    
                }
                i++;    
            }
        
        return f;
    }
    
    public boolean isExist(int[][] foods,int food[]){
        boolean exist=false;
        int temp[]=new int[food.length];
        for (int j = 0; j < foods.length; j++) {
            System.arraycopy(foods[j], 0, temp, 0, temp.length);
            if (Arrays.equals(food, temp)) {exist=true; break;}
        }
        
        return exist;
    }
    
    
    
    public int numberof1s(int[] f){
        int t=0;
        for (int i = 0; i < f.length; i++) {
            t=t+f[i];
        }
        return t;
    }
}
