package lab3;

import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

import java.util.*;

public class TspFactory extends AbstractCandidateFactory<TspSolution> {

    int dimension;
    public TspFactory(int dimension) {
        this.dimension = dimension;
    }

    public TspSolution generateRandomCandidate(Random random) {
        ArrayList<Integer> candidate = new ArrayList<>();
        for (int i = 0; i < dimension; i++) {
            candidate.add(i);
        }
        Collections.shuffle(candidate);
        return new TspSolution(candidate);
    }
}

