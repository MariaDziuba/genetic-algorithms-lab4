package lab3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TspSolution {

    ArrayList<Integer> cities = new ArrayList<>();

    public TspSolution() {

    }
    public TspSolution(ArrayList<Integer> cities) {
        this.cities = cities;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int city: cities) {
            sb.append(city + 1).append(" ");
        }
        return sb.toString();
    }

    public ArrayList<Integer> getCities() {
        return cities;
    }
    // any required fields and methods
}
