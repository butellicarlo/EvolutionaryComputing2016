import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;

import java.util.Random;
import java.util.Properties;

public class player9 implements ContestSubmission
{
    int POPULATION_SIZE = 100;
    int SURVIVERS = 25; // number of surviving individuals from old generation
    int FITSELECTION_SIZE = 40; // fit parent selection size
    int RANDOMSELECTION_SIZE = 10; // random parents selection, exclude fit

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

        System.out.println("Is multimodal? " + isMultimodal);
        System.out.println("I regualar? " + hasStructure);
        System.out.println("Is separable? " + isSeparable);
        System.out.println("Number of evaluations: " + evaluations_limit_);

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
        Population myPop = new Population(POPULATION_SIZE, true);

         // calculate fitness
        while(evals<evaluations_limit_){
            // Select parents
            // Apply crossover / mutation operators
            double child[] = {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
            // Check fitness of unknown fuction
            Double fitness = (double) evaluation_.evaluate(child);
            evals++;
            // Select survivors
        }

    }
}
