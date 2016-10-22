import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;

import java.util.Random;

public class player9 implements ContestSubmission {

	private static int POPULATION_SIZE = 10;

	private Random rand;
	private EvaluationWrapper evaluation;

	public player9() {
		rand = new Random();
	}

	public void setSeed(long seed) {
		// Set seed of algorithm's random process
		rand.setSeed(seed);
	}

	public void setEvaluation(ContestEvaluation evaluation) {
		// Set evaluation problem used in the run
		this.evaluation = new EvaluationWrapper(evaluation);
	}

	public void run() {

		// Initialize population
		Population population = new Population(POPULATION_SIZE, rand);

		// Calculate fitness
		double best = population.getBestFitness(evaluation);
		System.out.println("Best fitness: " + best);
		double average = population.getAverageFitness(evaluation);
		System.out.println("Average fitness: " + average);

		// Sort population by fitness
		population.sort(evaluation);
		System.out.println("Sorted population:\n");
		population.print();

		while (evaluation.hasEvaluationsLeft()) {

			// Select parents
			// Apply crossover / mutation operators

			// Select survivors

		}
	}

	public void cma_es() {

		// Initialization
		int lambda = 5;
		Matrix C = Matrix.Identity(10); // I (10-by-10)

		while (evaluation.hasEvaluationsLeft()) {

			// Generate and evaluate lambda offspring
			for (int i = 0; i < lambda; i++) {

			}

			// Sort by fitness and compute weighted mean into xmean

			// Cumulation: Update evolution paths

			// Adapt covariance matrix C

			// Adapt step size sigma

			// Decomposition of C into B*diag(D.^2)*B' (diagonalization)

			// Break, if fitness is good enough or condition exceeds 1e14,
			// better termination methods are advisable

		}
	}
}
