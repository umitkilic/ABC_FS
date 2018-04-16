/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abc_fs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author samsung1
 */
public class OnlookerBees {
    public int[][] onLook(int[][] foodsources,double[] foodfitnesses,int foldnumber,String filepath,double MR){
        getFitnessValue gfv=new getFitnessValue();
        //List<foodsource> foodlistonlooker=new ArrayList<>();
        //foodsource foodsource;
        Random  rand = new Random();
        double  n;
        //double  MR=0.1; // modification rate
        int f[][]=new int[foodsources.length][foodsources[0].length]; // her bir food sourceden olusturulacak feature sayısı kadar foodsource
        int f_one[]=new int[f[0].length];
        
        double f_fitness[]=new double[f[0].length];
        for (int k = 0; k < foodsources.length; k++) {
            
            for (int i = 0; i < foodsources.length; i++) {
            
            System.arraycopy(foodsources[k], 0, f[i], 0, f.length);
            for (int j = 0; j < f[0].length; j++) { // onlookerlar değişikliği burada yapıyor 1 yerine 0 yazıyor
                n=rand.nextDouble(); if(n>MR && f[i][j]!=0){f[i][j]=0;}
            }
            System.arraycopy(f[i], 0, f_one, 0, f[i].length);
            f_fitness[i]=gfv.getFitnessOnebyOne(f_one, foldnumber, filepath);
            
            }
            int maxfitindex=findMax(f_fitness);
            if (f_fitness[maxfitindex]>foodfitnesses[k]) {
                foodfitnesses[k]=f_fitness[maxfitindex];
                System.arraycopy(f[maxfitindex], 0, foodsources[k], 0, f[0].length);
            }else if(f_fitness[maxfitindex]==foodfitnesses[maxfitindex]){
                int f_onlooker[]=new int[foodsources.length];
                int f_food[]=new int[foodsources.length];
                System.arraycopy(f[maxfitindex], 0, f_onlooker, 0, f[0].length);
                System.arraycopy(f[maxfitindex], 0, f_food, 0, f[0].length);
                if (this.numberof1s(f_onlooker)<this.numberof1s(f_food)) {
                    foodfitnesses[k]=f_fitness[maxfitindex];
                    System.arraycopy(f_onlooker, 0, foodsources[k], 0, f_onlooker.length);
                }
            }
        }
        return foodsources;
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
