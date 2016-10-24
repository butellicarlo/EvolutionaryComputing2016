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
			cma_es();

			// Select parents
			// Apply crossover / mutation operators

			// Select survivors

		}
	}

	public void cma_es() {

		// Initialization
		int N = 10;
		Vector mean = new Vector(N);
		for (int i = 0; i < N; i++) {
			mean.setValue(i, rand.nextGaussian());
		}
		double sigma = 0.3;

		double lambda = 4.0 + Math.floor(3*Math.log(N));		
		double mu = lambda/2; 
		Vector weights = new Vector((int)mu); // Vector or double ??
		for(int i = 1; i <= mu; i++)
			weights.setValue(i-1, Math.log(mu+1/2)-Math.log(i));
		mu = Math.floor(mu);

		double sum_weights = weights.sum();
		for(int i = 0; i < weights.getDimension(); i++){
			weights.setValue(i, weights.getValue(i)/sum_weights);
		}
		double mueff = (weights.sum()*weights.sum()) / weights.sum_squares();
		double cc = (4+mueff/N) / (N+4 + 2*mueff/N);
		double cs = (mueff+2) / (N+mueff+5);
		double c1 = 2 / (((N+1.3)*(N+1.3))+mueff);
		double cmu = Math.min(1-c1, 2 * (mueff-2+1/mueff) / (((N+2)*(N+2))+mueff));
		double damps = 1 + 2 * Math.max(0, Math.sqrt((mueff-1)/(N+1))-1) + cs;

		Matrix B = Matrix.Identity(N); // I (10-by-10)
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				C.setValue(i, j, -5.0 + rand.nextDouble() * 10);
			}
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
			Vector old_mean = new Vector(mean.getDimension());
			for(int i = 0; i < mean.getDimension(); i++){
				old_mean.setValue(i, mean.getValue(i));
				mean.setValue(i, x.getValue(i)*weights.getValue(i)); 
			}


			// Cumulation: Update evolution paths

			// Adapt covariance matrix C

			// Adapt step size sigma

			// Decomposition of C into B*diag(D.^2)*B' (diagonalization)

		}
	}
}
