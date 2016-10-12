import java.util.Arrays;
import java.util.Random;

import org.vu.contest.ContestEvaluation;

public class Individual {

	private static int GENOM_SIZE = 10;
	private static int UNKNOWN = -1;

	private static double F = 0.5; // parameter
	private static double CROSSOVER_RATE = 0.5; // cross-over rate/possibility.

	private final double[] genotype;
	private double fitness = UNKNOWN;

	public Individual(Random rand) {
		super();
		this.genotype = new double[GENOM_SIZE];
		for (int i = 0; i < GENOM_SIZE; i++) {
			this.genotype[i] = -5.0 + rand.nextDouble() * 10;
		}
	}

	public Individual(double[] genotype) {
		super();
		assert (genotype.length == GENOM_SIZE);
		this.genotype = genotype;
	}

	public Individual(Individual other) {
		super();
		this.genotype = new double[GENOM_SIZE];
		for (int i = 0; i < GENOM_SIZE; i++) {
			this.genotype[i] = other.genotype[i];
		}
	}

	public double[] getGenotype() {
		return genotype;
	}

	public double getFitness(ContestEvaluation evaluation) {
		if (this.fitness == UNKNOWN) {
			this.fitness = (double) evaluation.evaluate(genotype);
		}
		return this.fitness;
	}

	public Individual mutate(Individual other) {
		double[] new_genotype = new double[GENOM_SIZE];
		for (int i = 0; i < GENOM_SIZE; i++) {
			double p = F * (this.genotype[i] - other.genotype[i]);
			new_genotype[i] = cutValue(p);
		}
		return new Individual(new_genotype);
	}

	public Individual crossover(Individual other, Random rand) {
		double[] new_genotype = new double[GENOM_SIZE];
		for (int i = 0; i < GENOM_SIZE; i++) {
			new_genotype[i] = this.genotype[i];
		}
		int forcedCrossoverIndex = rand.nextInt(GENOM_SIZE);
		// One allel/feature needs to be kept according to the theory
		new_genotype[forcedCrossoverIndex] = other.genotype[forcedCrossoverIndex];

		for (int j = 0; j < GENOM_SIZE; j++) {
			double c = rand.nextDouble();
			if (c > CROSSOVER_RATE && j != forcedCrossoverIndex) {
				// Take feature from parent
				new_genotype[forcedCrossoverIndex] = other.genotype[forcedCrossoverIndex];
			}
		}
		return new Individual(new_genotype);
	}

	private static double cutValue(double value) {
		if (value > -5.0) {
			if (value > 5.0) {
				return 5.0;
			} else {
				return value;
			}
		} else {
			return -5.0;
		}
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Individual other = (Individual) obj;
		if (!Arrays.equals(genotype, other.genotype))
			return false;
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