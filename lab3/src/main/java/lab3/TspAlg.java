package lab3;

import org.uncommons.watchmaker.framework.*;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.termination.GenerationCount;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TspAlg {

    public static ArrayList<City> parseInputFile(String problem) {
        ArrayList<City> cities_list = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(problem));
            cities_list = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if ("NODE_COORD_SECTION".equals(line)) {
                    while (scanner.hasNextLine()) {
                        String line1 = scanner.nextLine();
                        if (line1.equals("EOF")) {
                            break;
                        }
                        String[] inputs = line1.split(" ");
                        int[] ints = Arrays.stream(inputs).mapToInt(Integer::parseInt).toArray();
                        cities_list.add(new City(ints[1], ints[2]));
                    }
                }
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("Input file is not found");
        }
        return cities_list;
    }
    public static void main(String[] args) {
        String problem = "path"; // name of problem or path to input file

        int populationSize = 140; // size of population
        int generations = 1000000; // number of generations

        Random random = new Random(); // random

        ArrayList<City> cities = parseInputFile(problem);

        int dimension = cities.size();

        CandidateFactory<TspSolution> factory = new TspFactory(dimension); // generation of solutions

        ArrayList<EvolutionaryOperator<TspSolution>> operators = new ArrayList<EvolutionaryOperator<TspSolution>>();
        operators.add(new TspCrossover()); // Crossover
        operators.add(new TspMutation(0.6, 0.45,0.15,0.25)); // Mutation
        EvolutionPipeline<TspSolution> pipeline = new EvolutionPipeline<TspSolution>(operators);

        SelectionStrategy<Object> selection = new RouletteWheelSelection(); // Selection operator

        FitnessEvaluator<TspSolution> evaluator = new TspFitnessFunction(cities); // Fitness function

        EvolutionEngine<TspSolution> algorithm = new SteadyStateEvolutionEngine<TspSolution>(
                factory, pipeline, evaluator, selection, populationSize, false, random);

        algorithm.addEvolutionObserver(new EvolutionObserver() {
            int gen_of_best_fit = 0;
            double prev_best_fit = 999999999;
            public void populationUpdate(PopulationData populationData) {
                double bestFit = populationData.getBestCandidateFitness();
                System.out.println("Generation " + populationData.getGenerationNumber() + ": " + bestFit);
                TspSolution best = (TspSolution)populationData.getBestCandidate();
                System.out.println("\tBest solution = " + best.toString());

                if (prev_best_fit > bestFit) {
                    gen_of_best_fit = populationData.getGenerationNumber();
                    prev_best_fit = bestFit;
                }
                System.out.println("\tGeneration of best solution = " + gen_of_best_fit);
            }
        });

        TerminationCondition terminate = new GenerationCount(generations);
        algorithm.evolve(populationSize, 1, terminate);
    }
}
