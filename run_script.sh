#!/bin/bash
javac -cp contest.jar player9.java Individual.java Population.java
jar cmf MainClass.txt submission.jar player9.class Individual.class Population.class

case "$1" in
        1)
            echo "Sphere Evaluation"
			java -jar testrun.jar -submission=player9 -evaluation=SphereEvaluation -seed=1
            ;;
         
        2)
            echo "Rastrigin Evaluation"
			java -jar testrun.jar -submission=player9 -evaluation=RastriginEvaluation -seed=1
         	;;
        3)
            echo "Fletcher Powell Evaluation"
			java -jar testrun.jar -submission=player9 -evaluation=FletcherPowellEvaluation -seed=1
        	;;         
        *)
            echo $"Usage: $0 {1 for Sphere|2 for Rastrigin|3 for Fletcher}"
            exit 1
esac