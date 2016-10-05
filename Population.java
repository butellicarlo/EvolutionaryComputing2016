import java.util.Random;
import java.util.*;

public class Population extends Individual{
    
    Individual[] population;
    Random r = new Random();
    static double f = 0.3; // parameter
    static double cr = 0.3; // cross-over rate/possibility. 

    /* Constructur */
    public Population(int size, boolean flag){
        population = new Individual[size];
        if (flag){
            for (int i = 0; i < size; i++) {
                population[i] = new Individual(true);
            }
        }
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


    public Individual getIndividual(int index){
        return population[index];
    }

    public int findFittest(double[] fitnesses){
        double fittest = fitnesses[0];
        int index = 0;
        for(int i = 1; i < fitnesses.length; i++){
            if(fittest <= fitnesses[i]){
                fittest = fitnesses[i];
                index = i;
            }
        }
        return index;
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

    public Population getMutatedPopulation(){
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

        Population children = new Population(populationSize(), false);

        double c; //chance, to be compared with cr
        for(int i = 0; i < populationSize(); i++){
            // pick x,y,z from the population and create
            x = getIndividual(r.nextInt(populationSize)).copyIndividual();
            y = population.getIndividual(r.nextInt(populationSize));
            z = population.getIndividual(r.nextInt(populationSize));
            mutateX(x,y,z);

            parent = population[i];

            //make child
            for(int j=0;j<x.size();j++){
                c = r.nextDouble();
                
                if(c>cr){
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