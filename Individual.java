import java.util.Random;
import java.util.*;

public class Individual {

    int defaultSize = 10;
    double fitness = 0;
    double[] genotype;
    Random r = new Random();



    /* Constructur */
    public Individual(){
        // boolean random should decide if random allels will be created or if
        genotype = new double[defaultSize];
    }

    public double[] getGenotype(){
        return genotype;
    }

    public void setGenotypeRandom(){
        
        for (int i = 0; i < defaultSize; i++) {
            genotype[i] = -5.0 + r.nextDouble() * 10; 
            }
    }

    public Individual copyIndividual(){
        Individual newIndividual = new Individual();
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
    public void print(){
        System.out.print("[");
        for (int i = 0; i <size();i++){
            System.out.print(", ");
            System.out.print(getFeature(i));
        }
        System.out.println("]\n");
    }

}