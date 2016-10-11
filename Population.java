import java.util.*;

public class Population extends Individual {

	private Individual[] population;
	Random r = new Random(); // TODO: use method in player9.java
	private double f = 0.5; // parameter
	private double cr = 0.5; // cross-over rate/possibility.

	/* Constructor */
	public Population(int size, boolean random_init) {
		population = new Individual[size];
		if (random_init) {
			for (int i = 0; i < size; i++) {
				population[i] = new Individual();
				population[i].setRandomGenotype();
			}
		}
	}

	public int getPopulationSize() {
		return population.length;
	}

	public double[] getGenotype(int index) {
		return population[index].getGenotype();
	}

	public double[] getGenotypeCopy(int index) {
		double[] ind = new double[10];
		for (int i = 0; i < 10; i++) {
			ind[i] = population[index].getFeature(i);
		}
		return ind;
	}

	public Individual getIndividual(int index) {
		return population[index];
	}

	public int findFittest(double[] fitnesses) {
		double fittest = fitnesses[0];
		int index = 0;
		for (int i = 1; i < fitnesses.length; i++) {
			if (fittest <= fitnesses[i]) {
				fittest = fitnesses[i];
				index = i;
			}
		}
		return index;
	}

	public void printPopulation() {
		for (int i = 0; i < getPopulationSize(); i++) {
			System.out.println("Current population");
			population[i].print();
			System.out.println("\n");
		}
	}

	public void changeIndividual(int index, Individual x) {
		population[index] = x;
	}

	public Population getMutatedPopulation() {
		/*
		 * Three random individuals are randomly chosen x,y,z to create an
		 * mutated population p = F * (y-z) x' = x + p
		 * 
		 * This population will then recombine with the original population with
		 * cr (crossover rate for uniform crossover, with at least 1 allel of
		 * the parent)
		 * 
		 * From the parents and children list (deterministic elitist -> only the
		 * best is chosen)
		 */
		Individual x; // will be the child
		Individual y;
		Individual z;
		Individual parent;
		int forcedCrossoverIndex;

		Population children = new Population(getPopulationSize(), false);

		double c; // chance, to be compared with cr
		for (int i = 0; i < getPopulationSize(); i++) {
			// Pick x,y,z from the population and create
			x = getIndividual(r.nextInt(getPopulationSize())).copyIndividual();
			y = getIndividual(r.nextInt(getPopulationSize()));
			z = getIndividual(r.nextInt(getPopulationSize()));
			mutateX(x, y, z);

			parent = population[i];

			// Make child
			forcedCrossoverIndex = r.nextInt(10);
			// One allel/feature of the parent needs to be kept according to the theory
			x.changeFeature(forcedCrossoverIndex, parent.getFeature(forcedCrossoverIndex));
			for (int j = 0; j < x.size(); j++) {
				c = r.nextDouble();

				if (c > cr && j != forcedCrossoverIndex) {
					// Take feature from parent
					x.changeFeature(j, parent.getFeature(j));
				}
			}

			// Add child to children (population object)
			children.changeIndividual(i, x);
		}

		return children; // These need to be evaluated
	}

	private void mutateX(Individual x, Individual y, Individual z) {
		double p;
		double feature;
		for (int i = 0; i < y.size(); i++) {
			p = f * (y.getFeature(i) - z.getFeature(i));
			feature = x.getFeature(i) + p;
			if (feature > -5.0) {
				if (feature < 5.0) {
					x.changeFeature(i, feature);
				} else {
					x.changeFeature(i, 5.0);
				}
			} else {
				x.changeFeature(i, -5.0);
			}
		}
	}
}