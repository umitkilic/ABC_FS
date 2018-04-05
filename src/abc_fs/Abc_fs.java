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
        int         dikey_limit=4; // aşağı doğru kaç komsuluk bulunacak
        int         yatay_limit=4; // geriye doğru kaç komşuluk bulunacak
        List<foodsource> foodsourceslist; // her bir employed bee işleminden sonra oluşan toplu foodsource burada bulunacak.
        System.out.println("burada1");
        initialization_phase ip=new initialization_phase();
        System.out.println("burada2");
        data=ip.readData(); // veri alınıyor
        System.out.println("burada3");
        int attributeSayisi=data.numAttributes(); // toplam attribute sayısı alınıyor
        System.out.println("burada4");
        double[] foodFitnesses=new double[attributeSayisi-1];
        
        int[][] foodSource=new int[attributeSayisi-1][attributeSayisi-1];
        
        // besin kaynakları olusturuluyor
        foodSource=ip.createFoodSource(attributeSayisi, foodSource);
        System.out.println("burada5");
        
        
        
        
        EmployedBees e_bee=new EmployedBees();
        System.out.println("burada6");
        foodsourceslist=e_bee.determineNeighbors(foodSource,dikey_limit,yatay_limit,attributeSayisi); // foodsource: değiştirilecek besin kaynakları, attributeSayisi: dizileri oluşturmak için
        System.out.println("burada7");
        foodSource=e_bee.findBestFoodSources(foodSource, foodsourceslist,dikey_limit,yatay_limit).clone();
        System.out.println("burada8");
        
        
        getFitnessValue gfv=new getFitnessValue();
        int foldnumber=10;
        //fitness değerleri alınıyor
        foodFitnesses=gfv.getFitness(foodSource, foldnumber);
        
        
        System.out.println("\n--------------------------------------------\ntoplu:");
        for (int i = 0; i < foodSource.length; i++) {
            for (int j = 0; j < foodSource[0].length; j++) {
                System.out.print(foodSource[i][j]);
            }
            System.out.println("    Fitness:"+foodFitnesses[i]+" \n");
        }
    }
    
}
