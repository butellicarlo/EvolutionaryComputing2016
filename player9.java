package Collections;

import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;
import java.util.*;
import java.lang.*;
import java.util.Random;
import java.util.Properties;

import java.util.Arrays; // do we need to import this?
import java.util.Collections;
import java.util.*;

public class player9 implements ContestSubmission
{
    int POPULATION_SIZE = 100;
    int SURVIVERS = 25; // number of surviving individuals from old generation
    int FITSELECTION_SIZE = 40; // fit parent selection size
    int RANDOMSELECTION_SIZE = 10; // random parents selection, exclude fit
    double[] fitnesses = new double[POPULATION_SIZE]; // calculated fitnesses of each individual

    Random rnd_;
    ContestEvaluation evaluation_;
    private int evaluations_limit_;

    Random r = new Random();

    int POPULATION_SIZE = 100;
    int SURVIVERS = 25; // number of surviving individuals from old generation
    int FITSELECTION_SIZE = 40; // fit parent selection size
    int RANDOMSELECTION_SIZE = 10; // random parents selection, exclude fit
    
    double[][] population = generatePop(POPULATION_SIZE);

    double[] fitnesses = new double[POPULATION_SIZE]; // calculated fitnesses of each individual
    double[] fitnessesSorted; // sorted list of fitnesses

    double[][] parents = new double[FITSELECTION_SIZE + RANDOMSELECTION_SIZE][10];
    double[][] children = new double[2*(FITSELECTION_SIZE + RANDOMSELECTION_SIZE)][10]; // create two children per parent pair
	
    int CROSSOVER_PROBABILITY = 33; // in percentage, 0-100.


    List shuffled = makeShuffledList(RANDOMSELECTION_SIZE);

	public player9()
	{
		rnd_ = new Random();
	}
	
	public void setSeed(long seed)
	{
		// Set seed of algortihms random process
		rnd_.setSeed(seed);
	}
    
    public player9()
    {
        rnd_ = new Random();
    }
    
    public void setSeed(long seed)
    {
        // Set seed of algortihms random process
        rnd_.setSeed(seed);
    }

    public void setEvaluation(ContestEvaluation evaluation)
    {
        // Set evaluation problem used in the run
        evaluation_ = evaluation;
        
        // Get evaluation properties
        Properties props = evaluation.getProperties();

        // Get evaluation limit
        evaluations_limit_ = Integer.parseInt(props.getProperty("Evaluations"));

        // Property keys depend on specific evaluation
        // E.g. double param = Double.parseDouble(props.getProperty("property_name"));
        boolean isMultimodal = Boolean.parseBoolean(props.getProperty("Multimodal"));
        boolean hasStructure = Boolean.parseBoolean(props.getProperty("Regular"));
        boolean isSeparable = Boolean.parseBoolean(props.getProperty("Separable"));

        System.out.println("Is multimodal? " + isMultimodal);
        System.out.println("Is regualar? " + hasStructure);
        System.out.println("Is separable? " + isSeparable);
        System.out.println("Number of evaluations: " + evaluations_limit_);

		/* Do sth with property values, e.g. specify relevant settings of your algorithm */
		// If Modal
        /* Do sth with property values, e.g. specify relevant settings of your algorithm */
        // If Modal

        if(isMultimodal){
            // Do sth
        }else{
            // Do sth else
        }
        
        // If has strong structure
        if(hasStructure){
            // Do sth
        }else{
            // Do sth else
        }

        // If is separable
        if(isSeparable){
            // Do sth
        }else{
            // Do sth else
        }
    }
    
