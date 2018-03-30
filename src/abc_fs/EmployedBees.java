/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abc_fs;

import java.util.Random;

/**
 *
 * @author Umit Kilic
 */
public class EmployedBees {
    
    
    // komşuluk bulma fonksiyonu
    public void determineNeighbors(int[][] foodSource,int MAX_LIMIT,int attributeNumber){
        double MR=0.5; // modification rate
        int N=attributeNumber-1; // oluşturulacak diziler için
        
        Random rand = new Random();
        double  n = rand.nextDouble();
        int a=0; // while döngüsü param
        
        int food2[];
        
        for (int j = 0; j < N; j++) {
            getFitnessValue gfv=new getFitnessValue();
            double main_fitness=0.0,neigbor_fitness=0.0;
            int food[]=new int[N];
            int best_neigbor_food[]=new int[N];  // her defasında en iyi komşuluk burada tutulacak ve ana food dan da iyiyse ana listeye eklenecek
            // tek food oluşturuluyor
            System.out.print("\n\nBaşlangıç food:");
            for (int k  = 0; k < N; k++) {
                food[k]=foodSource[j][k];
                System.out.print(food[k]);
            }
            best_neigbor_food=food.clone(); // önceki ve sonraki turlardaki komşuluklar buraya aktarılmasın diye başlangıçta main food u en iyisi seçiyoruz
            main_fitness=gfv.getFitnessOnebyOne(food, 10);
            System.out.print("\nbaşlangıç food fitness: "+main_fitness);
            
                System.out.println("\n"); 
                
                    a=0;
                    while(a<MAX_LIMIT){
                        
                        food2=food.clone(); // elimizdeki food source ü kaybetmemek için yedeği ile çalışıyoruz
                        
                        System.out.println("İşleme giren food ("+a+"): ");
                        for (int i = 0; i < N; i++) {
                            System.out.print(food2[i]);
                        }
                        
                        

                        for (int i = 0; i < N; i++) {
                        n=rand.nextDouble();

                            // rastgele gelen sayı büyükse değişiklik yap
                            if (n>MR && food2[i]!=1) {
                                food2[i]=1;
                            }
                        }

                        //burada değşiklik yapılmış food elimizde
                        System.out.println("\n işlem sonucu food ("+a+"): ");
                        for (int i = 0; i < N; i++) {
                            System.out.print(food2[i]);
                        }
                        
                        
                        neigbor_fitness=gfv.getFitnessOnebyOne(food2, 10);
                        System.out.println("\n neighbor fitness:"+neigbor_fitness +" main fitness:"+main_fitness);
                        if (neigbor_fitness>main_fitness) {
                            System.out.println("Neighbor daha iyi ("+a+")\n\n\n");
                            best_neigbor_food=food2.clone();
                            main_fitness=neigbor_fitness;
                        }
                        a++;
            }// while bitiş
                    
                    System.out.println("\n Best One (Neighbor ve Main dahil): ");
                        for (int i = 0; i < N; i++) {
                            System.out.print(best_neigbor_food[i]);
                        }
        }    
    }
}
