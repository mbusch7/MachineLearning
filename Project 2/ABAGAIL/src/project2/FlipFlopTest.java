package project2;

import dist.DiscreteDependencyTree;
import dist.DiscreteUniformDistribution;
import dist.Distribution;
import opt.*;
import opt.example.ContinuousPeaksEvaluationFunction;
import opt.example.CountOnesEvaluationFunction;
import opt.example.FlipFlopEvaluationFunction;
import opt.example.FourPeaksEvaluationFunction;
import opt.ga.*;
import opt.prob.GenericProbabilisticOptimizationProblem;
import opt.prob.MIMIC;
import opt.prob.ProbabilisticOptimizationProblem;
import shared.FixedIterationTrainer;

import java.util.Arrays;

/**
 * A test using the FlipFlop evaluation function
 * @author James Liu
 * @version 1.0
 */
public class FlipFlopTest {

  public static void main(String[] args) {
    if(args.length < 2)
    {
      System.out.println("Provide a input size and repeat counter");
      System.exit(0);
    }
    int N = Integer.parseInt(args[0]);
    if(N < 0)
    {
      System.out.println(" N cannot be negaitve.");
      System.exit(0);
    }
    int iterations = Integer.parseInt(args[1]);
    int[] ranges = new int[N];
    Arrays.fill(ranges, 2);
    EvaluationFunction ef = new FlipFlopEvaluationFunction();
    Distribution odd = new DiscreteUniformDistribution(ranges);
    NeighborFunction nf = new DiscreteChangeOneNeighbor(ranges);
    MutationFunction mf = new DiscreteChangeOneMutation(ranges);
    CrossoverFunction cf = new SingleCrossOver();
    Distribution df = new DiscreteDependencyTree(.1, ranges);
    HillClimbingProblem hcp = new GenericHillClimbingProblem(ef, odd, nf);
    GeneticAlgorithmProblem gap = new GenericGeneticAlgorithmProblem(ef, odd, mf, cf);
    ProbabilisticOptimizationProblem pop = new GenericProbabilisticOptimizationProblem(ef, odd, df);

//    System.out.println("Randomized Hill Climbing\n---------------------------------");
//    for(int i = 0; i < iterations; i++)
//    {
//      RandomizedHillClimbing rhc = new RandomizedHillClimbing(hcp);
//      long t = System.nanoTime();
//      FixedIterationTrainer fit = new FixedIterationTrainer(rhc, 200000);
//      fit.train("rhc");
//      System.out.println(ef.value(rhc.getOptimal()) + ", " + (((double)(System.nanoTime() - t))/ 1e9d));
//    }
//
//    System.out.println("Simulated Annealing \n---------------------------------");
//    for(int i = 0; i < iterations; i++)
//    {
//      SimulatedAnnealing sa = new SimulatedAnnealing(100, .95, hcp);
//      long t = System.nanoTime();
//      FixedIterationTrainer fit = new FixedIterationTrainer(sa, 200000);
//      fit.train("sa");
//      System.out.println(ef.value(sa.getOptimal()) + ", " + (((double)(System.nanoTime() - t))/ 1e9d));
//    }
//
//    System.out.println("Genetic Algorithm\n---------------------------------");
//    for(int i = 0; i < iterations; i++)
//    {
//      StandardGeneticAlgorithm ga = new StandardGeneticAlgorithm(200, 100, 20, gap);
//      long t = System.nanoTime();
//      FixedIterationTrainer fit = new FixedIterationTrainer(ga, 1000);
//      fit.train("ga");
//      System.out.println(ef.value(ga.getOptimal()) + ", " + (((double)(System.nanoTime() - t))/ 1e9d));
//    }

    System.out.println("MIMIC \n---------------------------------");

    for(int i = 0; i < iterations; i++)
    {
      MIMIC mimic = new MIMIC(200, 100, pop);
      long t = System.nanoTime();
      FixedIterationTrainer fit = new FixedIterationTrainer(mimic, 1000);
      fit.train("mimic");
      System.out.println(ef.value(mimic.getOptimal()) + ", " + (((double)(System.nanoTime() - t))/ 1e9d));
    }
  }
}