	public void run() {
        // Run your algorithm here

        int evals = 0;
        // init population
        
        
        while (evals < evaluations_limit_) {

            int counter = 0; // used to keep track of individuals position
            for (double[] individual : population) {
                // for each individual in the population, calculate the fitness
                fitnesses[counter] = (double) evaluation_.evaluate(individual); // calculate fitness
                fitnessesSorted = Arrays.copyOf(fitnesses, fitnesses.length);
                Arrays.sort(fitnessesSorted); //fitness sorted from high to low.

                counter++;

            }

            // Apply crossover / mutation operators
            double child[] = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
            // Check fitness of unknown fuction
            Double fitness = (double) evaluation_.evaluate(child);
            evals++;
            // Select survivors


    public double[][] generatePop(int size) {
        // makes a population of size = size, randomly assigned allels

        double[][] pop = new double[size][10];
        {
            // Random r = new Random();
            for (int i =0; i < size+1; i++){
                // make population of size = size
                for (int j=0; j<10; j++){
                    // create every allel of the individual randomly
                    pop[i][j] = -5.0 + r.nextDouble() * 10;

                }

            }
            return pop;

        }
    }


    public int[] randomIndividualSelection(int startIndex, int endIndex, int n){
        // Randomly draw numbers to be the index of the chosen individuals.
        // numbers between start-end, for a total of n individuals.
        // Random r = new Random();
        int[] individualsIndex = new int[n];
        for (int i=0;i<n;i++){
            individualsIndex[i] = r.nextInt(endIndex) + startIndex;

        }
        

    }

    public void parentselection(){
        int startIndex = POPULATION_SIZE - FITSELECTION_SIZE;
        // Random r = new Random();
        int randIndex;
        int counter = 0;
        for (int i=startIndex;i>POPULATION_SIZE;i++){
            // fittest are at the end of the fittnessSorted array
            // here make an array of all fit individuals selected 
            parents[counter] = getIndividual(i); // add fit parent
            counter++;

        }
        for (int i=0;i>RANDOMSELECTION_SIZE;i++){
            // unfit or other are at the beginning of the fittnessSorted array
            // here make an array of all individuals selected radomly
            parents[counter] = getIndividual(shuffled[i]); // add random parent without replacement, exclude the fittest
            counter++;
        }
        Collections.shuffle(shuffled); // shuffle again

    }

    public void makeChildren(){
        // 1. pick parentpairs (randomly or through some algorithm)
        // 2. recombine / crossover of parents (onepoint or whatever)
        // 3. mutate (with some probability)
        double[] mom = new double[10];
        double[] dad = new double[10];
      
        List pairs = makeShuffledList(parents.length); // random pairs from selected parents
        for (int i=0;i>children.length;i+=2){
            mom = parents[i];
            dad = parents[i+1];
            recombine(mom,dad,i);
             
        }
    }

    public List makeShuffledList(int size){
        List arrlist = new ArrayList();
        for(int i = 0; i< size;i++){
            arrlist.add(i);
        }
        Collections.shuffle(arrlist);
        return arrlist;
    }

    public double[] recombine(double[] mom, double[] dad, int positionFirstChild){
        // some recombination strategy here
        double storedAllel;


        int decision;
        double[] child1 = Array.copyOf(mom,10);
        double[] child2 = Array.copyOf(dad,10);
        for (int i=0;i<10;i++){
            // go through each allel and throw dice
            decision = r.nextInt(100);
            if (decision < CROSSOVER_PROBABILITY){ // dice is 100 sided, so 1/3 chance --> should be an constant somewhen on top
                // crossover that allel
                storedAllel = child1[i];
                child1[i] = child2[i];
                child2[i] = storedAllel;
            }

        }
        children[positionFirstChild] = child1;
        children[positionFirstChild+1] = child2;

        //////////////////// maybe add mutate somewhere in recombination also.

    }

    public double[] mutate(double[] individual, double probability){
        // some mutation strategy here
    }


    public double[] getIndividual(int index){
        return population[getIndex(fitnesses,fitnessSorted[index])];
    }

    public int getIndex(double[] fitnesses, double fitness) {
        // get individuals index corresponding with that fitness
        for (int i=0; i<fitnesses.length; i++){
            if (fitnesses[i] == fitness) return i;
        }

}





