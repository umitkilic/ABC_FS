/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abc_fs;

/**
 *
 * @author Umit Kilic
 */
public class foodsource {
    int[] foodsource;
    double fitnessval;

    public foodsource(int[] food) {
        this.foodsource=food;
        this.fitnessval=0;
    }

    

    public int[] getFoodsource() {
        return foodsource;
    }

    public void setFoodsource(int[] foodsource) {
        this.foodsource = foodsource;
    }

    public double getFitnessval() {
        return fitnessval;
    }

    public void setFitnessval(double fitnessval) {
        this.fitnessval = fitnessval;
    }
}
