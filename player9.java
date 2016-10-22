import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;

import java.lang.reflect.Array;
import java.util.Arrays;
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
		int N = 10;
		Matrix C = Matrix.Identity(N); // I (10-by-10)
		double sigma = 0.3;     
		Vector m = new Vector(N);
		for (int i = 0; i < N; i++) {
			m.setValue(i, rand.nextGaussian());
		}

		while (evaluation.hasEvaluationsLeft()) {
			
			// Generate and evaluate lambda offsprings
			Individual x[] = new Individual[lambda];
			for (int i = 0; i < lambda; i++) {
				// x[i] = N(m_k,sigma^2C)
				x[i] = new Individual(Matrix.multivariateGaussianDistribution(C.multiply(sigma * sigma), m, rand));
				x[i].evaluateFitness(evaluation);
			}

			// Sort by fitness and compute weighted mean into xmean
			Arrays.sort(x); // x is now sorted by fitness
			// m = ...

			// Cumulation: Update evolution paths

			// Adapt covariance matrix C

			// Adapt step size sigma

			// Decomposition of C into B*diag(D.^2)*B' (diagonalization)

		}
	}
}
