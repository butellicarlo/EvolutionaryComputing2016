import java.util.Random;
import java.util.*;

public class Population extends Individual{
    Individual[] population;

    public Population(int size, boolean flag){
        population = new Individual[size];
        for (int i = 0; i < size; i++) {
            population[i] = new Individual();
        }
    }

    public int populationSize() {
        return population.length;
    }

    public double[] getIndividual(int index) {
        double[] ind = new double[10];
        for(int i = 0; i < 10; i++){
            ind[i] = population[index].getFeature(i);
        }
        return ind;
    }

    public int findFittest(double[] fitnesses){
        double fittest = fitnesses[0];
        int index = 0;
        for(int i = 1; i < fitnesses.length; i++){
            if(fittest <= fitnesses[i]){
                fittest = fitnesses[i];
                index = i;
            }
        }
        return index;
    }

    public void printPopulation() {
        for (int i = 0; i < populationSize(); i++) {
            for(int j = 0; j < population[i].size(); j++){
                System.out.print(population[i].getFeature(j) + "_");
            }
            System.out.println("\n");
        }
    }

}