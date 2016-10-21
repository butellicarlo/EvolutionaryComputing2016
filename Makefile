JCC = javac

default: contest.jar player9.java Individual.java Population.java EvaluationWrapper.java Matrix.java Vector.java
	javac -cp contest.jar player9.java Individual.java Population.java EvaluationWrapper.java Matrix.java Vector.java
	jar cmf MainClass.txt submission.jar player9.class Individual.class Population.class EvaluationWrapper.class Matrix.class Vector.class