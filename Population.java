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

	public int getSize() {
		return size;
	}

	public double getFitness(ContestEvaluation evaluation) {
		double max = Double.MIN_VALUE;
		for (Individual individual : population) {
			max = Double.max(max, individual.getFitness(evaluation));
		}
		return max;
	}
}