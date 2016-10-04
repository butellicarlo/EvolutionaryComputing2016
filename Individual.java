package ec2016;

import java.util.Random;
import java.util.*;

public class Individual {

    static int defaultSize = 10;
    static double fitness = 0;
    private double[] individual;
    Random r = new Random();

    public Individual(){
    	individual = new double[defaultSize];
    	for (int i = 0; i < defaultSize; i++) {
            individual[i] = -5.0 + r.nextDouble() * 10; 
        }
    }

    public double[] getIndividual(){
    	return individual;
    }

    /*get individual's feature*/
    public double getFeature(int index) {
        return individual[index];
    }

    /* save fitness for this individual*/
    public void saveFitness(int fit) {
        this.fitness = fit;
    }

    /* get fitness */
    public double getFitness(){
        return fitness;
    }

    public int size(){
    	return individual.length;
    }
}