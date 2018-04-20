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
        getFitnessValue     gfv=new getFitnessValue();
        Random              rand = new Random(1);
        double              n;
        int                 f[][]=new int[foodsources.length][foodsources[0].length]; // her bir food sourceden olusturulacak feature sayısı kadar foodsource
        int                 f_one[]=new int[f[0].length];
        
       
            
        double f_fitness[]=new double[f[0].length];
        //for (int k = 0; k < foodsources.length; k++) {
            int i=0;
            while(i<foodsources.length){
                System.arraycopy(foodsources[i], 0, f[i], 0, f.length);
                int numof1sfood[]=new int[f.length];
                System.arraycopy(f[i], 0, numof1sfood, 0, numof1sfood.length);
                int numof1=this.numberof1s(numof1sfood);
                for (int j = 0; j < f[0].length; j++) { // onlookerlar değişikliği burada yapıyor 1 yerine 0 yazıyor
                    n=rand.nextDouble();
                    numof1=this.numberof1s(numof1sfood);
                    if(n<MR && f[i][j]!=0 && numof1>1){f[i][j]=0;}
                    System.arraycopy(f[i], 0, numof1sfood, 0, numof1sfood.length);    
                }
                System.arraycopy(f[i], 0, f_one, 0, f[i].length);
                f_fitness[i]=gfv.getFitnessOnebyOne(f_one, foldnumber, filepath);
                i++;
            }
           
        
        return f;
    }
    
    public int findMax(double fitnesses[]){
        double maxfit=0.0;
        int maxfitindex=0;
        
        for (int i = 0; i < fitnesses.length; i++) {
            if (fitnesses[i]>maxfit) {
                maxfitindex=i;
                maxfit=fitnesses[i];
            }
        }
        
        return maxfitindex;
    }
    
    public int numberof1s(int[] f){
        int t=0;
        for (int i = 0; i < f.length; i++) {
            t=t+f[i];
        }
        return t;
    }
}
