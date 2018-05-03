/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abc_fs;

import java.util.Arrays;
import java.util.List;
import weka.core.Instances;

/**
 *
 * @author Umit Kilic
 */
public class Steps {
    public Instances allSteps(Instances data,int dikey_limit, int yatay_limit, int iterationNumber,int foldnumber,String pathname,String newpathname,double MR){
        
        List<foodsource>    foodsourceslist; // her bir employed bee işleminden sonra oluşan toplu foodsource burada bulunacak.
        initialization_phase ip=new initialization_phase();
        int                 attributeSayisi=data.numAttributes(); // toplam attribute sayısı alınıyor
        int[][]             foodSource=new int[attributeSayisi-1][attributeSayisi-1];
        int[][]             foodSource_oBees=new int[attributeSayisi-1][attributeSayisi-1];
        int[][]             foodSource_sBees=new int[attributeSayisi-1][attributeSayisi-1];
        int[][]             foodSource_eBees=new int[attributeSayisi-1][attributeSayisi-1];
        double[]            foodFitnesses=new double[attributeSayisi-1];
        double[]            foodFitnesses_eBees=new double[attributeSayisi-1];
        double[]            foodFitnesses_oBees=new double[attributeSayisi-1];
        double[]            foodFitnesses_sBees=new double[attributeSayisi-1];
        getFitnessValue     gfv=new getFitnessValue();
        EmployedBees        e_bee=new EmployedBees();
       
        // besin kaynakları olusturuluyor
        foodSource=ip.createFoodSource(attributeSayisi, foodSource);
        
        int it_count=0;
        
        while (it_count<iterationNumber) {
            System.out.println("------------------------------------------------------------------------------------------------>>> ITERATION NUMBER= "+it_count);
            // ------>>> EMPLOYED BEES SECTION starts <<<----------- // 
            for (int i = 0; i < foodFitnesses.length; i++) { // üretilen başlangıç değerlerinin fitness değerleri bulunuyor
                int m[]=new int[foodSource[0].length];
                System.arraycopy(foodSource[i], 0, m, 0, foodSource[i].length);
                foodFitnesses[i]=gfv.getFitnessOnebyOne(m, foldnumber,pathname);
                }
            
            System.out.println("BAŞLANGIÇ:");
            for (int i=0;i<foodSource.length;i++) {
                System.out.print(i+". foodsource:"+ Arrays.toString(foodSource[i]) +" fitness:"+foodFitnesses[i]);
                System.out.println(" ");    
            }
            Long time1=System.currentTimeMillis();
            // foodsource: değiştirilecek besin kaynakları, attributeSayisi: dizileri oluşturmak için
            foodsourceslist=e_bee.determineNeighbors(foodSource,dikey_limit,yatay_limit,attributeSayisi,foldnumber,MR,pathname); 
            Long time2=System.currentTimeMillis();
            System.out.print("emp.bees deter. neig...Time:"+(time2-time1)+"...");
            
            time1=System.currentTimeMillis();
            //------------------------------- ziyaret edilenler arasında en iyiler bulunuyor
            foodSource_eBees=e_bee.findBestFoodSources(foodSource, foodsourceslist,dikey_limit,yatay_limit).clone();
            time2=System.currentTimeMillis();
            System.out.print("employed bee find best..Time:"+(time2-time1)+"...");
            time1=System.currentTimeMillis();
            for (int i = 0; i < foodFitnesses.length; i++) {
                int m[]=new int[foodSource_eBees[0].length];
                System.arraycopy(foodSource_eBees[i], 0, m, 0, m.length);
                foodFitnesses_eBees[i]=gfv.getFitnessOnebyOne(m, foldnumber,pathname);
            }
            
            // employedbee lerden gelen foodfitness lar başlangıç olan foodsource fitnesslardan daha iyi mi diye kontrol ediliyor
            for (int k = 0; k < foodSource.length; k++) {
                if (foodFitnesses_eBees[k]>foodFitnesses[k]) {
                    System.arraycopy(foodSource_eBees[k], 0, foodSource[k], 0, foodSource[k].length);
                    foodFitnesses[k]=foodFitnesses_eBees[k];    
                } else if(foodFitnesses_eBees[k]==foodFitnesses[k]){
                    int f[]=new int[foodSource.length];
                    int f_ebees[]=new int[foodSource.length];
                    System.arraycopy(foodSource[k], 0, f, 0, f.length);
                    System.arraycopy(foodSource_eBees[k], 0, f_ebees, 0, f_ebees.length);
                    if (this.numberof1s(f)>this.numberof1s(f_ebees)) {
                        System.arraycopy(f_ebees, 0, foodSource[k], 0, f_ebees.length);
                        foodFitnesses[k]=foodFitnesses_eBees[k];
                    }
                }
            }
            time2=System.currentTimeMillis();
            System.out.println("employed bee section end..Time:"+(time2-time1)+"...");
            // ---->>> EMPLOYED BEE SECTION ends <<<<-----  ///
                
            System.out.print("onlooker bee section start..");
            // ----->>> ONLOOKEER BEE SECTION starts  <<<---- /////
            
            int onlookerfoods[][]=new int[foodSource.length][foodSource.length];
            OnlookerBees onlookers=new OnlookerBees();
            onlookerfoods=onlookers.onLook(foodSource, foodFitnesses,foldnumber, pathname,MR).clone();
            double onlookerfoodfitnesses[]=new double[onlookerfoods[0].length];
            
            for (int i = 0; i < onlookerfoodfitnesses.length; i++) {
                int m[]=new int[onlookerfoods[0].length];
                System.arraycopy(onlookerfoods[i], 0, m, 0, m.length);
                onlookerfoodfitnesses[i]=gfv.getFitnessOnebyOne(m, foldnumber,pathname);
            }
            
            // foodsource: değiştirilecek besin kaynakları, attributeSayisi: dizileri oluşturmak için
            time1=System.currentTimeMillis();
            foodsourceslist=e_bee.determineNeighbors(onlookerfoods,dikey_limit,yatay_limit,attributeSayisi,foldnumber,MR,pathname);
            time2=System.currentTimeMillis();
            System.out.print("onlooker bee det. neig...Time:"+(time2-time1)+"...");
            // ziyaret edilenler arasında en iyiler bulunuyor
            time1=System.currentTimeMillis();
            foodSource_oBees=e_bee.findBestFoodSources(onlookerfoods, foodsourceslist,dikey_limit,yatay_limit).clone();
            time2=System.currentTimeMillis();
            System.out.print("onlooker bee find best..Time:"+(time2-time1)+"...");
            
            time1=System.currentTimeMillis();
            for (int i = 0; i < onlookerfoods.length; i++) {
                int m[]=new int[foodSource_oBees[0].length];
                System.arraycopy(foodSource_oBees[i], 0, m, 0, m.length);
                foodFitnesses_oBees[i]=gfv.getFitnessOnebyOne(m, foldnumber,pathname);
            }
            
            for (int k = 0; k < onlookerfoods.length; k++) {
                if (foodFitnesses_oBees[k]>onlookerfoodfitnesses[k]) {
                    System.arraycopy(foodSource_oBees[k], 0, onlookerfoods[k], 0, onlookerfoods[k].length);
                    onlookerfoodfitnesses[k]=foodFitnesses_oBees[k];
                } else if(foodFitnesses_oBees[k]==onlookerfoodfitnesses[k]){
                    int f_oBees[]=new int[foodSource_oBees.length];
                    int f_news[]=new int[onlookerfoods.length];
                    System.arraycopy(foodSource_oBees[k], 0, f_oBees, 0, f_oBees.length);
                    System.arraycopy(onlookerfoods[k], 0, f_news, 0, f_news.length);
                    if(this.numberof1s(f_news)>this.numberof1s(f_oBees)){
                        System.arraycopy(f_oBees, 0, onlookerfoods[k], 0, f_oBees.length);
                        onlookerfoodfitnesses[k]=foodFitnesses_sBees[k];
                    }
                }
            }
            
            // ---- onlooker ve employed bee sonuçları karşılaştırılıyor
            
            initialization_phase ip3=new initialization_phase();
        
            for (int i = 0; i < foodSource.length; i++) {
                int oldminindex=ip3.findMin(foodFitnesses);
                double oldmin=foodFitnesses[oldminindex];
                int newmaxindex=ip3.findMax(onlookerfoodfitnesses);
                double newmax=onlookerfoodfitnesses[newmaxindex];
                if (newmax>oldmin) {
                    System.arraycopy(onlookerfoods[newmaxindex], 0, foodSource[oldminindex], 0, foodSource[0].length);
                    foodFitnesses[oldminindex]=onlookerfoodfitnesses[newmaxindex];
                    onlookerfoodfitnesses[newmaxindex]=0.0;
                }else if(newmax==oldmin){
                    int f_old[]=new int[foodSource.length];
                    int f_onew[]=new int[foodSource.length];
                    System.arraycopy(foodSource[oldminindex], 0, f_old, 0, f_old.length);
                    System.arraycopy(onlookerfoods[newmaxindex], 0, f_onew, 0, f_onew.length);
                    if (this.numberof1s(f_onew)<this.numberof1s(f_old)) {
                        System.arraycopy(onlookerfoods[newmaxindex], 0, foodSource[oldminindex], 0, onlookerfoods.length);
                        foodFitnesses[oldminindex]=onlookerfoodfitnesses[newmaxindex];
                        onlookerfoodfitnesses[newmaxindex]=0.0;
                    }
                }
                
            }
            time2=System.currentTimeMillis();
            System.out.println("onlooker bee section end..Time:"+(time2-time1)+"...");
            // ----->>> ONLOOKEER BEE SECTION ends  <<<---- /////
            
            System.out.print("scout bee section start..");
            ///---->>>  SCOUT BEE SECTION starts <<<<------ //
            int newfoodsources[][];
            ScoutBees scout=new ScoutBees();
            
            newfoodsources=scout.createRandomFoodSource(attributeSayisi, foodSource,MR).clone(); // scout bee ler rastgele yeni kaynaklar keşfediyor
            
            
            double newfoodfitnesses[]=new double[newfoodsources[0].length];
            for (int i = 0; i < newfoodfitnesses.length; i++) {
                int m[]=new int[newfoodsources[0].length];
                System.arraycopy(newfoodsources[i], 0, m, 0, m.length);
                newfoodfitnesses[i]=gfv.getFitnessOnebyOne(m, foldnumber,pathname);
            }

            // foodsource: değiştirilecek besin kaynakları, attributeSayisi: dizileri oluşturmak için
            time1=System.currentTimeMillis();
            foodsourceslist=e_bee.determineNeighbors(newfoodsources,dikey_limit,yatay_limit,attributeSayisi,foldnumber,MR,pathname);
            time2=System.currentTimeMillis();
            System.out.print("scout bee section det. neig..Time:"+(time2-time1)+"...");
            // ziyaret edilenler arasında en iyiler bulunuyor
            time1=System.currentTimeMillis();
            foodSource_sBees=e_bee.findBestFoodSources(newfoodsources, foodsourceslist,dikey_limit,yatay_limit).clone();
            time2=System.currentTimeMillis();
            System.out.print("scout bee find best..Time:"+(time2-time1)+"...");
            
            time1=System.currentTimeMillis();
            for (int i = 0; i < newfoodfitnesses.length; i++) {
                int m[]=new int[foodSource_sBees[0].length];
                System.arraycopy(foodSource_sBees[i], 0, m, 0, m.length);
                foodFitnesses_sBees[i]=gfv.getFitnessOnebyOne(m, foldnumber,pathname);
            }
            
            // scout ların ürettiği yeni kaynakların komşulukları yeni kaynaklardan daha mı iyi diye kontrol ediliyor
            for (int k = 0; k < newfoodsources.length; k++) {
                if (foodFitnesses_sBees[k]>newfoodfitnesses[k]) {
                    System.arraycopy(foodSource_sBees[k], 0, newfoodsources[k], 0, newfoodsources[k].length);
                    newfoodfitnesses[k]=foodFitnesses_sBees[k];
                } else if(foodFitnesses_sBees[k]==newfoodfitnesses[k]){
                    int f_sBees[]=new int[foodSource_sBees.length];
                    int f_news[]=new int[newfoodsources.length];
                    System.arraycopy(foodSource_sBees[k], 0, f_sBees, 0, f_sBees.length);
                    System.arraycopy(newfoodsources[k], 0, f_news, 0, f_news.length);
                    if(this.numberof1s(f_news)>this.numberof1s(f_sBees)){
                        System.arraycopy(f_sBees, 0, newfoodsources[k], 0, f_sBees.length);
                        newfoodfitnesses[k]=foodFitnesses_sBees[k];
                    }
                }
            }
        
        
            // -------------------------------------------------------------    scout ve old karşılaştırıp global en iyiyi bulma evresi
            initialization_phase ip2=new initialization_phase();
        
            for (int i = 0; i < foodSource.length; i++) {
                int oldminindex=ip2.findMin(foodFitnesses);
                double oldmin=foodFitnesses[oldminindex];
                int newmaxindex=ip2.findMax(newfoodfitnesses);
                double newmax=newfoodfitnesses[newmaxindex];
                if (newmax>oldmin) {
                    System.arraycopy(newfoodsources[newmaxindex], 0, foodSource[oldminindex], 0, foodSource[0].length);
                    foodFitnesses[oldminindex]=newfoodfitnesses[newmaxindex];
                    newfoodfitnesses[newmaxindex]=0.0;
                }else if(newmax==oldmin){
                    int f_old[]=new int[foodSource.length];
                    int f_new[]=new int[foodSource.length];
                    System.arraycopy(foodSource[oldminindex], 0, f_old, 0, f_old.length);
                    System.arraycopy(newfoodsources[newmaxindex], 0, f_new, 0, f_new.length);
                    if (this.numberof1s(f_new)<this.numberof1s(f_old)) {
                        System.arraycopy(newfoodsources[newmaxindex], 0, foodSource[oldminindex], 0, newfoodsources.length);
                        foodFitnesses[oldminindex]=newfoodfitnesses[newmaxindex];
                        newfoodfitnesses[newmaxindex]=0.0;
                    }
                }
                
            }
            time2=System.currentTimeMillis();
            System.out.println("scout bee section end..Time:"+(time2-time1)+"...");
            
            
            System.out.println("\nSONUÇ:");
            for (int i = 0; i < foodSource.length; i++) {
                for (int j = 0; j < foodSource[0].length; j++) {
                    System.out.print(foodSource[i][j]);
                }
                System.out.print(" fitness:"+ foodFitnesses[i]);
                System.out.println(" ");
            }
        
           
        it_count++;
        }// en dış while bitiş 
        
        
        
        double max=0.0;
        int index=0;
        int numberof1sMaxHave=attributeSayisi-1;
        int bestone[]=new int[foodSource[0].length];
        int selectedFeatureVector[]=new int[foodSource[0].length];
        
        for (int i = 0; i < foodFitnesses.length; i++) {
            
            if (foodFitnesses[i]>max) {
                max=foodFitnesses[i];
                index=i;
                System.arraycopy(foodSource[i], 0, selectedFeatureVector, 0, bestone.length);
                numberof1sMaxHave=this.numberof1s(selectedFeatureVector);
            }else if(foodFitnesses[i]==max){
                System.arraycopy(foodSource[i], 0, bestone, 0, bestone.length);
                int bestone1s=this.numberof1s(bestone);
                if(bestone1s<numberof1sMaxHave){
                    max=foodFitnesses[i];
                    index=i;
                    numberof1sMaxHave=bestone1s;
                }
            }
        }
        
        
        System.out.println("SEÇİLENLER:");
        for (int i = 0; i < foodSource[0].length; i++) {
            System.arraycopy(foodSource[index], 0, selectedFeatureVector, 0, selectedFeatureVector.length);
        }
        
        System.out.println(Arrays.toString(selectedFeatureVector)+" fmeasure:"+foodFitnesses[index]);
        ip.createARFF(data,selectedFeatureVector,newpathname);
        return data;
    }
    
    public int numberof1s(int[] f){
        int t=0;
        for (int i = 0; i < f.length; i++) {
            t=t+f[i];
        }
        return t;
    }
}
