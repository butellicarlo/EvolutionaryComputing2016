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

	public Individual getIndividual(int index) {
        return population[index];
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