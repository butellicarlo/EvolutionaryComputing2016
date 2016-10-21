import java.util.Arrays;
import java.util.Random;

import org.vu.contest.ContestEvaluation;

public class Individual implements Comparable<Individual> {

	private static int GENOM_SIZE = 10;
	private static double MIN_VALUE = -5.0;
	private static double MAX_VALUE = 5.0;
	private final double[] genotype;

	private static double UNKNOWN = -1;
	private double fitness = UNKNOWN;

	public Individual(Random rand) {
		this.genotype = new double[GENOM_SIZE];
		for (int i = 0; i < GENOM_SIZE; i++) {
			this.genotype[i] = -5.0 + rand.nextDouble() * 10;
		}
	}

	public Individual(double[] genotype) {
		assert (genotype.length == GENOM_SIZE);
		this.genotype = genotype;
	}

	public double[] getGenotype() {
		return genotype;
	}

	public void evaluateFitness(ContestEvaluation evaluation) {
		// Evaluate not more than once!
		if (this.fitness == UNKNOWN) {
			this.fitness = (Double) evaluation.evaluate(genotype);
		}
	}

	public double getFitness(ContestEvaluation evaluation) {
		evaluateFitness(evaluation);
		return this.fitness;
	}

	private static double cutValue(double value) {
		if (value < MIN_VALUE) {
			return MIN_VALUE;
		} else {
			if (value > MAX_VALUE) {
				return MAX_VALUE;
			} else {
				return value;
			}
		}
	}

	@Override
	public int compareTo(Individual other) {
		if (this.fitness == UNKNOWN || other.fitness == UNKNOWN) {
			throw new RuntimeException("evaluateFitness(..) not called before compareTo(..)");
		}
		Double fitness = this.fitness;
		return fitness.compareTo(other.fitness);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(genotype);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Individual)) {
			return false;
		}
		Individual other = (Individual) obj;
		if (!Arrays.equals(genotype, other.genotype)) {
			return false;
		}
		return true;
	}

	public void print() {
		System.out.print("[");
		for (int i = 0; i < GENOM_SIZE; i++) {
			if (i != 0) {
				System.out.print(", ");
			}
			System.out.print(this.genotype[i]);
		}
		System.out.println("]\n");
	}
}