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
        int dikeycount=0,yataycount=0,count=0; // while döngüsü param
        int food2[];
        foodsourceslist.clear(); // her iterasyon öncesinde liste temizleniyor
        
        for (int j = 0; j < foodSource.length; j++) {
            getFitnessValue     gfv=new getFitnessValue();
            double              main_fitness=0.0,neigbor_fitness=0.0;
            int                 food[]=new int[N];
            int                 best_neigbor_food[]=new int[N];  // her defasında en iyi komşuluk burada tutulacak ve ana food dan da iyiyse ana listeye eklenecek
            
            
            // tek food oluşturuluyor
            System.arraycopy(foodSource[j], 0, food, 0, food.length);
            best_neigbor_food=food.clone(); // önceki ve sonraki turlardaki komşuluklar buraya aktarılmasın diye başlangıçta main food u en iyisi seçiyoruz
            main_fitness=gfv.getFitnessOnebyOne(food, foldnumber,filepath);
            
            
            // AŞAĞIDAKİ İŞLEMLER ARTIK YENİ KOMŞULUKLARIN ÜRETİLMESİ İÇİN
            
            
            
            dikeycount=dikey_limit; // dikey count dikey olarak ne kadar komsuluk üretileceğini belirler
            yataycount=yatay_limit; // yatak count yatay olarak ne kadar adım gidileceğini belirler
            
            int total=this.getTotalNeighborNumber(yataycount, dikeycount); // TOPLAM OLUŞTURULACAK KOMSULUK SAYISI HESAPLANIYOR
            
            count=0;
            int sizeoffsl=foodsourceslist.size();
            while(count<total){ // komşuluk üretimi burada başlıyor
                if(count%dikeycount==0 && count!=0){
                    food=this.getParent(count+sizeoffsl,foodsourceslist,j).getFoodsource();
                    food2=this.findNeighbors(food,j,MR);
                }else{
                    // komşuluk bulma
                    food2=this.findNeighbors(food,j,MR);
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
    public int[] findNeighbors(int[] food,int j,double MR){
        int food2[]=food.clone();
        Random  rand = new Random();
        double  n;        
        //int numofchange=0; // değişiklik sayısı
        for (int i = 0; i < food2.length; i++) {
            //System.out.println("for giris!");
            /*if (i==j) { // eğer parent taki ana 1 değişmesi istenmezde bu kullanılır
                continue;
            }*/
            n=rand.nextDouble();

            // rastgele gelen sayı büyükse değişiklik yap
            if (n>MR ) {
                if(food2[i]==1) food2[i]=0; 
                else food2[i]=1; 
                //numofchange++;
            } 

            /*if(numofchange>=3){
                break;
            }*/
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
        //System.out.print("\n gelen child:"+C+" dondurulen index: "+e+" ");
        foodsources=foodsourceslist.get(e); // parent bull
            
        return foodsources;
    }
    
    public int[][] findBestFoodSources(int[][] mainFoodSource,List<foodsource> foodsourceslist,int dikey_limit,int yatay_limit){
        int     listsize=foodsourceslist.size()/mainFoodSource.length;
        int     dikeycount=dikey_limit;
        int     f[]=new int[mainFoodSource.length]; // geçici en iyiyi tutuyor
        int     mainfood[][]=new int[mainFoodSource.length][mainFoodSource.length]; // tüm en iyileri tutuyor
                
                
        for (int i = 0; i < mainFoodSource.length; i++) {
            //System.out.println("j:"+(listsize*(i+1)-1)+" edge:"+((listsize*i+dikey_limit)-3));
            int j=0;
            for (j = listsize*(i+1)-1; j>((listsize*i+dikey_limit)-1); j-=dikeycount) {
                
                int c=0,maxfoodindex=0; double maxfitness=0.0; foodsource parentfood;
                
                // bu döngü ile cocukların en iyisinin indisi bulunuyor
                while(c<dikeycount){ 
                    if (foodsourceslist.get(j-c).getFitnessval()>maxfitness) {
                        maxfoodindex=j-c; 
                        maxfitness=foodsourceslist.get(j-c).getFitnessval();}  
                    c++;
                }
                
                parentfood=this.getParent(maxfoodindex,foodsourceslist,i);
                //System.out.println("max in child:"+maxfoodindex+"child fit.:"+ foodsourceslist.get(maxfoodindex).getFitnessval()+" parent fit.:"+parentfood.getFitnessval());
                if(foodsourceslist.get(maxfoodindex).getFitnessval()>parentfood.getFitnessval()){
                    this.getParent(maxfoodindex,foodsourceslist,i).setFoodsource(foodsourceslist.get(maxfoodindex).getFoodsource());
                    this.getParent(maxfoodindex,foodsourceslist,i).setFitnessval(foodsourceslist.get(maxfoodindex).getFitnessval());
                    f=this.getParent(maxfoodindex,foodsourceslist,i).getFoodsource().clone(); 
                }
                
                
            }
            double maxfitness=0.0;
            int maxfoodindex=0;
            int s=0;
            while(s!=dikey_limit){
                //System.out.println(j+". parent food:"+Arrays.toString(foodsourceslist.get(j).getFoodsource())+ " fitness:"+foodsourceslist.get(j).getFitnessval());
                if(foodsourceslist.get(j).getFitnessval()>maxfitness){
                    f=foodsourceslist.get(j).getFoodsource().clone();
                    maxfoodindex=j;
                    maxfitness=foodsourceslist.get(j).getFitnessval();
                }else if(foodsourceslist.get(j).getFitnessval()==maxfitness){
                    if (this.numberof1s(foodsourceslist.get(j).getFoodsource())>this.numberof1s(f)) {
                        f=foodsourceslist.get(j).getFoodsource().clone();
                        maxfoodindex=j;
                        maxfitness=foodsourceslist.get(j).getFitnessval();
                    }
                }
                j--;
                s++;
            }
            
            System.arraycopy(f, 0, mainfood[i], 0, f.length);
            //System.out.println("secildi:"+ Arrays.toString(f)+" foodlistten:"+Arrays.toString(foodsourceslist.get(maxfoodindex).getFoodsource())+" fitnes:"+foodsourceslist.get(maxfoodindex).getFitnessval());
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
