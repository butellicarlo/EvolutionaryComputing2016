#!/bin/bash
javac -cp contest.jar player9.java Individual.java Population.java
jar cmf MainClass.txt submission.jar player9.class Individual.class Population.class

if [ $1 -eq 1 ] ; then
	echo "Sphere Evaluation"
	java -jar testrun.jar -submission=player9 -evaluation=SphereEvaluation -seed=1
else if [ $1 -eq 2 ] ; then
	echo "Rastrigin Evaluation"
	java -jar testrun.jar -submission=player9 -evaluation=RastriginEvaluation -seed=1
else if [ $1 -eq 3 ] ; then
	echo "Fletcher Powell Evaluation"
	java -jar testrun.jar -submission=player9 -evaluation=FletcherPowellEvaluation -seed=1
fi
fi
fi