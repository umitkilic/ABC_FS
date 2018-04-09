/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abc_fs;

import java.util.logging.Level;
import java.util.logging.Logger;
import weka.core.Instances;
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
    public Instances readData(){  
        ConverterUtils.DataSource   source;
        Instances                   data=null;
        
        try {
            String path="hypothyroid.arff";
            source=new ConverterUtils.DataSource(path);
            data=source.getDataSet();
            data.setClassIndex(data.numAttributes()-1); // class indexi belirleniyor
            
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
          
        return data;
    }
    
    public int findMin(double[] fit){
        //System.out.print("gelen -> ");
        //for (int i = 0; i < fit.length; i++) {System.out.println(fit[i]);}
        double min=1.0;
        int index=0;
        for (int i = 0; i < fit.length; i++) {
            //System.out.print("fit i="+fit[i]+ " min="+min);
            if (fit[i]<min) {
                min=fit[i];
                //System.out.println(" -> min den kucuk. index="+i);
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
    
    
}
