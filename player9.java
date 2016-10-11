import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;
import java.util.Random;
import java.util.Properties;

public class player9 implements ContestSubmission {

	private static int POPULATION_SIZE = 100;
	private static int SURVIVERS = 25; // Number of surviving individuals from old generation
	private static int FITSELECTION_SIZE = 40; // Fit parent selection size
	private static int RANDOMSELECTION_SIZE = 10; // Random parents selection, exclude fit
	private double[] fitnesses = new double[POPULATION_SIZE]; // Calculated fitness values of each individual

	private Random rnd;
	private ContestEvaluation evaluation;
	private int evaluations_limit;
	// Evaluation properties
	private boolean isMultimodal;
	private boolean hasStructure;
	private boolean isSeparable;

	public player9() {
		rnd = new Random();
	}

	public void setSeed(long seed) {
		// Set seed of algorithm's random process
		rnd.setSeed(seed);
	}

	public void setEvaluation(ContestEvaluation evaluation) {
		// Set evaluation problem used in the run
		this.evaluation = evaluation;

		// Get evaluation properties
		Properties props = evaluation.getProperties();

		// Get evaluation limit
		this.evaluations_limit = Integer.parseInt(props.getProperty("Evaluations"));

		isMultimodal = Boolean.parseBoolean(props.getProperty("Multimodal"));
		hasStructure = Boolean.parseBoolean(props.getProperty("Regular"));
		isSeparable = Boolean.parseBoolean(props.getProperty("Separable"));
		// Do sth with property values, e.g. specify relevant settings of the
		// algorithm
	}

	public void run() {

		// Init first generation
		int evals = 0;
		Population population = new Population(POPULATION_SIZE, true);
		Population children;
		Double fitness;
		// Calculate fitness values of the first random population
		for (int i = 0; i < POPULATION_SIZE; i++) {
			fitness = (double) evaluation.evaluate(population.getGenotype(i));
			evals++;
			population.getIndividual(i).saveFitness(fitness);
		}

		boolean checkFitness = true;

		// Calculate fitness
		while (evals < evaluations_limit) {

			// Select parents
			// Apply crossover / mutation operators
			// *** this is all done in getMutatedPopulation
			children = population.getMutatedPopulation();

			// Check fitness of each child
			for (int i = 0; i < children.getPopulationSize(); i++) {
				
				Individual child = children.getIndividual(i);
				Individual parent = population.getIndividual(i);

				fitness = (double) evaluation.evaluate(children.getGenotype(i));
				evals++;
				child.saveFitness(fitness);

				// TODO: remove debug print
				// children.getIndividual(i).print();

				// Select survivor (parent or child, the one with the highest fitness)
				// TODO: Elitist survivor selection might lead to premature convergence?
				if (child.getFitness() >= parent.getFitness()) {
					// Take child into population
					population.changeIndividual(i, child);
				}
			}

		}
	}

	/*
	 * Randomly chose the index (numbers between start-end) of the individuals
	 * to be chosen
	 */
	// TODO: method is unused at the moment!
	public int[] randomIndividualSelection(int startIndex, int endIndex, int n) {
		Random r = new Random();
		int[] individualsIndex = new int[n];
		for (int i = 0; i < n; i++) {
			individualsIndex[i] = r.nextInt(endIndex) + startIndex;
		}
		return individualsIndex;
	}
}
