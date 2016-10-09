javac -cp contest.jar player9.java Individual.java Population.java
jar cmf MainClass.txt submission.jar player9.class Individual.class Population.class
java -jar testrun.jar -submission=player9 -evaluation=SphereEvaluation -seed=1

java -jar testrun.jar -submission=player9 -evaluation=RastriginEvaluation -seed=1

java -jar testrun.jar -submission=player9 -evaluation=FletcherPowellEvaluation -seed=1
