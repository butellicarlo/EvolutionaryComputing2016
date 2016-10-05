import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;
import java.util.*;
import java.lang.*;
import java.util.Random;
import java.util.Properties;

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
    
    public void run()
    {
        // Run your algorithm here       
        int evals = 0;
        // init starting population
        Population population = new Population(POPULATION_SIZE, true);
        
        /* Calculate fitensses of the first random population */
        for(int i = 0; i < POPULATION_SIZE; i++){
            fitnesses[i] = (double) evaluation_.evaluate(population.getIndividual(i));
        }

        int fittest = population.findFittest(fitnesses);
        double[] test = population.getIndividual(fittest);

         // calculate fitness
        while(evals<evaluations_limit_-POPULATION_SIZE){
            // Select parents
            // Apply crossover / mutation operators
            double child[] = {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
            // Check fitness of unknown fuction
            Double fitness = (double) evaluation_.evaluate(test);
            evals++;
            // Select survivors
        }
    }

    /* Randomly chose the index (numbers between start-end) of the individuals to be chosen */
    public int[] randomIndividualSelection(int startIndex, int endIndex, int n){
        Random r = new Random();
        int[] individualsIndex = new int[n];
        for (int i=0; i < n; i++){ 
            individualsIndex[i] = r.nextInt(endIndex) + startIndex; 
        }
        return individualsIndex;
    }
}
