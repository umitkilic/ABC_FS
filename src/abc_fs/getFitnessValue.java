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
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IBk;
import weka.classifiers.meta.Bagging;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.rules.OneR;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.classifiers.trees.RandomTree;
import weka.core.Debug;
import weka.core.Instances;

/**
 *
 * @author Umit Kilic
 */
public class getFitnessValue {
    
    public double getFitnessOnebyOne(int[] food,int foldnumber,String filepath){
        Classifier classifier;
        Evaluation eval;
        initialization_phase init=new initialization_phase();
        Instances data=init.readData(filepath);
        int N=data.numAttributes();
        double fitness=0.0;
        //Instances data1=data;
        
        try{
            
            classifier=new BayesNet();                // * +
            //classifier=new MultilayerPerceptron();    // ***** 
            //classifier=new RandomTree();              // * +
            //classifier=new J48();                     // ** +
            //classifier=new Bagging();                 // *** +
            //classifier=new NaiveBayes();              // * +
            //classifier=new DecisionTable();           // ** + (ayarlanacak)
            //classifier=new SMO();                     // **** +
            //classifier=new RandomForest();              // **** -
            //classifier=new OneR();                      // ** +
            //classifier=new IBk(3); // sınıflandırıcı oluşturuldu // * +
            data=this.deleteZeros(food, N, data);
            eval=new Evaluation(data); // degerlendirici olusturuldu
            eval.crossValidateModel(classifier, data, foldnumber, new Random(1));
            fitness=eval.weightedFMeasure();
            //fitness=eval.pctCorrect();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return fitness;
    }
    
    // sıfırların denk geldiği attributeleri silen fonksiyon
    public Instances deleteZeros(int[] food,int N,Instances data2){
        List<Integer> deleteIndexes=new ArrayList<Integer>();
        
        for(int i=0;i<food.length;i++){
            if (food[i]==0) {
                deleteIndexes.add(i);
            }
        }
        
        int girildi=0;
        
        Instances dataset=data2;
        for (int i = 0; i < deleteIndexes.size(); i++) {
            dataset.deleteAttributeAt(deleteIndexes.get(i)-girildi);
            girildi++;
        }
        
        return dataset;
        
    }
}
