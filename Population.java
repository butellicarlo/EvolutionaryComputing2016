import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.vu.contest.ContestEvaluation;

public class Population {

	private final Individual[] population;
	private int size;

	public Population(int size, Random rand) {
		super();
		this.population = new Individual[size];
		this.size = size;
		for (int i = 0; i < size; i++) {
			this.population[i] = new Individual(rand);
		}
	}

	public Population(Individual[] population) {
		super();
		this.population = population;
		this.size = population.length;
	}

	public Population createChildGeneration(Random rand) {
		
		Individual[] new_generation = new Individual[this.size];
		for (int i = 0; i < this.size; i++) {
			
			// Pick random individuals
			Individual[] individuals = this.randomIndividuals(5, rand);
			Individual x = individuals[0];

			Individual y_1 = individuals[1];
			Individual z_1 = individuals[2];
			Individual y_2 = individuals[3];
			Individual z_2 = individuals[4];
			
			// Mutate (2)
			Individual x_2 = x.mutate(y_1, z_1, y_2, z_2);
			
			// Crossover (bin)
			Individual parent = this.population[i];
			new_generation[i] = x_2.crossover(parent, rand);
		}
		return new Population(new_generation);
	}
	
	private Individual[] randomIndividuals(int n, Random rand) {
		Individual[] individuals = new Individual[n];
		List<Integer> ints = new ArrayList<Integer>();
		for (int i = 0; i < n; i++) {
			int r = rand.nextInt(this.size);
			while (ints.contains(r))
				r = rand.nextInt(this.size);
			ints.add(r);
			individuals[i] = this.population[r];
		}
		return individuals;
	}

	public Population selectSurvivors(Population child, ContestEvaluation evaluation, Random rand) {
		Individual[] survivors = new Individual[this.size];
		for (int i = 0; i < this.size; i++) {
			if (child.population[i].getFitness(evaluation) >= this.population[i].getFitness(evaluation)) {
				survivors[i] = child.population[i];
			} else {
				survivors[i] = this.population[i];
			}
		}
		return new Population(survivors);
	}

}