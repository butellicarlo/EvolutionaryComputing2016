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

	private int getPopulationSize() {
		// TODO: Implement Yasmina's idea:
		// make the size of the population bigger/smaller according to the
		// maximum number of evaluations
		return POPULATION_SIZE;
	}

	public void run() {

		// Random first generation
		Population population = new Population(this.getPopulationSize(), rand);

		while (evaluation.hasEvaluationsLeft()) {

			// Select parents
			// Apply crossover / mutation operators
			Population new_generation = population.createChildGeneration(rand);

			// Select survivors
			population = population.selectSurvivors(new_generation, evaluation, rand);
		}

	}
}
