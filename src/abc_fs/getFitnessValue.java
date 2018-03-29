/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abc_fs;

import java.util.ArrayList;
import java.util.List;
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
    
    public double[] getFitness(Instances data,int[][] foodSource,int foldnumber){
        Classifier classifier;
        Evaluation eval;
        int N=data.numAttributes();
        double FoodFitnesses[]=new double[N-1];
        Instances data1=data;
        
        try{
            classifier=new IBk(); // sınıflandırıcı oluşturuldu
            eval=new Evaluation(data1); // degerlendirici olusturuldu
            
            
            for (int i = 0; i < N-1; i++) {
                        int food[]=new int[N-1];

                        // tek attribute oluşturuluyor
                        for (int j = 0; j < N-1; j++) {
                            food[j]=foodSource[i][j];
                        }

                       data1=this.deleteZeros(food, N, data1);

                        Debug.Random rand=new Debug.Random(1);

                        eval.crossValidateModel(classifier, data1, foldnumber, rand);
                        FoodFitnesses[i]=eval.weightedFMeasure();
                
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return FoodFitnesses;
        
    }
    
    public Instances deleteZeros(int[] food,int N,Instances data){
        int gidildi=0;
        for (int i = 0; i < N-1; i++) {
            if(food[i]==0){
            }
        }
        
        return data;
    }
}
