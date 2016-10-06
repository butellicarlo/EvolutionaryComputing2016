#### **My first thoughts on the algorithm**
thursday 29 - sept

1. make the size of the population bigger/smaller according to the max evaluations
2. Always keep diversity: ratio fit and random parents
3. test different recombination/crossover methods: one-point - two-point or decide per allel is it is switched or not
4. Survival selection can have a part of the new generation as well as some of the old
5 **We should only evaluate the new generation each time (so we do not unneccarily check those that are already evaluated)
6. Mutation could be just chosing a random allel and mutate it giving it a random new double or control the mutation by adding or subtracting (using some function)

#### The online functions
I was talking to some of our classmates on the assignment and one of them actually figured out that all the online functions were modal, and unstructed and unseperable.
Meaning the following:

1. There is no structure, therefor we cannot know if we are close to the global optimum (optima).
2. Each allel's value is dependent on one another because it is insperable.
3. As mentioned in 1, we can't know if we are close to the global optimum because it is modal and unstuctured. We might be able to approach this global optimum by keeping diversity high.


#### Results
##### Differential Algoritm 
(DE/rand/1/bin --> Random base parent, 1 difference vector, uniform crossover)

- Cr = 0.3
- F = 0.3
- Population size = 100

- Function 1: 9.828662882374749
- Function 2: 0.19671557645716536
- Function 3: 9.944575080552198

----------------

* Cr = 0.5
* F = 0.5
* Population size = 100

* Function 1: 9.851157466790884 +
* Function 2: 0.13582734701789337 -
* Function 3: 7.455024876704664 --



