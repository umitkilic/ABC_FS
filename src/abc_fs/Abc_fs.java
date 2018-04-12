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
        int                 dikey_limit=3; // aşağı doğru kaç komsuluk bulunacak
        int                 yatay_limit=3; // geriye doğru kaç komşuluk bulunacak
        int                 iterationNumber=5;
        int                 foldnumber=4;
        String              filepath="C:/bap_calisan_2017_Mustafa.arff";
        String              newfilepath="/file1.arff";
        Instances data;
        initialization_phase ip=new initialization_phase();
        data=ip.readData(filepath); // veri alınıyor
        Steps s=new Steps();
        data=s.allSteps(data,dikey_limit, yatay_limit, iterationNumber, foldnumber,filepath,newfilepath);
        System.out.println("numofattr: "+data.numAttributes());
        
        filepath=newfilepath;
        newfilepath="/file2.arff";
        data=s.allSteps(data,dikey_limit, yatay_limit, iterationNumber, foldnumber,filepath,newfilepath);
        System.out.println("numofattr: "+data.numAttributes());
        
        filepath=newfilepath;
        newfilepath="/file3.arff";
        data=s.allSteps(data,dikey_limit, yatay_limit, iterationNumber, foldnumber,filepath,newfilepath);
        System.out.println("numofattr: "+data.numAttributes());
        
       /* Instances           data;
        List<foodsource>    foodsourceslist; // her bir employed bee işleminden sonra oluşan toplu foodsource burada bulunacak.
        initialization_phase ip=new initialization_phase();
        data=ip.readData(); // veri alınıyor
        int                 attributeSayisi=data.numAttributes(); // toplam attribute sayısı alınıyor
        double[]            foodFitnesses=new double[attributeSayisi-1];
        int[][]             foodSource=new int[attributeSayisi-1][attributeSayisi-1];
        int[][]             foodSource_eBees=new int[attributeSayisi-1][attributeSayisi-1];
        double[]            foodFitnesses_eBees=new double[attributeSayisi-1];
        getFitnessValue     gfv=new getFitnessValue();
        EmployedBees        e_bee=new EmployedBees();
        int[][]             foodsource_main=new int[attributeSayisi-1][attributeSayisi-1];
        double[]            foodFitnesses_main=new double[attributeSayisi-1];*/
        
        
        
    }
    
    
}
