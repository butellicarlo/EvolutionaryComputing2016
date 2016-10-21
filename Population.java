import java.util.Arrays;
import java.util.Random;

import org.vu.contest.ContestEvaluation;

public class Population {

	private Individual[] population;
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

	public void sort(ContestEvaluation evaluation) {
		// Need to evaluate fitness before sorting
		evaluateFitness(evaluation);
		Arrays.sort(this.population);
	}

	public void evaluateFitness(ContestEvaluation evaluation) {
		for (Individual individual : population) {
			individual.evaluateFitness(evaluation);
		}
	}

	public double getBestFitness(ContestEvaluation evaluation) {
		double max = Double.MIN_VALUE;
		for (Individual individual : population) {
			max = Double.max(max, individual.getFitness(evaluation));
		}
		return max;
	}

	public double getAverageFitness(ContestEvaluation evaluation) {
		double sum = 0.0;
		for (int i = 0; i < size; i++) {
			sum += population[i].getFitness(evaluation);
		}
		return sum / size;
	}

	public void print() {
		for (int i = 0; i < size; i++) {
			System.out.print("Individual " + i + ":");
			population[i].print();
		}
	}
}