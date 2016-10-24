import java.util.Random;
import java.util.*;

public class Individual {

    int defaultSize = 10;
    double fitness = 0;
    double[] genotype = new double[defaultSize];
    double sigma = 1.0;


    /* Constructur */
    public Individual(){
        // boolean random should decide if random allels will be created or if
        genotype = new double[defaultSize];
    }

    public double[] getGenotype(){
        return genotype;
    }

    public void setGenotypeRandom(Random r){
        
        for (int i = 0; i < defaultSize; i++) {
            genotype[i] = -5.0 + r.nextDouble() * 10; 
            }
    }

    public void setGenotypeGaussian(Individual mean, Random r){
        for (int i = 0 ; i < defaultSize ; i++){
            double rgaus = mean.getFeature(i) + r.nextGaussian()* sigma;
            while (rgaus>= 5.0 || rgaus<=-5.0){
                rgaus = r.nextGaussian()* sigma +mean.getFeature(i);
            }
            genotype[i]= rgaus;
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