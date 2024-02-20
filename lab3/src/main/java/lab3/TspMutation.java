package lab3;

import org.uncommons.watchmaker.framework.EvolutionaryOperator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TspMutation implements EvolutionaryOperator<TspSolution> {

    double insertProb;
    double swapProb;
    double inversionProb;
    double scrambleProb;

    public TspMutation(double insertProb, double swapProb, double inversionProb, double scrambleProb) {
        this.insertProb = insertProb;
        this.swapProb = swapProb;
        this.inversionProb = inversionProb;
        this.scrambleProb = scrambleProb;
    }


    public TspSolution doInsertMutation(Random random, int route_length, ArrayList<Integer> cities) {
        int a = random.nextInt(route_length);
        int b = random.nextInt(route_length);

        int i = Math.min(a, b);
        int j = Math.max(a, b);
        cities.add(j, cities.get(i));
        cities.remove(i);
        return new TspSolution(cities);
    }

    public TspSolution doSwapMutation(Random random, int route_length, ArrayList<Integer> cities) {
        int i = random.nextInt(route_length);
        int j = random.nextInt(route_length);
        int i_val = cities.get(i);
        cities.set(i, cities.get(j));
        cities.set(j, i_val);
        return new TspSolution(cities);
    }

    public TspSolution doInversionMutation(Random random, int route_length, ArrayList<Integer> cities) {
        int a = random.nextInt(route_length);
        int b = random.nextInt(route_length);

        int start = Math.min(a, b);
        int end = Math.max(a, b);

        int sub_array_length = end - start + 1;
        int[] inverted_sub_array = new int[sub_array_length];

        for (int i = 0; i < sub_array_length; i++) {
            inverted_sub_array[sub_array_length - 1 - i] = cities.get(start + i);
        }

        for (int i = start; i < end + 1; i++) {
            cities.set(i, inverted_sub_array[i - start]);
        }
        return new TspSolution(cities);
    }

    public TspSolution doScrambleMutation(Random random, int route_length, ArrayList<Integer> cities) {
        int a = random.nextInt(route_length);
        int b = random.nextInt(route_length);

        int start = Math.min(a, b);
        int end = Math.max(a, b);

        ArrayList<Integer> sub_array = new ArrayList<>();
        for (int i = start; i < end + 1; i++) {
            sub_array.add(cities.get(i));
        }

        Collections.shuffle(sub_array);

        for (int i = start; i < end + 1; i++) {
            cities.set(i, sub_array.get(i - start));
        }
        return new TspSolution(cities);
    }

    public List<TspSolution> apply(List<TspSolution> population, Random random) {
        int route_length = population.get(0).getCities().size();

        ArrayList<TspSolution> new_population = new ArrayList<>(population.size());

        for (TspSolution solution : population) {
            TspSolution mutatedSolution;
            if (random.nextDouble() < insertProb) {
                mutatedSolution = doInsertMutation(random, route_length, solution.getCities());
                new_population.add(mutatedSolution);
            } else if (random.nextDouble() < swapProb) {
                mutatedSolution = doSwapMutation(random, route_length, solution.getCities());
                new_population.add(mutatedSolution);
            } else if (random.nextDouble() < inversionProb) {
                mutatedSolution = doInversionMutation(random, route_length, solution.getCities());
                new_population.add(mutatedSolution);
            } else if (random.nextDouble() < scrambleProb) {
                mutatedSolution = doScrambleMutation(random, route_length, solution.getCities());
                new_population.add(mutatedSolution);
            } else {
                new_population.add(solution);
            }
        }
        return new_population;
    }
}
