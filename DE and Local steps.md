### Steps for the DE+Local search hybrid algorithm

##### Metrics for keeping track of the populations fitness

In population.java:
* Function that calculates __average fitness__ of the population object and stores it in the object as a variable
* Function that calculates __sum fitness__ and stores it in the object as a variable
* Function that stores that best individuals in an area (object variable)
  * The number of individuals taken should be a parameter, so we can decrease or increase if we want to in a later stadium (default would be 20 maybe)
* Function that copies the fittest (range of 10-20 individuals in a population of 100) into a new Population object
  * After Object is created also the average/sum etc need to be calculated. Fitnesses already were calculated in the individual
  * Returns Population instance of size 10-20 with the fittest individuals
* Function for local search on subpopulation (can perhaps be the current DE mutation function, since this is differential as well, but with a smaller F value?)
  * Area size as parameter: a max deviation
  * Each individual will get between 3-10 new neighbors in their area
  * Each allel of the neighbor will be a deviation on the base allel: ORIGINAL VALUE plus or minus RAND(0,max_deviation), with the end value staying withing the rande of -5 and 5 of course
  * Returns a Population object with the neighbor individuals

__!!__ All new "local search" babies will have to have their fitness evaluated __!!__
Best 10-20 local search babies (neighbors) will be selected to join the original DE population, and go through DE mutation and evolution

Next functions can wait:
* Function (algorithm) that checks/evaluates the population's fitness and increases/decreases the parameters of the mutation/recombination/area size of the local search
  * Not sure yet what type of reasoning we can apply

  
  
