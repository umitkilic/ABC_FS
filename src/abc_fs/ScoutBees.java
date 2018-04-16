/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abc_fs;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Umit Kilic
 */
public class ScoutBees {
    
    public int[][] createRandomFoodSource(int attrNumber,int[][] foodSources,double MR){
        int     newFoodSources[][]=new int[attrNumber-1][attrNumber-1];
        Random  rand = new Random();
        double  n = rand.nextDouble();
        //double  MR=0.5; // modification rate
        int singlefood[]=new int[attrNumber-1];
        boolean e=true;
        int numofchange=0; // değişiklik sayısı
        int i=0;
        boolean change=true;
        while(i<newFoodSources.length){
            
            while(change){
                for (int j = 0; j < newFoodSources.length; j++) {
                n=rand.nextDouble();
                // rastgele gelen sayı büyükse değişiklik yap
                if (n>MR /*&& food2[i]!=1*/) 
                { newFoodSources[i][j]=0; numofchange++; change=false;}
                else
                {newFoodSources[i][j]=1; numofchange++; change=false; }
                singlefood[j]=newFoodSources[i][j];
                }
            }
            change=true;
            
            int othersingle[]=new int[foodSources[0].length];
            for (int j = 0; j < foodSources.length; j++) {
                for (int k = 0; k < foodSources[0].length; k++) {
                    othersingle[k]=foodSources[j][k];
                }
                e=Arrays.equals(singlefood,othersingle);
                if (e) {
                    break;
                }
            }
            

            //System.out.println("num of change:"+numofchange);
            /*if(numofchange>=3){
                break;
            }*/
            if (e) {
                System.out.println("Eşit olduğundan arttırılmadı.");
            }else{
                i++;
            }
            
        }
        
        
        return newFoodSources;
    }
}
