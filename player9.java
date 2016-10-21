import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;

import java.util.Random;

public class player9 implements ContestSubmission {

	private static int POPULATION_SIZE = 100;

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
		double fitness = population.getFitness(evaluation);

		while (evaluation.hasEvaluationsLeft()) {

			// Select parents
			// Apply crossover / mutation operators

			// Select survivors
		}

	}
}
