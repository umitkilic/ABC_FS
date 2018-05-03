/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abc_fs;

import static java.lang.Math.pow;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import weka.core.Instances;

/**
 *
 * @author Umit Kilic
 */
public class EmployedBees {
        
    foodsource      foodsources;
    // komşuluk bulma fonksiyonu
    public List<foodsource> determineNeighbors(int[][] foodSource,int dikey_limit,int yatay_limit,int attributeNumber,int foldnumber,double MR,String filepath){
        
        List<foodsource> foodsourceslist=new ArrayList<>();
        int N=attributeNumber-1; // oluşturulacak diziler için
        int count=0; // while döngüsü param
        int food2[];
        foodsourceslist.clear(); // her iterasyon öncesinde liste temizleniyor
        
        for (int j = 0; j < foodSource.length; j++) {
            getFitnessValue     gfv=new getFitnessValue();
            //double              main_fitness=0.0;
            double neigbor_fitness=0.0;
            int                 food[]=new int[N];
            
            // tek food oluşturuluyor
            System.arraycopy(foodSource[j], 0, food, 0, food.length);
            // AŞAĞIDAKİ İŞLEMLER ARTIK YENİ KOMŞULUKLARIN ÜRETİLMESİ İÇİN
                        
            int total=this.getTotalNeighborNumber(yatay_limit, dikey_limit); // TOPLAM OLUŞTURULACAK KOMSULUK SAYISI HESAPLANIYOR
            count=0;
            int sizeoffsl=foodsourceslist.size();
            
            
            while(count<total){ // komşuluk üretimi burada başlıyor
                if(count%dikey_limit==0 && count!=0){
                    food=this.getParent(count+sizeoffsl,foodsourceslist,j).getFoodsource();
                    food2=this.findNeighbors(food,MR);
                    
                }else{
                    // komşuluk bulma
                    food2=this.findNeighbors(food,MR);
                }
                
                neigbor_fitness=gfv.getFitnessOnebyOne(food2, foldnumber,filepath);
                foodsources=new foodsource(food2);
                foodsources.setFitnessval(neigbor_fitness);
                foodsourceslist.add(foodsources);
                count++;
            }// while bitiş            
        }
        
        return foodsourceslist;
    }
    
    // komsuluk bul (max değişecek attr sayısı burada ayarlanıyor)
    public int[] findNeighbors(int[] food,double MR){
        int food2[]=food.clone();
        Random  rand = new Random(1);
        double  n;        
        for (int i = 0; i < food2.length; i++) {
            n=rand.nextDouble();
            // rastgele gelen sayı büyükse değişiklik yap
            if (n<MR ) {
                food2[i]=1; 
            }
        }
        return food2;
    }

    // toplam komsuluk sayısı bulunuyor, determineNeighbors fonksiyonundaki while buna göre dönüyor
    public int getTotalNeighborNumber(int yatay,int dikey){
        double total=0;
        for (int i = 1; i <= yatay; i++) {total=total+pow(dikey,i);}            
        return (int) total;
    }

    // ağaç yapısına göre oluşturulduğundan komsulukların parent bilgisi bulunmalı. Parent buradan alınıyor
    public foodsource getParent(int childindex,List<foodsource> foodsourceslist,int j){
        int C=childindex;
        // foodsource=foodsourcelist.get((N*3+1)-1); // cocuk bul
        double d=((C)/3)-1;
        d=Math.ceil(d);
        int e=(int) d;
        e=26*j + e;
        foodsources=foodsourceslist.get(e); // parent bull
            
        return foodsources;
    }
    
    public int[][] findBestFoodSources(int[][] mainFoodSource,List<foodsource> foodsourceslist,int dikey_limit,int yatay_limit){
        int     listsize=foodsourceslist.size()/mainFoodSource.length;
        int     f[]=new int[mainFoodSource.length]; // geçici en iyiyi tutuyor
        int     mainfood[][]=new int[mainFoodSource.length][mainFoodSource.length]; // tüm en iyileri tutuyor
                
                
        for (int i = 0; i < mainFoodSource.length; i++) {
            int j=0;
            for (j = listsize*(i+1)-1; j>((listsize*i+dikey_limit)-1); j-=dikey_limit) {
                
                int c=0,maxfoodindex=0; double maxfitness=0.0; foodsource parentfood;
                
                // bu döngü ile cocukların en iyisinin indisi bulunuyor
                while(c<dikey_limit){ 
                    if (foodsourceslist.get(j-c).getFitnessval()>maxfitness) {
                        maxfoodindex=j-c; 
                        maxfitness=foodsourceslist.get(j-c).getFitnessval();}  
                    c++;
                }
                
                parentfood=this.getParent(maxfoodindex,foodsourceslist,i);
                if(foodsourceslist.get(maxfoodindex).getFitnessval()>parentfood.getFitnessval()){
                    this.getParent(maxfoodindex,foodsourceslist,i).setFoodsource(foodsourceslist.get(maxfoodindex).getFoodsource());
                    this.getParent(maxfoodindex,foodsourceslist,i).setFitnessval(foodsourceslist.get(maxfoodindex).getFitnessval());
                    f=this.getParent(maxfoodindex,foodsourceslist,i).getFoodsource().clone(); 
                }
                
                
            }
            double maxfitness=0.0;
            //int maxfoodindex=0;
            int s=0;
            while(s!=dikey_limit){
                if(foodsourceslist.get(j).getFitnessval()>maxfitness){
                    f=foodsourceslist.get(j).getFoodsource().clone();
                    //maxfoodindex=j;
                    maxfitness=foodsourceslist.get(j).getFitnessval();
                }else if(foodsourceslist.get(j).getFitnessval()==maxfitness){
                    if (this.numberof1s(foodsourceslist.get(j).getFoodsource())>this.numberof1s(f)) {
                        f=foodsourceslist.get(j).getFoodsource().clone();
                        //maxfoodindex=j;
                        maxfitness=foodsourceslist.get(j).getFitnessval();
                    }
                }
                j--;
                s++;
            }
            
            System.arraycopy(f, 0, mainfood[i], 0, f.length);
        }// for bitiş
        
        return mainfood;
    }
    
    public int numberof1s(int[] f){
        int t=0;
        for (int i = 0; i < f.length; i++) {
            t=t+f[i];
        }
        return t;
    }
}
