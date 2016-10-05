import java.util.Random;
import java.util.*;

public class Individual {

    static int defaultSize = 10;
    static double fitness = 0;
    private double[] genotype;
    Random r = new Random();



    /* Constructur */
    public Individual(boolean random){
        // boolean random should decide if random allels will be created or if
        if(random){
            genotype = new double[defaultSize];
            for (int i = 0; i < defaultSize; i++) {
                genotype[i] = -5.0 + r.nextDouble() * 10; 
            }
        }
    }

    public double[] getGenotype(){
        return genotype;
    }

    public Individual copyIndividual(){
        Individual newIndividual = new Individual(false);
        for(int i=0;i<defaultSize;i++){
            newIndividual.changeFeature(i, genotype[i]);
        }
        return newIndividual;
    }

    /*get individual's feature*/
    public double getFeature(int index) {
        return genotype[index];
    }

    /* save fitness for this individual*/
    public void saveFitness(double fit) {
        this.fitness = fit;
    }

    /* get fitness */
    public double getFitness(){
        return fitness;
    }

    public int size(){
        return genotype.length;
    }

    public void changeFeature(int index, double value){
        genotype[index] = value;
        fitness = 0;
    }

}