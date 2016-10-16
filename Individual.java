import java.util.Arrays;
import java.util.Random;

import org.vu.contest.ContestEvaluation;

public class Individual {
	
	private static double F = 0.5; // parameter
	private static double CR = 0.5; // cross-over rate/possibility

	private static int GENOM_SIZE = 10;
	private final double[] genotype;
	
	private static int UNKNOWN = -1;
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

	/*
	 * DE mutation 1
	 */
	public Individual mutate(Individual y, Individual z) {
		double[] new_genotype = new double[GENOM_SIZE];
		for (int i = 0; i < GENOM_SIZE; i++) {
			double p = F * (y.genotype[i] - z.genotype[i]);
			double feature = this.genotype[i] + p;
			new_genotype[i] = cutValue(feature);
		}
		return new Individual(new_genotype);
	}

	/*
	 * DE mutation 2
	 */
	public Individual mutate(Individual y_1, Individual z_1, Individual y_2, Individual z_2) {
		double[] new_genotype = new double[GENOM_SIZE];
		for (int i = 0; i < GENOM_SIZE; i++) {
			double p = F * (y_1.genotype[i] - z_1.genotype[i] + y_2.genotype[i] - z_2.genotype[i]);
			double feature = this.genotype[i] + p;
			new_genotype[i] = cutValue(feature);
		}
		return new Individual(new_genotype);
	}

	/*
	 * DE crossover bin
	 */
	public Individual crossover(Individual parent, Random rand) {
		double[] new_genotype = new double[GENOM_SIZE];
		int forcedCrossoverIndex = rand.nextInt(GENOM_SIZE);
		for (int i = 0; i < GENOM_SIZE; i++) {
			double c = rand.nextDouble();
			if (c > CR || i == forcedCrossoverIndex) {
				// Take feature from parent
				new_genotype[i] = parent.genotype[i];
			} else {
				new_genotype[i] = this.genotype[i];
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