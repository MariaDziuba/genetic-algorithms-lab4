package lab3;

import lab3.TspSolution;
import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.util.ArrayList;
import java.util.List;

public class TspFitnessFunction implements FitnessEvaluator<TspSolution> {
    ArrayList<City> cities;

    public TspFitnessFunction(ArrayList<City> cities) {
        this.cities = cities;
    }

    public double getFitness(TspSolution solution, List<? extends TspSolution> list) {
        double path_dist = 0.0;
        List<Integer> ids = solution.getCities();
        for (int i = 1; i < ids.size(); i++) {
            int id_pred = ids.get(i - 1);
            int id = ids.get(i);
            City city = cities.get(id_pred);
            path_dist += city.getDistance(cities.get(id));
        }
        return path_dist;
    }

    public boolean isNatural() {
        return false;
    }
}
