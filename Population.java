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
			Individual rand_individual1 = this.population[rand.nextInt(this.size)];
			Individual rand_individual2 = this.population[rand.nextInt(this.size)];
			// TODO: re-think the way mutation works
			Individual mutated_individual = rand_individual1.mutate(rand_individual2);
			// TODO: select parents based on their fitness
			// TODO: make sure crossover of two times the same individual does
			// not occur
			Individual parent = this.population[i];
			new_generation[i] = parent.crossover(mutated_individual, rand);
		}
		return new Population(new_generation);
	}

	public Population selectSurvivors(Population other, ContestEvaluation evaluation, Random rand) {
		// TODO: keep diversity:
		// give fit individual higher probability to survive but not make an
		// pure elitist selection
		Individual[] survivors = new Individual[this.size];
		for (int i = 0; i < this.size; i++) {
			if (this.population[i].getFitness(evaluation) > other.population[i].getFitness(evaluation)) {
				survivors[i] = this.population[i];
			} else {
				survivors[i] = other.population[i];
			}
		}
		return new Population(survivors);
	}

}