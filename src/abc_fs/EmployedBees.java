/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abc_fs;

import static java.lang.Math.pow;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Umit Kilic
 */
public class EmployedBees {
        foodsource      foodsources;
        List<foodsource> foodsourceslist=new ArrayList<>();
    
    // komşuluk bulma fonksiyonu
    public List<foodsource> determineNeighbors(int[][] foodSource,int dikey_limit,int yatay_limit,int attributeNumber){
        System.out.println("burada6-1");
        int N=attributeNumber-1; // oluşturulacak diziler için
        int foldnumber=10;
        int dikeycount=0,yataycount=0,count=0; // while döngüsü param
        int food2[];
        System.out.println("burada6-2");
        foodsourceslist.clear(); // her iterasyon öncesinde liste temizleniyor
        for (int j = 0; j < foodSource.length; j++) {
            System.out.println("burada6-3"+" j="+j+ " lenght"+ foodSource.length);
            getFitnessValue     gfv=new getFitnessValue();
            double              main_fitness=0.0,neigbor_fitness=0.0;
            int                 food[]=new int[N];
            int                 best_neigbor_food[]=new int[N];  // her defasında en iyi komşuluk burada tutulacak ve ana food dan da iyiyse ana listeye eklenecek
            
            
            // tek food oluşturuluyor
            food=this.getSingleFoodSource(j, N, foodSource); // tek bir food çekiliyor
            System.out.println("burada6-4");
            best_neigbor_food=food.clone(); // önceki ve sonraki turlardaki komşuluklar buraya aktarılmasın diye başlangıçta main food u en iyisi seçiyoruz
            main_fitness=gfv.getFitnessOnebyOne(food, 10);
            System.out.println("burada6-5");
            // AŞAĞIDAKİ İŞLEMLER ARTIK YENİ KOMŞULUKLARIN ÜRETİLMESİ İÇİN
            
            
            
            dikeycount=dikey_limit; // dikey count dikey olarak ne kadar komsuluk üretileceğini belirler
            yataycount=yatay_limit; // yatak count yatay olarak ne kadar adım gidileceğini belirler
            
            int total=this.getTotalNeighborNumber(yataycount, dikeycount); // TOPLAM OLUŞTURULACAK KOMSULUK SAYISI HESAPLANIYOR
            int countd=0,county=0;
            count=0;
            System.out.println("burada6-6");
            while(count<total){
                System.out.println("burada6-7"+"count:"+count+" total:"+total);
                //System.out.println("COUNT:"+ count +" COUNTD:"+ countd + " COUNTY:"+ county);
                if(count%dikeycount==0 && count!=0){
                    food=this.getParent(count+1).getFoodsource();
                    food2=this.findNeighbors(food);
                }else{
                    // komşuluk bulma
                    food2=this.findNeighbors(food);
                }
                System.out.println("burada6-8");
                foodsources=new foodsource(food2);
                
                //System.out.print("\nOluşturulan food"+"("+count+")"); for (int i = 0; i < N; i++) {System.out.print(food2[i]);}

                System.out.println("burada6-9");
                neigbor_fitness=gfv.getFitnessOnebyOne(food2, foldnumber);      foodsources.setFitnessval(neigbor_fitness);
                //System.out.println("\n neighbor fitness:"+neigbor_fitness +" main fitness:"+main_fitness);
                foodsourceslist.add(foodsources);
                System.out.println("burada6-10");
                //if (neigbor_fitness>main_fitness) { System.out.println("Neighbor daha iyi ("+a+")\n\n\n"); best_neigbor_food=food2.clone(); main_fitness=neigbor_fitness;}
                count++;
                countd+=1;
            }// while bitiş
                    
                    //System.out.println("\nBest One (Neighbor ve Main dahil): "); for (int i = 0; i < N; i++) {System.out.print(best_neigbor_food[i]);}
        }
        
        return foodsourceslist;
    }
    
    // row: çekilecek satır, size: dönecek dizinin boyutu, foodSource: foodun içinden çekeleceği foodSource
    public int[] getSingleFoodSource(int row,int size,int[][] foodSource){
        int singleFoodSource[]=new int[size];
        
        for (int k  = 0; k < size; k++) {singleFoodSource[k]=foodSource[row][k]; }
        
        return singleFoodSource;
    }
    
    // komsuluk bul (max değişecek attr sayısı burada ayarlanıyor)
    public int[] findNeighbors(int[] food){
        int food2[]=food.clone();
        Random  rand = new Random();
        double  n = rand.nextDouble();
        double  MR=0.5; // modification rate
        
        int numofchange=0; // değişiklik sayısı
        for (int i = 0; i < food2.length; i++) {
            //System.out.println("for giris!");
            n=rand.nextDouble();

            // rastgele gelen sayı büyükse değişiklik yap
            if (n>MR /*&& food2[i]!=1*/) {if(food2[i]==1) {food2[i]=0;} else {food2[i]=1;}numofchange++;} 

            //System.out.println("num of change:"+numofchange);
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
    public foodsource getParent(int childindex){
        int C=childindex;
        //System.out.print("\nParent of "+ C +": ");
        // foodsource=foodsourcelist.get((N*3+1)-1); // cocuk bul
        double d=((C-1)/3);
        d=Math.ceil(d);
        int e=(int) d;
        foodsources=foodsourceslist.get(e); // parent bull
            
        return foodsources;
    }
    
    public int[][] findBestFoodSources(int[][] mainFoodSource,List<foodsource> foodsourceslist,int dikey_limit,int yatay_limit){
        int     listsize=foodsourceslist.size()/mainFoodSource.length;
        int     dikeycount=dikey_limit;
        int     total=0;
        int     f[]=new int[mainFoodSource.length]; // geçici en iyiyi tutuyor
        int     mainfood[][]=new int[mainFoodSource.length][mainFoodSource.length]; // tüm en iyileri tutuyor
        
        total=this.getTotalNeighborNumber(yatay_limit, dikey_limit);
        
        for (int i = 0; i < mainFoodSource.length; i++) {
            System.out.println("\n"+i+". mainfood >>>");
            for (int j = listsize*(i+1)-1; j>((listsize*i+dikey_limit)-1); j-=dikeycount) {
                int c=0,maxfoodindex=0; double minfitness=0.0; foodsource parentfood;
                
                // bu döngü ile cocukların en iyisinin indisi bulunuyor
                while(c<dikeycount){ if (foodsourceslist.get(j-c).getFitnessval()>minfitness) {maxfoodindex=j-c;}  c++;}
                
                parentfood=this.getParent(maxfoodindex);
                if(foodsourceslist.get(maxfoodindex).getFitnessval()>parentfood.getFitnessval()){
                    this.getParent(maxfoodindex).setFoodsource(foodsourceslist.get(maxfoodindex).getFoodsource());
                    this.getParent(maxfoodindex).setFitnessval(foodsourceslist.get(maxfoodindex).getFitnessval());
                    f=this.getParent(maxfoodindex).getFoodsource().clone();
                }
            }
            
            for (int g = 0; g < mainFoodSource[0].length; g++) {
                mainfood[i][g]=f[g];
            }
            
            System.out.print("\n\n------------------------------------------------------\n"+i+". adım için en iyisi: ");
                    
                    for (int k = 0; k < mainFoodSource.length; k++) {
                        System.out.print(mainfood[i][k]);
                    }
                    getFitnessValue gfv=new getFitnessValue();
                    System.out.print(" its fitness: "+gfv.getFitnessOnebyOne(f, 10));
        }// for bitiş
        
        return mainfood;
    }
}
