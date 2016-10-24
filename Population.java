import java.util.Random;
import java.util.*;

public class Population extends Individual{
    
    Individual[] population;
    double f = 0.3; // parameter
    double cr = 0.3; // cross-over rate/possibility. 

    Individual bestFitness;

    /* Constructur */
    public Population(int size, boolean flag, Random r){
        Individual mean = new Individual();
        mean.setGenotypeRandom(r);

        population = new Individual[size];

        if (flag){
            for (int i = 0; i < size; i++) {
                population[i] = new Individual();
                population[i].setGenotypeGaussian(mean, r);
            }

        }

        // if (flag){
        //     for (int i = 1; i < size; i++) {
        //         population[i] = new Individual();
        //         population[i].setGenotypeRandom();
        //     }

        // }
    }


    public void changeCr(double newCr){
        cr = newCr;
    }

      public void changeF(double newF){
        f = newF;
    }

    public int populationSize() {
        return population.length;
    }

    public double[] getGenotype(int index) {
        return population[index].getGenotype();
    }

    public double[] getGenotypeCopy(int index) {
        double[] ind = new double[10];
        for(int i = 0; i < 10; i++){
            ind[i] = population[index].getFeature(i);
        }
        return ind;
    }

    public void resetPopulation(Random r){
        population[0].setGenotypeRandom(r);

            for (int i = 1; i < population.length; i++) {
                population[i] = new Individual();
                population[i].setGenotypeGaussian(getIndividual(0), r);
            }

        
    }


    public Individual getIndividual(int index){
    	assert(index < population.length);
    	Individual x = population[index];
    	assert(x!=null);
        return x;
    }

    public void findFittest(){
        Individual fittest = population[0];
        int index = 0;
        for(int i = 1; i < populationSize(); i++){
            if(fittest.getFitness() <= population[i].getFitness()){
                fittest = population[i];
                index = i;
            }
        }
        bestFitness = fittest;
    }

    public void printPopulation() {
        for (int i = 0; i < populationSize(); i++) {
            for(int j = 0; j < population[i].size(); j++){
                System.out.print(population[i].getFeature(j) + "_");
            }
            System.out.println("\n");
        }
    }

    public void changeIndividual(int index, Individual x){
        population[index] = x;
    }

    public Population getMutatedPopulation(Random r){
        /* 
            Three random individuals are randomly chosen x,y,z to create an mutated population
            p = F * (y-z)
            x' = x + p

            This population will then recombinate with the original population
            with Cr (crossover rate for uniform crossover, with at least 1 allel op the parent)

            From the parents and children list (deterministic elitist -> only the best is chosen)
        */
        Individual x; // will be the child
        Individual y;
        Individual z;
        Individual parent;
        int forcedCrossoverIndex;

        Population children = new Population(populationSize(), false, r);
        children.changeCr(cr);
        children.changeF(f);

        double c; //chance, to be compared with cr
        for(int i = 0; i < populationSize(); i++){
            // pick x,y,z from the population and create
            x = getIndividual(r.nextInt(populationSize())).copyIndividual();
            y = getIndividual(r.nextInt(populationSize()));
            z = getIndividual(r.nextInt(populationSize()));
            mutateX(x,y,z);

            parent = population[i];

            //make child
            forcedCrossoverIndex = r.nextInt(10);
            x.changeFeature(forcedCrossoverIndex,parent.getFeature(forcedCrossoverIndex)); //One allel/feature of the parent needs to be kept accoring to the theory
            for(int j=0;j<x.size();j++){
                c = r.nextDouble();
                
                if(c>cr && j!= forcedCrossoverIndex){
                    //take feature from parent
                    x.changeFeature(j,parent.getFeature(j));
                }
            }

            // add child to children (population object)
            children.changeIndividual(i,x);
        }

        return children; // These need to be evaluated
    }

    private void mutateX(Individual x, Individual y, Individual z){
        double p;
        double feature;
        for(int i = 0; i < y.size() ; i++){
            p = f * (y.getFeature(i) - z.getFeature(i));
            feature = x.getFeature(i) + p;
            if(feature > -5.0){
                if (feature < 5.0){
                    x.changeFeature(i,feature);
                } else {
                    x.changeFeature(i,5.0);
                }
            }else{
                x.changeFeature(i,-5.0);
            }  
        }
    }


}