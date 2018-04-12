/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abc_fs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils;

/**
 *
 * @author Umit Kilic
 */
public class initialization_phase {
    
    // besin kaynağı oluşturuluyor. Feature sayısına eşit olacak
    public int[][] createFoodSource(int attributeSayisi,int foodSource[][]){ 
        
        foodSource=new int[attributeSayisi-1][attributeSayisi-1]; // feature sayısı kadar besin kaynağı oluşturduk
        
        // food source içini dolduracağız
        for(int i=0;i<foodSource.length;i++){
            foodSource[i][i]=1;
        }
        return foodSource;
    }
    
    // veriyi oku özellikleri al
    public Instances readData(String name){  
        ConverterUtils.DataSource   source;
        Instances                   data=null;
        
        try {
            String path=name;
            source=new ConverterUtils.DataSource(path);
            data=source.getDataSet();
            data.setClassIndex(data.numAttributes()-1); // class indexi belirleniyor
            
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
          
        return data;
    }
    
    public int findMin(double[] fit){
        
        double min=1.0;
        int index=0;
        for (int i = 0; i < fit.length; i++) {
            if (fit[i]<min) {
                min=fit[i];
                index=i;
            }
        }
        return index;
    }
    
    public int findMax(double[] fit){
        double max=0.0;
        int index=0;
        for (int i = 0; i < fit.length; i++) {
            if (fit[i]>max) {
                max=fit[i];
                index=i;
            }
        }
        return index;
    }
    
    public void createARFF(Instances instance,int[] selectedFeatureVector,String newfilepath){
        List<Integer> deleteIndexes=new ArrayList<Integer>();
        
        for(int i=0;i<selectedFeatureVector.length;i++){
            if (selectedFeatureVector[i]==0) {
                deleteIndexes.add(i);
            }
        }
        int girildi=0;
        try {
            Instances dataset=instance;
            for (int i = 0; i < deleteIndexes.size(); i++) {
                dataset.deleteAttributeAt(deleteIndexes.get(i)-girildi);
                girildi++;
            }
            
            ArffSaver saver=new ArffSaver();
            saver.setInstances(dataset);
            saver.setFile(new File(newfilepath));
            saver.writeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
