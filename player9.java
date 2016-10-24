import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

public class player9 implements ContestSubmission {

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

		// Initialization
		int N = 10;
		Vector mean = new Vector(N);
		for (int i = 0; i < N; i++) {
			mean.setValue(i, rand.nextGaussian());
		}
		double sigma = 0.3;

		double lambda = 4.0 + Math.floor(3 * Math.log(N));
		double mu = lambda / 2;
		
		Vector weights = new Vector((int) mu);
		for (int i = 0; i < mu; i++)
			weights.setValue(i, Math.log(mu + 1/2) - Math.log(i+1));
		mu = Math.floor(mu);
		double sum_weights = weights.sum();
		for (int i = 0; i < weights.getDimension(); i++) {
			weights.setValue(i, weights.getValue(i) / sum_weights);
		}

		double mueff = (weights.sum() * weights.sum()) / weights.sum_squares(); // TODO: 1 / sum(weights^2) ?
		double cc = (4 + mueff / N) / (N + 4 + 2 * mueff / N);
		double cs = (mueff + 2) / (N + mueff + 5);
		double c1 = 2 / (((N + 1.3) * (N + 1.3)) + mueff);
		double cmu = Math.min(1 - c1, 2 * (mueff - 2 + 1 / mueff) / (((N + 2) * (N + 2)) + mueff));
		double damps = 1 + 2 * Math.max(0, Math.sqrt((mueff - 1) / (N + 1)) - 1) + cs;
		
		double chiN = Math.sqrt(N)*(1-1/(4*N)+1/(21*N*N));

		Vector ps = Vector.zero(N);
		Vector pc = Vector.zero(N);
		
		Matrix C = Matrix.Identity(N); // I (10-by-10)
		
		while (evaluation.hasEvaluationsLeft()) {

			// Generate and evaluate lambda offsprings
			Vector x[] = new Vector[(int) lambda];
			for (int i = 0; i < (int) lambda; i++) {
				// x[i] = N(m_k,sigma^2C)
				x[i] = Matrix.multivariateGaussianDistribution(C.multiply(sigma * sigma), mean, rand);
				x[i].setFitness((double) evaluation.evaluate(x[i].getDoubleArray()));
			}

			// Sort by fitness and compute weighted mean into xmean
			Arrays.sort(x); // x is now sorted by fitness
			Vector old_mean = new Vector(mean);
			// mean = old_mean + sum_1_mu w_i * (x_i_lambda - oldmean)
			Vector sum = Vector.zero(N);
			for (int i = 0; i < mu; i++) {
				sum.add(x[i].subtract(old_mean).multiply(weights.getValue(i)));
			}
			mean = old_mean.add(sum);

			// Cumulation: Update evolution paths
			// ps = (1-cs)*ps + sqrt(cs*(2-cs)*mueff) * invsqrtC * (mean - old_mean)/sigma;
			ps = ps.multiply(1-cs).add(C.inverseSqrt().multiply(Math.sqrt(cs*(2-cs)*mueff)).multiply(mean.subtract(old_mean).divide(sigma)));
			
			// TODO: hsig = norm(ps)/sqrt(1-(1-cs)^(2*counteval/lambda))/chiN < 1.4 + 2/(N+1);
			boolean hsig = true;
			// pc = (1-cc)*pc + hsig * sqrt(cc*(2-cc)*mueff)* (mean - old_mean) / sigma
			pc = pc.multiply(1-cc).add(hsig ? (mean.subtract(old_mean).divide(sigma)).multiply(Math.sqrt(cc*(2-cc)*mueff)) : Vector.zero(N));
			
			// Adapt covariance matrix C
			Matrix rank_min_mu_n = new Matrix(N); // TODO: calculate rank min mu n matrix
			for (int i = 0; i < mu; i++) {
				// weights.getValue(i).multiply
			}
			C = C.multiply(1-c1-cmu + cs).add(pc.rankOneMatrix().multiply(c1)).add(rank_min_mu_n.multiply(cmu));

			// Adapt step size sigma
			sigma = sigma * Math.exp((cs/damps)*(ps.norm()/chiN - 1)); 

			// Decomposition of C into B*diag(D.^2)*B' (diagonalization)
			// TODO: implement this
		}
	}
}
