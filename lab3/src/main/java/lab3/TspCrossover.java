package lab3;

import lab3.TspSolution;
import org.uncommons.watchmaker.framework.operators.AbstractCrossover;

import java.util.*;

public class TspCrossover extends AbstractCrossover<TspSolution> {
    protected TspCrossover() {
        super(1);
    }

    protected TspSolution doOrderedCrossover(ArrayList<Integer> p1, ArrayList<Integer> p2, Random random, int route_length) {
        int a = random.nextInt(route_length);
        int b = random.nextInt(route_length);
        int start = Math.max(a, b);
        int end = Math.max(a, b);
        Set<Integer> used = new HashSet<>();
        for (int i = start; i < end + 1; i++) {
            used.add(p1.get(i));
        }

        int i_idx = (end + 1) % route_length;
        ArrayList<Integer> child = new ArrayList<>(p1);

        for (int i = 0; i < route_length; i++) {
            int cur = p2.get((end + 1 + i) % route_length);
            if (!used.contains(cur)) {
                child.set(i_idx, cur);
                i_idx = (i_idx + 1) % route_length;
            }
        }
        return new TspSolution(child);
    }

    protected List<TspSolution> mate(TspSolution p1, TspSolution p2, int i, Random random) {
        ArrayList children = new ArrayList();

        ArrayList<Integer> p1_cities = p1.getCities();
        ArrayList<Integer> p2_cities = p2.getCities();
        int route_length = p1_cities.size();

        children.add(doOrderedCrossover(p1_cities, p2_cities, random, route_length));
        children.add(doOrderedCrossover(p1_cities, p2_cities, random, route_length));
        return children;
    }
}
