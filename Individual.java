import java.util.Random;

import org.vu.contest.ContestEvaluation;

public class Individual extends Vector implements Comparable<Individual> {

	private static int GENOM_SIZE = 10;
	private static double MIN_VALUE = -5.0;
	private static double MAX_VALUE = 5.0;

	private static double UNKNOWN = Double.NaN;
	private double fitness = UNKNOWN;

	public Individual(Random rand) {
		super(GENOM_SIZE);
		for (int i = 0; i < GENOM_SIZE; i++) {
			this.data[i] = -5.0 + rand.nextDouble() * 10;
		}
	}

	public Individual(double[] genotype) {
		super(genotype);
		assert (genotype.length == GENOM_SIZE);
	}

	public Individual(Vector vector) {
		this(vector.data);
	}

	public void evaluateFitness(ContestEvaluation evaluation) {
		// Evaluate not more than once!
		if (this.fitness == UNKNOWN) {
			this.fitness = (Double) evaluation.evaluate(this.data);
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

	public void print() {
		System.out.print("[");
		for (int i = 0; i < GENOM_SIZE; i++) {
			if (i != 0) {
				System.out.print(", ");
			}
			System.out.print(this.data[i]);
		}
		System.out.println("]\n");
	}
}