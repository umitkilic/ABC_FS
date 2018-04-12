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
    public Instances allSteps(Instances data,int dikey_limit, int yatay_limit, int iterationNumber,int foldnumber,String pathname,String newpathname){
        
        List<foodsource>    foodsourceslist; // her bir employed bee işleminden sonra oluşan toplu foodsource burada bulunacak.
        initialization_phase ip=new initialization_phase();
        int                 attributeSayisi=data.numAttributes(); // toplam attribute sayısı alınıyor
        double[]            foodFitnesses=new double[attributeSayisi-1];
        int[][]             foodSource=new int[attributeSayisi-1][attributeSayisi-1];
        int[][]             foodSource_eBees=new int[attributeSayisi-1][attributeSayisi-1];
        double[]            foodFitnesses_eBees=new double[attributeSayisi-1];
        getFitnessValue     gfv=new getFitnessValue();
        EmployedBees        e_bee=new EmployedBees();
       
        // besin kaynakları olusturuluyor
        foodSource=ip.createFoodSource(attributeSayisi, foodSource);
        
        int it_count=0;
        
        while (it_count<iterationNumber) {
            System.out.println("------------------------------------------------------------------------------------------------>>> ITERATION NUMBER= "+it_count);
            
            System.out.println("BAŞLANGIÇ:");
            for (int[] foodSource1 : foodSource) {
                for (int j = 0; j < foodSource[0].length; j++) {
                    System.out.print(foodSource1[j]);
                }
                System.out.println(" ");    
            }
            
            for (int i = 0; i < foodFitnesses.length; i++) { // üretilen başlangıç değerlerinin fitness değerleri bulunuyor
                int m[]=new int[foodSource[0].length];
                System.arraycopy(foodSource[i], 0, m, 0, foodSource[i].length);
                foodFitnesses[i]=gfv.getFitnessOnebyOne(m, foldnumber,pathname);
                }
            
        
        
        // foodsource: değiştirilecek besin kaynakları, attributeSayisi: dizileri oluşturmak için
        foodsourceslist=e_bee.determineNeighbors(foodSource,dikey_limit,yatay_limit,attributeSayisi,foldnumber,pathname); 
            System.out.print("deter. neig.");
            
            
            
        //---------------------------------------------------------------------- ONLOOKER GÖREVİ YAPILIYOR ziyaret edilenler arasında en iyiler bulunuyor
        foodSource_eBees=e_bee.findBestFoodSources(foodSource, foodsourceslist,dikey_limit,yatay_limit).clone();
        System.out.println(" find. best.");
        for (int i = 0; i < foodFitnesses.length; i++) {
            int m[]=new int[foodSource_eBees[0].length];
            System.arraycopy(foodSource_eBees[i], 0, m, 0, m.length);
            /*for (int j = 0; j < m.length; j++) {
                m[j]=foodSource_eBees[i][j];
            }*/
            foodFitnesses_eBees[i]=gfv.getFitnessOnebyOne(m, foldnumber,pathname);
        }
        
        //foodFitnesses_eBees=gfv.getFitness(foodSource_eBees, foldnumber);
        for (int k = 0; k < foodSource.length; k++) {
            if (foodFitnesses_eBees[k]>foodFitnesses[k]) {
                System.arraycopy(foodSource_eBees[k], 0, foodSource[k], 0, foodSource[k].length);
                foodFitnesses[k]=foodFitnesses_eBees[k];
                /*for (int j = 0; j < foodSource[0].length; j++) {  
                    foodSource[k][j]=foodSource_eBees[k][j]; foodFitnesses[k]=foodFitnesses_eBees[k];
                }*/
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
            System.arraycopy(newfoodsources[i], 0, m, 0, m.length);
            /*for (int j = 0; j < m.length; j++) {
                m[j]=newfoodsources[i][j];
            }*/
            newfoodfitnesses[i]=gfv.getFitnessOnebyOne(m, foldnumber,pathname);
        }
        
        foodsourceslist=e_bee.determineNeighbors(newfoodsources,dikey_limit,yatay_limit,attributeSayisi,foldnumber,pathname); // foodsource: değiştirilecek besin kaynakları, attributeSayisi: dizileri oluşturmak için
        // ONLOOKER GÖREVİ YAPILIYOR ziyaret edilenler arasında en iyiler bulunuyor
        foodSource_eBees=e_bee.findBestFoodSources(newfoodsources, foodsourceslist,dikey_limit,yatay_limit).clone();
        for (int i = 0; i < newfoodfitnesses.length; i++) {
            int m[]=new int[foodSource_eBees[0].length];
            System.arraycopy(foodSource_eBees[i], 0, m, 0, m.length);
            /*for (int j = 0; j < m.length; j++) {
                m[j]=foodSource_eBees[i][j];
            }*/
            foodFitnesses_eBees[i]=gfv.getFitnessOnebyOne(m, foldnumber,pathname);
        }
        
        for (int k = 0; k < newfoodsources.length; k++) {
            if (foodFitnesses_eBees[k]>newfoodfitnesses[k]) {
                System.arraycopy(foodSource_eBees[k], 0, newfoodsources[k], 0, newfoodsources[k].length);
                //for (int j = 0; j < foodSource[0].length; j++) {  newfoodsources[k][j]=foodSource_eBees[k][j]; }
                newfoodfitnesses[k]=foodFitnesses_eBees[k];
            } else {
            }
        }
        
        
        // --------------------------------------------------------------------------------------------------------------    scout ve old karşılaştırıp en iyiyi bulma evresi
        initialization_phase ip2=new initialization_phase();
        
            for (int i = 0; i < foodSource.length; i++) {
                int oldminindex=ip2.findMin(foodFitnesses);
                double oldmin=foodFitnesses[oldminindex];
                int newmaxindex=ip2.findMax(newfoodfitnesses);
                double newmax=newfoodfitnesses[newmaxindex];
                if (newmax>oldmin) {
                    //for (int j = 0; j < foodSource.length; j++) {foodSource[oldminindex][j]=newfoodsources[newmaxindex][j];}
                    System.arraycopy(newfoodsources[newmaxindex], 0, foodSource[oldminindex], 0, foodSource[0].length);
                    foodFitnesses[oldminindex]=newfoodfitnesses[newmaxindex];
                    newfoodfitnesses[newmaxindex]=0.0;
                }
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
            }
        }
        int selectedFeatureVector[]=new int[foodSource[0].length];
        
        System.out.println("SEÇİLENLER:");
        for (int i = 0; i < foodSource[0].length; i++) {
            System.out.print(foodSource[index][i]);
            selectedFeatureVector[i]=foodSource[index][i];
        }
        System.out.println(" fmeasure:"+foodFitnesses[index]);
        //int[] sFV=new int[]{0,0,0,1,1,0,0,1,0,0,0,0,0,1,0,1,1,0,1,0,0,0,0,0};
        //fitness="0.8364629566404916";
        ip.createARFF(data,selectedFeatureVector,newpathname);
        return data;
    }
}
