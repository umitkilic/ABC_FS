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
        int         yatay_limit=3; // geriye doğru kaç komşuluk bulunacak
        List<foodsource> foodsourceslist; // her bir employed bee işleminden sonra oluşan toplu foodsource burada bulunacak.
        
        initialization_phase ip=new initialization_phase();
        data=ip.readData(); // veri alınıyor
        int attributeSayisi=data.numAttributes(); // toplam attribute sayısı alınıyor
        double[] foodFitnesses=new double[attributeSayisi-1];
        
        int[][] foodSource=new int[attributeSayisi-1][attributeSayisi-1];
        
        // besin kaynakları olusturuluyor
        foodSource=ip.createFoodSource(attributeSayisi, foodSource);
        
        
        //getFitnessValue gfv=new getFitnessValue();
        //int foldnumber=10;
        // fitness değerleri alınıyor
        //foodFitnesses=gfv.getFitness(foodSource, foldnumber);
        
        
        EmployedBees e_bee=new EmployedBees();
        foodsourceslist=e_bee.determineNeighbors(foodSource,dikey_limit,yatay_limit,attributeSayisi); // foodsource: değiştirilecek besin kaynakları, attributeSayisi: dizileri oluşturmak için
        e_bee.findBestFoodSources(foodSource, foodsourceslist,dikey_limit,yatay_limit);
    }
    
}
