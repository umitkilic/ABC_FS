/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abc_fs;

import java.util.List;
import weka.core.Instances;

/**
 *
 * @author Umit Kilic
 */
public class Steps {
    public Instances allSteps(Instances data,int dikey_limit, int yatay_limit, int iterationNumber,int foldnumber){
        
        //Instances           data;
        List<foodsource>    foodsourceslist; // her bir employed bee işleminden sonra oluşan toplu foodsource burada bulunacak.
        initialization_phase ip=new initialization_phase();
        //data=ip.readData(); // veri alınıyor
        int                 attributeSayisi=data.numAttributes(); // toplam attribute sayısı alınıyor
        double[]            foodFitnesses=new double[attributeSayisi-1];
        int[][]             foodSource=new int[attributeSayisi-1][attributeSayisi-1];
        int[][]             foodSource_eBees=new int[attributeSayisi-1][attributeSayisi-1];
        double[]            foodFitnesses_eBees=new double[attributeSayisi-1];
        getFitnessValue     gfv=new getFitnessValue();
        EmployedBees        e_bee=new EmployedBees();
        int[][]             foodsource_main=new int[attributeSayisi-1][attributeSayisi-1];
        double[]            foodFitnesses_main=new double[attributeSayisi-1];
        
        
        
        
        
        
        
        
        // besin kaynakları olusturuluyor
        foodSource=ip.createFoodSource(attributeSayisi, foodSource);
        
        int it_count=0;
        while (it_count<iterationNumber) {
            System.out.println("------------------------------------------------------------------------------------------------>>> ITERATION NUMBER= "+it_count);
            
            System.out.println("BAŞLANGIÇ:");
            for (int i = 0; i < foodSource.length; i++) {
                for (int j = 0; j < foodSource[0].length; j++) {
                    System.out.print(foodSource[i][j]);
                }
                System.out.println(" ");
            }
        
        for (int i = 0; i < foodFitnesses.length; i++) {
            int m[]=new int[foodSource[0].length];
            for (int j = 0; j < m.length; j++) {
                m[j]=foodSource[i][j];
            }
            foodFitnesses[i]=gfv.getFitnessOnebyOne(m, foldnumber);
        }
        
        
        
        foodsourceslist=e_bee.determineNeighbors(foodSource,dikey_limit,yatay_limit,attributeSayisi,foldnumber); // foodsource: değiştirilecek besin kaynakları, attributeSayisi: dizileri oluşturmak için
            System.out.println("bitirdi-1.");
        //------------------------------------------------------------------------------------------------------- ONLOOKER GÖREVİ YAPILIYOR ziyaret edilenler arasında en iyiler bulunuyor
        foodSource_eBees=e_bee.findBestFoodSources(foodSource, foodsourceslist,dikey_limit,yatay_limit).clone();
        System.out.println("bitirdi-2.");
        for (int i = 0; i < foodFitnesses.length; i++) {
            int m[]=new int[foodSource_eBees[0].length];
            for (int j = 0; j < m.length; j++) {
                m[j]=foodSource_eBees[i][j];
            }
            foodFitnesses_eBees[i]=gfv.getFitnessOnebyOne(m, foldnumber);
        }
        
        //foodFitnesses_eBees=gfv.getFitness(foodSource_eBees, foldnumber);
        for (int k = 0; k < foodSource.length; k++) {
            if (foodFitnesses_eBees[k]>foodFitnesses[k]) {
                for (int j = 0; j < foodSource[0].length; j++) {  foodSource[k][j]=foodSource_eBees[k][j]; foodFitnesses[k]=foodFitnesses_eBees[k];}
            } else {
            }
        }
        
        
        
        
        ///------------------------------------------------------------------------------------------------------------  SCOUT BEE SECTION
        int newfoodsources[][]=new int[attributeSayisi-1][attributeSayisi-1];
        ScoutBees scout=new ScoutBees();
        newfoodsources=scout.createRandomFoodSource(attributeSayisi, foodSource).clone();
        
        
        // scout bee ler yeni kaynaklar buldu
        
        double newfoodfitnesses[]=new double[newfoodsources[0].length];
        for (int i = 0; i < newfoodfitnesses.length; i++) {
            int m[]=new int[newfoodsources[0].length];
            for (int j = 0; j < m.length; j++) {
                m[j]=newfoodsources[i][j];
            }
            newfoodfitnesses[i]=gfv.getFitnessOnebyOne(m, foldnumber);
        }
        
        foodsourceslist=e_bee.determineNeighbors(newfoodsources,dikey_limit,yatay_limit,attributeSayisi,foldnumber); // foodsource: değiştirilecek besin kaynakları, attributeSayisi: dizileri oluşturmak için
        // ONLOOKER GÖREVİ YAPILIYOR ziyaret edilenler arasında en iyiler bulunuyor
        foodSource_eBees=e_bee.findBestFoodSources(newfoodsources, foodsourceslist,dikey_limit,yatay_limit).clone();
        for (int i = 0; i < newfoodfitnesses.length; i++) {
            int m[]=new int[foodSource_eBees[0].length];
            for (int j = 0; j < m.length; j++) {
                m[j]=foodSource_eBees[i][j];
            }
            foodFitnesses_eBees[i]=gfv.getFitnessOnebyOne(m, foldnumber);
        }
        System.out.println("\n--------------------------------------------\n");
        for (int k = 0; k < newfoodsources.length; k++) {
            if (foodFitnesses_eBees[k]>newfoodfitnesses[k]) {
                for (int j = 0; j < foodSource[0].length; j++) {  newfoodsources[k][j]=foodSource_eBees[k][j]; }
                newfoodfitnesses[k]=foodFitnesses_eBees[k];
            } else {
            }
        }
        
        
        for (int i = 0; i < foodSource.length; i++) {
                System.out.print(i+". -> ");
                for (int j = 0; j < foodSource[0].length; j++) {
                    System.out.print(foodSource[i][j]);
                }
                System.out.print(" old fitness:"+ foodFitnesses[i] +" ||| ");
                
                for (int j = 0; j < newfoodsources[0].length; j++) {
                    System.out.print(newfoodsources[i][j]);
                }
                System.out.println(" new fitness:"+ newfoodfitnesses[i]);
        }
        
        // --------------------------------------------------------------------------------------------------------------    scout ve old karşılaştırıp en iyiyi bulma evresi
        initialization_phase ip2=new initialization_phase();
        
            for (int i = 0; i < foodSource.length; i++) {
                int oldminindex=ip2.findMin(foodFitnesses);
                double oldmin=foodFitnesses[oldminindex];
                int newmaxindex=ip2.findMax(newfoodfitnesses);
                double newmax=newfoodfitnesses[newmaxindex];
                System.out.println("oldmin index:"+oldminindex+" oldmin val="+ oldmin + "|||  newmax index="+newmaxindex+ " newmax val="+newmax);
                if (newmax>oldmin) {
                    for (int j = 0; j < foodSource.length; j++) {foodSource[oldminindex][j]=newfoodsources[newmaxindex][j];}
                    foodFitnesses[oldminindex]=newfoodfitnesses[newmaxindex];
                    newfoodfitnesses[newmaxindex]=0.0;
                }
                /*if (foodFitnesses[i]<=newfoodfitnesses[i]) {
                    System.out.println("scout daha iyi");
                    for (int j = 0; j < foodSource.length; j++) {foodSource[i][j]=newfoodsources[i][j];}
                    foodFitnesses[i]=newfoodfitnesses[i];
                }*/
            }
            
            System.out.println("SONUÇ:");
            for (int i = 0; i < foodSource.length; i++) {
                for (int j = 0; j < foodSource[0].length; j++) {
                    System.out.print(foodSource[i][j]);
                }
                System.out.print(" fitness:"+ foodFitnesses[i]);
                System.out.println(" ");
            }
        
        it_count++;
        }// en dış while bitiş 
        
        double max=0.0;
        int index=0;
        for (int i = 0; i < foodFitnesses.length; i++) {
            if (foodFitnesses[i]>max) {
                
                max=foodFitnesses[i];
                index=i;
                System.out.println("buyuk. index: "+ index);
            }
        }
        int selectedFeatureVector[]=new int[foodSource[0].length];
        
        System.out.println("SEÇİLENLER:");
        for (int i = 0; i < foodSource[0].length; i++) {
            System.out.print(foodSource[index][i]);
            selectedFeatureVector[i]=foodSource[index][i];
        }
        System.out.println(" fmeasure:"+foodFitnesses[index]);
        String fitness=Double.toString(foodFitnesses[index]);
        //int[] sFV=new int[]{0,0,0,1,1,0,0,1,0,0,0,0,0,1,0,1,1,0,1,0,0,0,0,0};
        //fitness="0.8364629566404916";
        ip.createARFF(data,selectedFeatureVector,fitness);
        return data;
    }
}