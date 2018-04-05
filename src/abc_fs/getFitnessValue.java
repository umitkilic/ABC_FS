/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abc_fs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;
import weka.core.Debug;
import weka.core.Instances;

/**
 *
 * @author Umit Kilic
 */
public class getFitnessValue {
    
    /*public double[] getFitness(int[][] foodSource,int foldnumber){
        Classifier classifier;
        Evaluation eval;
        initialization_phase init=new initialization_phase();
        Instances data=init.readData();
        
        int N=data.numAttributes();
        double FoodFitnesses[]=new double[N-1];
        Instances data1=data;
        
        try{
            classifier=new IBk(); // sınıflandırıcı oluşturuldu
            eval=new Evaluation(data1); // degerlendirici olusturuldu
            
            
            for (int i = 0; i < N-1; i++) {
                data1=init.readData(); // attr silmek için her gönderilişte ilk halinin olması için
                        int food[]=new int[N-1];
                        
                        // tek attribute oluşturuluyor
                        for (int j = 0; j < N-1; j++) {
                            food[j]=foodSource[i][j];
                        }
                       data1=this.deleteZeros(food, N, data1);
                        //Debug.Random rand=new Debug.Random(1); // sürekli 1 değeri veriyor?
                        
                        //Random rand = new Random();             // üstteki yerine bu kullanıldı !!!
                        //double  rand1 = rand.nextDouble();

                        eval.crossValidateModel(classifier, data1, foldnumber, new Random(1));
                        FoodFitnesses[i]=eval.weightedFMeasure();
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return FoodFitnesses;
        
    }*/
    
    public double getFitnessOnebyOne(int[] food,int foldnumber){
        Classifier classifier;
        Evaluation eval;
        initialization_phase init=new initialization_phase();
        Instances data=init.readData();
        int N=data.numAttributes();
        double fitness=0.0;
        Instances data1=data;
        
        try{
            classifier=new IBk(); // sınıflandırıcı oluşturuldu
            eval=new Evaluation(data1); // degerlendirici olusturuldu
            
            data1=this.deleteZeros(food, N, data1);
            
            eval.crossValidateModel(classifier, data1, foldnumber, new Random(1));
            fitness=eval.weightedFMeasure();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return fitness;
    }
    
    // sıfırların denk geldiği attributeleri silen fonksiyon
    public Instances deleteZeros(int[] food,int N,Instances data2){
        int girildi=0;
        for (int i = 0; i < N-1; i++) {
            if(food[i]==0){                
                data2.deleteAttributeAt(i-girildi);
                girildi+=1;
            }else{
            }
        }
        return data2;
    }
}
