/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abc_fs;

import java.io.BufferedWriter;
import java.io.File;
import java.util.List;
import weka.core.Instances;
import java.io.FileInputStream;
import java.io.FileWriter;

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
        int                 iterationNumber=100;
        int                 foldnumber=10;
        double              MR=0.3;
        String              filepath="C:/SCADI.arff";
        int                 fileno=0;
        String              filenostr=Integer.toString(fileno);
        String              newfilepath="/file"+filenostr+".arff";
        Instances data;
        initialization_phase ip=new initialization_phase();
        data=ip.readData(filepath); // veri alınıyor
        Steps s=new Steps();
        
        int                 dikey_limit=3; // aşağı doğru kaç komsuluk bulunacak
        int                 yatay_limit=3; // geriye doğru kaç komşuluk bulunacak
        Long start=System.currentTimeMillis();
        data=s.allSteps(data,dikey_limit, yatay_limit, iterationNumber, foldnumber,filepath,newfilepath,MR);
        /*Instances data2=data;
        System.out.println("numofattr2:"+data2.numAttributes());
        data.deleteAttributeAt(0);
        System.out.println("numofattr2:"+data2.numAttributes()+" numofattr1:"+data.numAttributes());*/
        Long end=System.currentTimeMillis();
        System.out.println("numofattr: "+data.numAttributes()+" total Time:"+(end-start));
    }
    
    
}
