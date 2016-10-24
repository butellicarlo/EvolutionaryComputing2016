import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;
import java.util.*;
import java.lang.*;
import java.util.Random;
import java.util.Properties;

public class player9 implements ContestSubmission
{
    int POPULATION_SIZE = 30;
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
        int generation=0;      
        int evals = 0;
        int restart = 0;

        Boolean changeParameters = true;

        Boolean reached10 = false;
        double checkpoint = 6.0;

        // init starting population
        Population population = new Population(POPULATION_SIZE, true);
        Population children;
        Double fitness;
        /* Calculate fitensses of the first random population */
        for(int i = 0; i < POPULATION_SIZE; i++){
            fitness = (double) evaluation_.evaluate(population.getGenotype(i));
            population.getIndividual(i).saveFitness(fitness);
        }
        population.findFittest();

        boolean checkFitness = true;

         // calculate fitness
        double bestFitnessStart = 0.5;
        while(evals<evaluations_limit_){
            
            // Select parents
            // Apply crossover / mutation operators
            // *** this is all done in getMutatedPopulation
            children = population.getMutatedPopulation();
            
            // Check fitness of each child
            for(int i=0;i<children.populationSize();i++){
                Individual child = children.getIndividual(i);
                Individual parent = population.getIndividual(i);
                
                fitness = (double) evaluation_.evaluate(child.getGenotype());
                child.saveFitness(fitness);
                
                // if(evals< 5){
                //     children.getIndividual(i).print(); 
                // }
                
                //select survivor (parent or child, the one with the highest fitness)
                if (child.getFitness() >= parent.fitness){
                    //take child into population
                    population.changeIndividual(i,child);
                }
                evals++;
                
            }
            population.findFittest();
            generation++;
 
           // print gen number and evaluation number to check how quick it converges to global optimum
            if(!reached10){
                if (evaluation_.getFinalResult() >= checkpoint){
                    System.out.print("gen #: ");
                    System.out.print(generation);
                    System.out.print(" || eval #: ");
                    System.out.print(evals);
                    System.out.print(" || best fitness so far: ");
                    System.out.print(evaluation_.getFinalResult());
                    System.out.print("\n");
                    if (evaluation_.getFinalResult() + 0.5 < 10.0){
                        checkpoint = evaluation_.getFinalResult() + 0.5;
                    }else{
                        checkpoint = 9.99;
                    }
                    if(evaluation_.getFinalResult() > 9.99){
                        reached10=true;
                    }
                }
            }

            if(generation == 200){
                double checkPerformance = population.bestFitness.getFitness()/bestFitnessStart ;
                if(checkPerformance<= 1.25){
                    // population = new Population(POPULATION_SIZE,true);
                    population.resetPopulation();
                    population.findFittest();
                    System.out.print("=============== restart ===========\n");
                    System.out.println(evaluation_.getFinalResult());
                    System.out.print("=============== restart ===========\n");
                    restart++;
                    generation = 0;

                    System.out.print("Population fitness increased by: ");
                    System.out.print(checkPerformance);
                    System.out.print(" in 30 generations\n");
                    System.out.print(population.bestFitness.getFitness());

                }else{
                    System.out.print("++++Population fitness increased by: ");
                    System.out.print(checkPerformance);
                    System.out.print(" in 30 generations++++\n");

                }
            }

            // if (changeParameters){
            //     if(generation >= 800){
            //         if(evaluation_.getFinalResult() > 6.0){
            //             population.changeF(0.1);
            //             population.changeCr(0.3);
            //             System.out.print("=============== change parameters ===========\n");
            //             System.out.println(evaluation_.getFinalResult());
            //             System.out.print("=============== change parameters ===========\n");
            //             changeParameters = false;
            //         }
            //     }
            // }


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

    public void printBestFitLarge(int evals){
        if (evaluation_.getFinalResult() >= 9.0){
            System.out.print("gen #: ");
            System.out.print(evals);
            System.out.print(" || best fitness so far: ");
            System.out.print(evaluation_.getFinalResult());
             System.out.print("\n");


        }
    }
}
