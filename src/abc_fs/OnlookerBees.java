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
    public int[][] onLook(int[][] foodsources,double[] foodfitnesses,String filepath){
        getFitnessValue gfv=new getFitnessValue();
        List<foodsource> foodlistonlooker=new ArrayList<>();
        foodsource foodsource;
        Random  rand = new Random();
        double  n;
        double  MR=0.3; // modification rate
        int f[][]=new int[foodsources.length][foodsources[0].length];
        int f_one[]=new int[f[0].length];
        
        double f_fitness[]=new double[f[0].length];
        for (int k = 0; k < foodsources.length; k++) {
            
            for (int i = 0; i < foodsources.length; i++) {
            
            System.arraycopy(foodsources[k], 0, f[i], 0, f.length);
            for (int j = 0; j < f[0].length; j++) {n=rand.nextDouble(); if(n>MR && f[i][j]!=0){f[i][j]=0;}}
            System.arraycopy(f[i], 0, f_one, 0, f[i].length);
            f_fitness[i]=gfv.getFitnessOnebyOne(f_one, 10, filepath);
            
            }
            int maxfitindex=findMax(f_fitness);
            if (f_fitness[maxfitindex]>foodfitnesses[k]) {
                foodfitnesses[k]=f_fitness[maxfitindex];
                System.arraycopy(f[maxfitindex], 0, foodsources[k], 0, f[0].length);
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
}
