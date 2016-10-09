import java.util.Random;
import java.util.Arrays;
import java.util.*;

public class Population extends Individual
{
    
    Individual[] population;
    Random r = new Random();
    double f = 0.3; // parameter
    double cr = 0.3; // cross-over rate/possibility. 
    int tournament_size = 10; // tournament selection

    /* Constructur */
    public Population(int size, boolean flag)
    {
        population = new Individual[size];
        if (flag)
        {
            for (int i = 0; i < size; i++) 
            {
                population[i] = new Individual();
                population[i].setGenotypeRandom();
            }
        }
    }

    /* Return the population size */
    public int populationSize() 
    {
        return population.length;
    }

    /* Add individual to population at position index*/
    public void add(Individual i, int index){
        population[index] = i;
    }

    /* return the single individual */
    public double[] getGenotype(int index) 
    {
        return population[index].getGenotype();
    }

    public double[] getGenotypeCopy(int index) 
    {
        double[] ind = new double[10];
        for(int i = 0; i < 10; i++)
        {
            ind[i] = population[index].getFeature(i);
        }
        return ind;
    }


    public Individual getIndividual(int index)
    {
        return population[index];
    }

    public Individual findFittest()
    {
        double fittest = population[0].getFitness();
        int index = 0;
        for(int i = 1; i < populationSize(); i++)
        {
            if (fittest <= population[i].getFitness()) 
            {
                fittest = population[i].getFitness();
            }
        }
        return population[index];
    }

    public Individual[] twoBestFittest()
    {
        double fittest = population[0].getFitness();
        Individual[] twoBest = new Individual[2];
        int index = 0;
        int tmp = 0;
        for(int i = 1; i < populationSize(); i++)
        {
            if (fittest <= population[i].getFitness()) 
            {
                fittest = population[i].getFitness();
                tmp = index;
                index = i;
            }
        }
        twoBest[0] = population[index];
        twoBest[1] = population[tmp];
        return twoBest;
    }

    public double[] orderFitnesses()
    {
        double[] fitnesses = new double[populationSize()];
        for(int i = 0; i < populationSize(); i++)
        {
            fitnesses[i] = population[i].getFitness();
        }
        // sorting array
        Arrays.sort(fitnesses);
        return fitnesses;
    }

    public void printPopulation() 
    {
        for (int i = 0; i < populationSize(); i++) 
        {
            for(int j = 0; j < population[i].size(); j++)
            {
                System.out.print(population[i].getFeature(j) + "_");
            }
            System.out.println("\n");
        }
    }

    public void changeIndividual(int index, Individual x)
    {
        population[index] = x;
    }

    public Population getMutatedPopulation()
    {
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
        double c; //chance, to be compared with cr

        Population children = new Population(populationSize(), false);
        children.changeIndividual(0, findFittest());

        for(int i = 1; i < populationSize(); i++)
        {
            // pick x,y,z from the population and create
            x = this.tournamentSelection().copyIndividual();//getIndividual(r.nextInt(populationSize())).copyIndividual();
            y = this.tournamentSelection();//getIndividual(r.nextInt(populationSize()));
            z = crossover(x,y); //getIndividual(r.nextInt(populationSize()));
            mutateX(x,y,z);

            parent = population[i];

            //make child
            for(int j=0;j<x.size();j++)
            {
                c = r.nextDouble();
                
                if(c > cr)
                {
                    //take feature from parent
                    x.changeFeature(j,parent.getFeature(j));
                }
            }

            // add child to children (population object)
            children.changeIndividual(i,x);
        }
        return children; // These need to be evaluated
    }

    private void mutateX(Individual x, Individual y, Individual z)
    {
        double p;
        double feature;
        for(int i = 0; i < y.size() ; i++)
        {
            p = f * (y.getFeature(i) - z.getFeature(i));
            feature = x.getFeature(i) + p;
            if(feature > -5.0)
            {
                if (feature < 5.0)
                {
                    x.changeFeature(i,feature);
                } else 
                {
                    x.changeFeature(i,5.0);
                }
            } else 
            {
                x.changeFeature(i,-5.0);
            }  
        }
    }

    /* Tournament Selection return the winner of the tournament*/
    private Individual tournamentSelection() 
    {   
        // Create a tournament population
        Population tournament = new Population(tournament_size, false);

        // For each place in the tournament get a random individual
        for (int i = 0; i < tournament_size; i++) 
        {
            int index = (int) (Math.random() * population.length);
            tournament.add(getIndividual(index), i);
        }
        // Get the fittest
        Individual fittest = tournament.findFittest();
        return fittest;
    }

    // Crossover individuals
    private Individual crossover(Individual x, Individual y) 
    {
        Individual newIndividual = new Individual();
        // Loop through genes
        for (int i = 0; i < x.size(); i++) 
        {
            // Crossover
            if (Math.random() <= cr) 
            {
                newIndividual.changeFeature(i, x.getFeature(i));
            } else 
            {
                newIndividual.changeFeature(i, y.getFeature(i));
            }
        }
        return newIndividual;
    }

    // Mutate an individual
    private void mutatePopulation(Individual ind) 
    {
        // Loop through genes
        for (int i = 0; i < ind.size(); i++) 
        {
            if (Math.random() <= 0.15) 
            {
                // Create random gene
                double genotype = -5.0 + r.nextDouble() * 10; 
                ind.changeFeature(i, genotype);
            }
        }
    }

}




