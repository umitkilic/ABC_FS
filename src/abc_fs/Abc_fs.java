/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abc_fs;

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
        Instances data;
        
        
        initialization_phase ip=new initialization_phase();
        data=ip.readData(); // veri alınıyor
        int attributeSayisi=data.numAttributes(); // toplam attribute sayısı alınıyor
        double[] foodFitnesses=new double[attributeSayisi-1];
        
        int[][] foodSource=new int[attributeSayisi-1][attributeSayisi-1];
        
        // besin kaynakları olusturuluyor
        foodSource=ip.createFoodSource(attributeSayisi, foodSource);
        
        
        getFitnessValue gfv=new getFitnessValue();
        int foldnumber=10;
        // fitness değerleri alınıyor
        foodFitnesses=gfv.getFitness(foodSource, foldnumber);
        
        /*for (int i = 0; i < attributeSayisi-1; i++) {
            System.out.println(foodFitnesses[i]);
        }*/
        EmployedBees eb=new EmployedBees();
        eb.determineNeighbors(foodSource,3,attributeSayisi); // foodsource: değiştirilecek besin kaynakları, 3: MAX_LIMIT, attributeSayisi: dizileri oluşturmak için
    }
    
}
