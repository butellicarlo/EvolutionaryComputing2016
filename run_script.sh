#!/bin/bash
javac -cp contest.jar player9.java EvaluationWrapper.java Matrix.java Vector.java
jar cmf MainClass.txt submission.jar player9.class EvaluationWrapper.class Matrix.class Vector.class

case "$1" in
				0) echo "Compiled submission.jar"
						;;
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
            echo $"Usage: $0 {0 for compile-only|1 for Sphere|2 for Rastrigin|3 for Fletcher}"
            exit 1
esac