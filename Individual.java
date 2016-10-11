import java.util.Random;

public class Individual {

	private static int genomSize = 10;
	private double fitness = 0;
	private double[] genotype;
	private Random r = new Random();

	/* Constructor */
	public Individual() {
		// boolean random should decide if random allels will be created or if
		genotype = new double[genomSize];
	}

	public double[] getGenotype() {
		return genotype;
	}

	public void setRandomGenotype() {
		for (int i = 0; i < genomSize; i++) {
			genotype[i] = -5.0 + r.nextDouble() * 10;
		}
	}

	public Individual copyIndividual() {
		Individual newIndividual = new Individual();
		for (int i = 0; i < genomSize; i++) {
			newIndividual.changeFeature(i, genotype[i]);
		}
		return newIndividual;
	}

	/* get individual's feature */
	public double getFeature(int index) {
		return genotype[index];
	}

	/* save fitness for this individual */
	public void saveFitness(double fit) {
		this.fitness = fit;
	}

	/* get fitness */
	public double getFitness() {
		return fitness;
	}

	public int size() {
		return genotype.length;
	}

	public void changeFeature(int index, double value) {
		genotype[index] = value;
		fitness = 0;
	}

	public void print() {
		System.out.print("[");
		for (int i = 0; i < size(); i++) {
			if (i != 0) {
				System.out.print(", ");
			}
			System.out.print(getFeature(i));
		}
		System.out.println("]\n");
	}

}