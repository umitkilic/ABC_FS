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
public class Abc_fs {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Instances       data;
        int         dikey_limit=3; // aşağı doğru kaç komsuluk bulunacak
        int         yatay_limit=4; // geriye doğru kaç komşuluk bulunacak
        List<foodsource> foodsourceslist; // her bir employed bee işleminden sonra oluşan toplu foodsource burada bulunacak.
        initialization_phase ip=new initialization_phase();
        data=ip.readData(); // veri alınıyor
        int attributeSayisi=data.numAttributes(); // toplam attribute sayısı alınıyor
        double[] foodFitnesses=new double[attributeSayisi-1];
        int[][] foodSource=new int[attributeSayisi-1][attributeSayisi-1];
        int[][] foodSource_eBees=new int[attributeSayisi-1][attributeSayisi-1];
        double[] foodFitnesses_eBees=new double[attributeSayisi-1];
        getFitnessValue gfv=new getFitnessValue();
        EmployedBees e_bee=new EmployedBees();
        EmployedBees onlooker=new EmployedBees();
        int foldnumber=10;
        
        
        
        
        
        
        
        
        
        // besin kaynakları olusturuluyor
        foodSource=ip.createFoodSource(attributeSayisi, foodSource);
        
        
        for (int i = 0; i < foodFitnesses.length; i++) {
            int m[]=new int[foodSource[0].length];
            for (int j = 0; j < m.length; j++) {
                m[j]=foodSource[i][j];
            }
            foodFitnesses[i]=gfv.getFitnessOnebyOne(m, foldnumber);
        }
        //foodFitnesses=gfv.getFitness(foodSource, foldnumber);
        
        
        
        foodsourceslist=e_bee.determineNeighbors(foodSource,dikey_limit,yatay_limit,attributeSayisi); // foodsource: değiştirilecek besin kaynakları, attributeSayisi: dizileri oluşturmak için
        
        // ONLOOKER GÖREVİ YAPILIYOR
        foodSource_eBees=e_bee.findBestFoodSources(foodSource, foodsourceslist,dikey_limit,yatay_limit).clone();
        for (int i = 0; i < foodFitnesses.length; i++) {
            int m[]=new int[foodSource_eBees[0].length];
            for (int j = 0; j < m.length; j++) {
                m[j]=foodSource_eBees[i][j];
            }
            foodFitnesses_eBees[i]=gfv.getFitnessOnebyOne(m, foldnumber);
        }
        
        //foodFitnesses_eBees=gfv.getFitness(foodSource_eBees, foldnumber);
        System.out.println("\n--------------------------------------------\n iyiyi bulma:");
        for (int k = 0; k < foodSource.length; k++) {
            if (foodFitnesses_eBees[k]>foodFitnesses[k]) {
                System.out.println("daha iyi");
                for (int j = 0; j < foodSource[0].length; j++) {  foodSource[k][j]=foodSource_eBees[k][j]; foodFitnesses[k]=foodFitnesses_eBees[k];}
            } else {
            }
        }
        System.out.println("\n--------------------------------------------\ntoplu:");
        for (int i = 0; i < foodSource.length; i++) {
            for (int j = 0; j < foodSource[0].length; j++) {
                System.out.print(foodSource[i][j]);
            }
            System.out.println("    Fitness:"+foodFitnesses[i]+" \n");
        }
        int newfoodsources[][]=new int[attributeSayisi-1][attributeSayisi-1];
        ScoutBees scout=new ScoutBees();
        newfoodsources=scout.createRandomFoodSource(attributeSayisi, foodSource).clone();
        
        for (int i = 0; i < newfoodsources.length; i++) {
            for (int j = 0; j < newfoodsources[0].length; j++) {
                System.out.print(newfoodsources[i][j]);
            }
            System.out.println(" ");
        }
    }
    
}
