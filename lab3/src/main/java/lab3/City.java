package lab3;

public class City {
    double x;
    double y;

    public City(double x, double y) {
        this.x = x;
        this.y = y;
    }

    double getDistance(City other) {
        double x_dis = Math.abs(x - other.x);
        double y_dis = Math.abs(y - other.y);
        return Math.sqrt((Math.pow(x_dis, 2) + (Math.pow(y_dis, 2))));
    }
}
